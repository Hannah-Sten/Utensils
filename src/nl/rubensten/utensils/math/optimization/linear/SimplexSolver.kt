package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.general.partitionOrNull
import nl.rubensten.utensils.math.matrix.*

/**
 *
 * @author Sten Wessel
 */
class SimplexSolver<T : Comparable<T>>(private val linearProgram: LinearProgram<T>) :
        LPSolver<T> {

    private val op = linearProgram.operationSet

    override fun solve(): LPSolution<T> {
        val standard = computeStandardForm()

        val tableau = Tableau(op, standard.A, standard.b, standard.c, standard.d).computeBasicFeasible()
        tableau.makeOptimal()

        return LPSolution(if (linearProgram.negated) op.negate(tableau.value) else tableau.value, tableau.basicSolution()!!.toList().subList(0, linearProgram.variables.map { it.size() }.sum()).toVector(op))
    }

    private fun computeStandardForm(): StandardLP<T> {
        val constraints: MutableSet<LinearConstraint<T>> = HashSet(linearProgram.constraints)
        val slack = mutableListOf<MutableVector<T>>()

        // Convert >= to <=
        constraints.filterIsInstance<LinearConstraint.GreaterThanEqual<T>>()
                .forEach {
                    constraints.add(it.right lessThanEqual it.left)
                    constraints.remove(it)
                }

        // Convert <= to == with added slack
        constraints.filterIsInstance<LinearConstraint.LessThanEqual<T>>()
                .forEach {
                    val s = GenericVector(op, it.left.dimension) { op.zero }
                    slack.add(s)
                    constraints.add(it.left + s equalTo it.right)
                    constraints.remove(it)
                }

        // Define ordering of the variables
        val variables = linearProgram.variables.toList() + slack.toList()

        // Positive variable and slack constraint
        val zero = GenericVector(op, variables.map(Vector<T>::size).sum()) { op.zero }
        val x = zero.toMutableVector()

        var b: Vector<T> = GenericVector(op)
        var A: Matrix<T> = GenericMatrix(op, width = x.size(), height = 1) { _, _ -> op.zero }

        // Now, all constraints are equality
        for (constraint in constraints) {
            // Normalize == to unit form Ax = b
            val (leftVarTerms, leftConstTerms) = constraint.left.terms.partitionOrNull { (_, x) -> variables.any { it === x } }
            val (rightVarTerms, rightConstTerms) = constraint.right.terms.partitionOrNull { (_, x) -> variables.any { it === x } }

            val leftVar = leftVarTerms?.let { LinearExpression(leftVarTerms) } ?: LinearExpression.zero(constraint.left.dimension, op)
            val leftConst = leftConstTerms?.let { LinearExpression(leftConstTerms) } ?: LinearExpression.zero(constraint.left.dimension, op)
            val rightVar = rightVarTerms?.let { LinearExpression(rightVarTerms) } ?: LinearExpression.zero(constraint.right.dimension, op)
            val rightConst = rightConstTerms?.let { LinearExpression(rightConstTerms) } ?: LinearExpression.zero(constraint.right.dimension, op)

            // Squash the right hand side into a single constant vector
            val right = (rightConst - leftConst).evaluate()

            // Convert left hand side to a single term Ax = b
            val leftTerms = leftVar - rightVar
            val left = variables.map {
                leftTerms.terms.find { (_, x) -> x === it }?.first
                        ?: GenericMatrix(op, leftTerms.dimension, it.size()) { _, _ -> op.zero }
            }.reduce(Matrix<T>::glueRight)

            // Merge the constraints
            A = A.glueBottom(left)
            b = b.append(right)
        }

        // Remove top dummy row from A
        A = A.subMatrix(1, 0, A.width(), A.height() - 1)

        // Convert the goal function into the standard form
        val const = linearProgram.goalFunction.terms.filter { (_, x) -> !variables.any { it === x } }

        // Squash constant part of the goal function
        val d = if (const.isEmpty()) op.zero else LinearFunction(const).evaluate()

        // Convert goal function to a single term
        val c = variables.map {
            linearProgram.goalFunction.terms.find { (_, x) -> x === it }?.first
                    ?: GenericVector(op, it.size()) { op.zero }
        }.reduce(Vector<T>::append)

        return StandardLP(A, b, c, d, x, zero)
    }

    private data class StandardLP<T: Comparable<T>>(val A: Matrix<T>, val b: Vector<T>, val c: Vector<T>, val d: T, val x: MutableVector<T>, val zero: Vector<T>)
}

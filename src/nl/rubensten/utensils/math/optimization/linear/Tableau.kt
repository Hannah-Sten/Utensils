package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.*

/**
 *
 * @author Sten Wessel
 */
class Tableau<T : Comparable<T>> private constructor(private val matrix: MutableMatrix<T>) : MutableMatrix<T> by matrix {

    constructor(op: OperationSet<T>, A: Matrix<T>, b: Vector<T>, c: Vector<T>, d: T) : this(
            A.glueRight(b.toMatrix()).glueTop(c.scalar(op.negate(op.unit)).append(d).toMatrix().transpose()).toMutableMatrix()
    )

    val value
        get() = get(0, width() - 1)

    fun pivot(i: Int, j: Int) {
        require(i in 0 until height() && j in 0 until width()) { throw IndexOutOfBoundsException() }
        val op = operations()

        scalarRowModify(i, op.inverse(get(i, j)))
        set(i, j, op.unit)

        for (row in (0 until height()).filter { it != i }) {
            if (isZero(get(row, j))) {
                continue
            }

            setRow(row, getRow(row) - getRow(i) * get(row, j))
            set(row, j, op.zero)
        }
    }

    fun nextPivot(): Pair<Int, Int>? {
        val op = operations()

        return c().mapIndexed { j, c -> j to c }
                .filter { (_, c) -> c > op.zero && !isZero(c) }
                .mapNotNull { (j, _) -> nextPivot(j) }
                .firstOrNull()
    }

    private fun nextPivot(j: Int): Pair<Int, Int>? {
        val op = operations()

        if (j !in 0 until width()) {
            throw IndexOutOfBoundsException()
        }

        val (i, _) = getColumn(j).mapIndexed { i, a -> i to a }.drop(1)
                .filter { (_, a) -> a > op.zero && !isZero(a) }
                .map { (i, a) -> i to op.division(b()[i - 1], a) }
                .minBy { (_, div) -> div } ?: return null

        return i to j
    }

    fun isFeasible(): Boolean {
        val op = operations()
        return b().all { it >= op.zero || isZero(it) }
    }

    fun isDualFeasible(): Boolean {
        val op = operations()
        return c().all { it <= op.zero || isZero(it) }
    }

    fun isOptimal() = isFeasible() && isDualFeasible()

    fun isBasic(): Boolean {
        val op = operations()
        val basis = MatrixUtils.identity(A().height(), op).columns().toSet()
        val c = c()

        val negZero = op.negate(op.zero)

        return A().columns()
                .map { it.map { if (it == negZero) op.zero else it }.toVector(op) }
                .filterIndexed { i, col -> col in basis && isZero(c[i]) }
                .toSet() == basis
    }

    fun isBasicFeasible() = isBasic() && isFeasible()

    fun basicSolution(): Vector<T>? {
        if (!isBasic()) {
            return null
        }

        val op = operations()
        val negZero = op.negate(op.zero)
        val b = b()
        val c = c()
        val basis = MatrixUtils.identity(A().height(), op).columns().toSet()

        return A().columns()
                .map { it.map { if (it == negZero) op.zero else it }.toVector(op) }
                .mapIndexed { i, col ->
                    if (col in basis && isZero(c[i])) {
                        b[col.indexOf(op.unit)]
                    }
                    else {
                        op.zero
                    }
                }.toVector(op)
    }

    fun makeOptimal() {
        check(isBasicFeasible()) { "Cannot make optimal when not basic or feasible." }

        while (!isOptimal()) {
            val (i, j) = nextPivot() ?: break
            pivot(i, j)
        }
    }

    fun computeBasicFeasible(): Tableau<T> {
        if (isBasicFeasible()) {
            return this
        }

        val op = operations()
        val negZero = op.negate(op.zero)
        val A = A()
        val c = c()
        val identity = MatrixUtils.identity(A.height(), op)
        val basis = identity.columns().toSet()

        if (isFeasible()) {
            return Tableau(op, A.glueRight(identity), b().toVector(op), c.toVector(op).append(GenericVector(op, identity.width()) { op.zero }), d())
        }

        // Solve auxiliary problem to find a basic feasible tableau
        val auxiliary = Tableau(op, A.glueRight(identity).glueRight(GenericVector(op, A.height()) { op.negate(op.unit) }.toMatrix()), b().toVector(op), GenericVector(op, A.width() + identity.width(), { op.zero }).append(op.negate(op.unit)), op.zero)

        auxiliary.apply {
            val j = A.width() + identity.width()
            val i = b().mapIndexed { i, x -> (i + 1) to x }.minBy { (_, x) -> x }!!.first
            pivot(i, j)
            makeOptimal()

            if (!isZero(value)) {
                throw IllegalStateException("Problem is infeasible.")
            }

        }

        val auxA = auxiliary.A()

        val basicFeasible = Tableau(op, auxA.subMatrix(0, 0, auxA.width() - 1, auxA.height()), auxiliary.b().toVector(op), c.toVector(op).append(GenericVector(op, identity.width()) { op.zero }), d())

        // Modify such that the first row has zeros above the basis columns
        basicFeasible.apply {
            val basicC = c()
            A().columns().mapIndexed { j, col -> j to col }
                    .filter { (j, col) -> !isZero(basicC[j]) && col.map { if (it == negZero) op.zero else it }.toVector(op) in basis }
                    .forEach { (j, col) -> pivot(col.indexOf(op.unit) + 1, j) }
        }

        return basicFeasible
    }

    private fun c() = getRow(0).scalar(operations().negate(operations().unit)).toList().dropLast(1)

    private fun b() = getColumn(width() - 1).toList().drop(1)

    private fun A() = subMatrix(1, 0, width() - 1, height() - 1)

    private fun d() = value

    private fun isZero(value: T): Boolean {
        val op = operations()
        return value == op.zero || value == op.negate(op.zero)
    }

    override fun toString(): String {
        return matrix.toString()
    }
}

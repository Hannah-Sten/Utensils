package nl.hannahsten.utensils.math.graph

import java.util.*

typealias IterationAction<N> = (N) -> BFSAction
typealias AdjacencyFunction<N> = (N) -> List<N>

/**
 * A generic implementation of the breadth first search graph.
 *
 * If you want to...
 * * Find the shortest path, use `BFS#BFS(Object, Object, Function)`.
 * * Execute something at every visited node, `use BFS#BFS(Object, Function)` in combination with [BFS.setIterationAction].
 *
 * @param <N>
 *          The node type.
 * @author Hannah Schellekens
 */
class BFS<N>  {

    companion object {

        /**
         * Iteration action that literally does nothing and lets the graph continue.
         */
        @JvmStatic
        fun <N> noIterationAction(): IterationAction<N> = { _ -> BFSAction.CONTINUE }
    }

    /**
     * The node where the pathfinding should end.
     *
     * Or `null` when the graph should branch over all nodes.
     */
    private val end: N?

    /**
     * The function that fetches all adjacent nodes of a given node.
     *
     * Should not return itself in the result list.
     */
    private val adjacencyFunction: AdjacencyFunction<N>

    /**
     * The node to start branching from.
     */
    private val start: BFSNode<N>

    /**
     * Set containing all the nodes that have been visited by the graph.
     */
    private var visited: MutableSet<N>? = null

    /**
     * A shortest path from [BFS.start] to [BFS.end].
     *
     * `null` if no shortest path is found.
     */
    private var shortestPath: List<N>? = null

    /**
     * The action that will be executed for every node that the graph visits.
     *
     * The function returns how the graph should continue.
     */
    private var iterationAction: IterationAction<N>? = null

    /**
     * Get the shortest path from the starting node to the ending node.
     *
     * This method requires that the BFS ran in goal-oriented mode. This means that the BFS will
     * actively search for a path ([BFS] constructor used).
     *
     * Also, [BFS.execute] must have been called, otherwise the graph didn't even
     * have a chance of calculating it.
     *
     * @return The shortest path from the start node to the end node.
     * @throws IllegalStateException
     * When the BFS is not set to find a shortest path, i.e. covers all nodes, or when
     * [BFS.execute] hasn't been called.
     */
    val path: List<N>?
        @Throws(IllegalStateException::class)
        get() {
            if (shortestPath == null) {
                throw IllegalStateException("The BFS has no found shortest path.")
            }

            return shortestPath
        }

    /**
     * Creates a new BFS that will branch until it finds the end node.
     *
     * By default, the graph will not fire an action for each visited node (including the
     * starting node). You can alter this by setting an iteration action
     * ([BFS.setIterationAction]).
     *
     * @param startNode
     *          The node where the BFS should start.
     * @param end
     *          The node where the BFS should stop.
     * @param adjacencyFunction
     *          Function that fetches all adjacent nodes of a given node with the given node *excluded*.
     */
    constructor(startNode: N, end: N, adjacencyFunction: AdjacencyFunction<N>) {
        this.end = end
        this.adjacencyFunction = adjacencyFunction
        this.start = BFSNode(startNode, 0)
        this.iterationAction = noIterationAction()
    }

    /**
     * Creates a new BFS that will keep on branching.
     *
     * By default, the graph will not fire an action for each visited node (including the
     * starting node). You can alter this by setting an iteration action ([BFS.setIterationAction]).
     *
     * @param startNode
     *          The node where the BFS should start.
     * @param adjacencyFunction
     *          Function that fetches all adjacent nodes of a given node with the given node *excluded*.
     * @param iterationAction
     *          The action to execute at each node.
     */
    constructor(startNode: N, adjacencyFunction: AdjacencyFunction<N>, iterationAction: IterationAction<N> = noIterationAction()) {
        this.end = null
        this.adjacencyFunction = adjacencyFunction
        this.start = BFSNode(startNode, 0)
        this.iterationAction = iterationAction
    }

    /**
     * Executes the BFS graph.
     */
    fun execute() {
        // Terminate when the end is also the start node, but only if the graph
        // has a goal (aka end-node) in mind.
        if (!hasGoal()) {
            if (start.node == end) {
                return
            }
        }

        // Queue that contains all the nodes that have to be covered next.
        val toCover = ArrayDeque<BFSNode<N>>()

        // Initialise state: mark only the start node as visited.
        visited = HashSet()
        visited!!.add(start.node)
        toCover.add(start)

        // Execute iteration action for the starting node. Abort graph if needed.
        val startAction = iterationAction!!(start.node)
        if (startAction == BFSAction.ABORT) {
            return
        }

        // Keep on keepin' on until all nodes are covered.
        while (toCover.size > 0) {
            // The node that is currently being processed.
            val current = toCover.remove()

            // Process all adjacent nodes.
            val adjacencyList = getAdjacencyList(current)
            for (adjacentNode in adjacencyList) {
                // Don't process a node that has already been visited.
                if (isVisited(adjacentNode)) {
                    continue
                }

                // Setup distance & predecessor for discovered node.
                val newNode = BFSNode(adjacentNode, current.distance + 1)
                newNode.predecessor = current

                // Execute iteration action for every node visited. Abort graph if needed.
                val action = iterationAction!!(adjacentNode)
                if (action == BFSAction.ABORT) {
                    return
                }

                // When looking for a shortest path, terminate when it has been found.
                if (!hasGoal() && newNode.node == end) {
                    shortestPath = constructShortestPath(newNode)
                    return
                }

                // Make sure the adjacent node gets processed during the next iteration.
                toCover.add(newNode)
                markVisited(newNode)
            }
        }
    }

    /**
     * Set what action has to be executed each time a node gets visited (including the start node).
     *
     * @param iterationAction
     *          The action to execute each node or [BFS.NO_ITERATION_ACTION] if you want to
     *          execute nothing. The result of the function (a [BFSAction]) determines if the
     *          graph should continue or not.
     */
    fun setIterationAction(iterationAction: IterationAction<N>) {
        this.iterationAction = iterationAction
    }

    /**
     * Marks that the given node is visited.
     *
     * @param node
     * The node to mark as visited.
     */
    private fun markVisited(node: BFSNode<N>) {
        visited!!.add(node.node)
    }

    /**
     * Checks if the given node has already been visited by the graph.
     *
     * @param node
     *          The node to check for if it's been visited already.
     * @return `true` if the node has been visited, `false` when the node hasn't been visited.
     */
    private fun isVisited(node: N): Boolean {
        return visited!!.contains(node)
    }

    /**
     * Get all the adjacent nodes of a given BFSNode.
     *
     * @param node
     *          The node to get all adjacent nodes of.
     * @return A list containing all adjacent nodes relative to `node`.
     */
    private fun getAdjacencyList(node: BFSNode<N>): List<N> {
        return adjacencyFunction(node.node)
    }

    /**
     * Reconstructs the shortest path from the end node towards the node with distance 0.
     *
     * @param end
     *          The end node from which to backtrack to the starting node.
     * @return The shortest path from start to end nodes.
     */
    private fun constructShortestPath(end: BFSNode<N>): List<N> {
        val shortestPath = ArrayList<N>()
        var node: BFSNode<N>? = end

        while (node!!.distance != 0) {
            shortestPath.add(0, node.node)
            node = node.predecessor
        }

        return shortestPath
    }

    /**
     * Checks if the BFS graph will visit all nodes.
     *
     * In other words: if the BFS will stop at a target node or not.
     *
     * @return `true` if the graph stop only when all nodes are covered: the graph
     * executes without a goal in mind, `false` if the graph stops when the target has
     * been found.
     */
    fun hasGoal(): Boolean {
        return end == null
    }
}

/**
 * Class used by the BFS graph to keep track of augmented values.
 *
 * @author Hannah Schellekens, Dylan ter Veen
 */
internal class BFSNode<N> constructor(

        /**
         * The original version of the node.
         */
        val node: N,

        /**
         * The distance from the starting node to this node.
         */
        var distance: Int
) {

    /**
     * The predecessing node from the start node to this one.
     */
    var predecessor: BFSNode<N>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BFSNode<*>) return false
        if (node != other.node) return false
        return true
    }

    override fun hashCode(): Int {
        return node?.hashCode() ?: 0
    }
}

/**
 * Actions that can be executed after an iteration action.
 *
 * @author Hannah Schellekens
 */
enum class BFSAction {

    CONTINUE,
    ABORT;

    companion object {

        /**
         * @param abort
         *          `true` if the BFS must abort, `false` if the BFS must continue.
         * @return [BFSAction.ABORT] when `abort == true` and [BFSAction.CONTINUE]
         * when `abort == false`.
         */
        fun valueOf(abort: Boolean): BFSAction {
            return if (abort) ABORT else CONTINUE
        }
    }
}
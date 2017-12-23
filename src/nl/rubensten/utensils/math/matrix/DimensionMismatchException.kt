package nl.rubensten.utensils.math.matrix

/**
 * Exception thrown when the dimension of given objects do not match.
 *
 * @author Ruben Schellekens
 */
class DimensionMismatchException : RuntimeException {

    constructor() : super()
    constructor(s: String) : super(s)
    constructor(s: String, throwable: Throwable) : super(s, throwable)
}
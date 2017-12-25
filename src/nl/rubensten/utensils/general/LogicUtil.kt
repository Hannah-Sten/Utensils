package nl.rubensten.utensils.general

/**
 * Returns `true` if `this => other`.
 */
infix fun Boolean.implies(other: Boolean) = !this || other
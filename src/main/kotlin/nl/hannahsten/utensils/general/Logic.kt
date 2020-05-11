package nl.hannahsten.utensils.general

/**
 * Returns `true` if `this => other`.
 */
infix fun Boolean.implies(other: Boolean) = !this || other

/**
 * Returns `true` if `this !=> other`.
 */
infix fun Boolean.notImplies(other: Boolean) = !implies(other)
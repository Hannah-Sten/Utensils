package nl.rubensten.utensils.util

/**
 * Just returns [Unit].
 */
public fun Any?.thenUnit() = Unit

/**
 * Just returns `null`.
 */
public fun Any?.thenNull() = null

/**
 * Does nothing and returns [Unit].
 */
public fun pass() = Unit
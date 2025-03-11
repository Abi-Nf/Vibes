package com.vibes.rv.util

interface Conversion<A, B> {
    fun convert(value: A): B
    fun revert(value: B): A
}

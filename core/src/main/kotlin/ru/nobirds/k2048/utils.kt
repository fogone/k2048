package ru.nobirds.k2048

import java.util.Queue
import java.util.ArrayDeque
import java.util.Deque
import java.util.Random

public fun intArrayFor(size:Int, builder:(Int)->Int):IntArray {
    val array = IntArray(size)

    for (i in array.indices) {
        array[i] = builder(i)
    }

    return array
}

public fun <T> List<T>.toDeque():Deque<T> = ArrayDeque<T>(this)

private val random = Random()
public fun <T> List<T>.random():T {
    val pos = random.nextInt(size)
    return this[pos]
}

public fun readChar():Int = System.`in`.read()

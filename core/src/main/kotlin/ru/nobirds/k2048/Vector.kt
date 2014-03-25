package ru.nobirds.k2048

import kotlin.support.AbstractIterator

public trait Vector : Iterable<Int> {

    val size:Int

    val indices:IntRange
    val invertIndices:IntProgression

    fun get(index:Int):Int

    fun set(index:Int, value:Int)

    fun setAll(builder:(Int)->Int) {
        indices.forEach {
            this[it] = builder(it)
        }
    }

    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        private var position = -1

        override fun computeNext() {
            position++
            if(position in indices) setNext(get(position))
            else done()
        }
    }

    fun indexOf(start:Int = 0, predicate:(Int)->Boolean):Int? {
        for (i in start..(size - 1)) {
            if(predicate(get(i)))
                return i
        }
        return null
    }

    fun indexOfInverce(start:Int = size-1, predicate:(Int)->Boolean):Int? {
        for (i in start.downTo(0)) {
            if(predicate(get(i)))
                return i
        }
        return null
    }

}

private trait MatrixVector : Vector {

    val matrix:Matrix

    override val size: Int
        get() = matrix.size

    override val indices: IntRange
        get() = matrix.indices

    override val invertIndices: IntProgression
        get() = matrix.invertIndices

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append("[")
        for (i in this) {
            builder.append(i).append(", ")
        }
        builder.append("]")

        return builder.toString()
    }
}

private class RowVector(override val matrix:Matrix, val row:Int) : MatrixVector {

    override fun get(index: Int): Int = matrix[index, row]

    override fun set(index: Int, value: Int) {
        matrix[index, row] = value
    }

    override fun toString(): String {
        return "row($row): " + super<MatrixVector>.toString()
    }
}

private class ColVector(override val matrix:Matrix, val col:Int) : MatrixVector {

    override fun get(index: Int): Int = matrix[col, index]

    override fun set(index: Int, value: Int) {
        matrix[col, index] = value
    }

    override fun toString(): String {
        return "col($col): " + super<MatrixVector>.toString()
    }
}
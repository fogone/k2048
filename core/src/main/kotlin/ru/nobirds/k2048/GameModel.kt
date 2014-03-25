package ru.nobirds.k2048

import java.util.ArrayDeque
import java.util.ArrayList
import java.util.Random

public class GameModel(val size:Int) {

    private val history = ArrayDeque<Matrix>(20)

    private val downRoller = DownRoller()
    private val upRoller = UpRoller()

    public val field:Matrix = Matrix(size) { x, y -> 0 }

    public fun back() {
        if(!history.empty)
            field.copyFrom(history.poll()!!)
    }

    public fun turn(turn:TurnType) {
        val copy = field.copy()
        processTurn(turn, copy)

        if(!copy.equals(field)) {
            history.add(field.copy())
            field.copyFrom(copy)
            putRandom()
        }
    }

    public fun putRandom() {
        val (x, y) = field.findAllPositions { it == 0 }.random()

        field[x, y] = 2
    }

    private fun processTurn(turn:TurnType, field:Matrix) {
        when(turn) {
            TurnType.left -> field.eachRow { downRoller.roll(it) }
            TurnType.right -> field.eachRow { upRoller.roll(it) }
            TurnType.up -> field.eachColumn { downRoller.roll(it) }
            TurnType.down -> field.eachColumn { upRoller.roll(it) }
        }
    }

    public val gameOver:Boolean
        get() = !field.anyIndex { x, y ->
            val current = field[x, y]

            TurnType.values().any { move ->
                val ix = x + move.dx
                val iy = y + move.dy

                if(ix in field.indices && iy in field.indices) {
                    val value = field[ix, iy]
                    value == 0 || value == current
                } else false
            }
        }

}
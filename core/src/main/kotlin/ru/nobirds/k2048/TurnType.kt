package ru.nobirds.k2048

public enum class TurnType(val dx:Int, val dy:Int) {

    none:TurnType(0, 0)
    left:TurnType(-1, 0)
    right:TurnType(1, 0)
    up:TurnType(0, -1)
    down:TurnType(0, 1)

}
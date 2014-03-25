package ru.nobirds.k2048

trait Roller {

    fun roll(vector:Vector)

}

private class DownRoller() : Roller {

    override fun roll(vector: Vector) {
        var index = 0
        while(index < vector.size) {
            val pos = vector.indexOf(index) { it != 0}

            if(pos == null)
                return

            index = rollDown(vector, pos)
        }
    }

    private fun rollDown(vector:Vector, index:Int):Int {
        if(index == 0)
            return vector.size

        var i = index - 1

        while(i > 0 && vector[i] == 0) { i-- }

        if(vector[index] == vector[i]) {
            vector[index] = 0
            vector[i] *= 2
            return index + 1
        } else if(vector[i] != 0) {
            i++
        }

        if(i == index)
            return index + 1

        vector[i] = vector[index]
        vector[index] = 0
        return index + 1
    }

}

private class UpRoller() : Roller {

    override fun roll(vector: Vector) {
        var index = vector.size-1
        while(index >= 0) {
            val pos = vector.indexOfInverce(index) { it != 0}

            if(pos == null)
                return

            index = rollUp(vector, pos)
        }
    }

    private fun rollUp(vector:Vector, index:Int):Int {
        if(index == vector.size-1)
            return index - 1

        var i = index + 1

        while(i < vector.size-1 && vector[i] == 0) { i++ }

        if(vector[index] == vector[i]) {
            vector[index] = 0
            vector[i] *= 2
            return index - 1
        } else if(vector[i] != 0) {
            i--
        }

        if(i == index)
            return index - 1

        vector[i] = vector[index]
        vector[index] = 0
        return index - 1
    }

}
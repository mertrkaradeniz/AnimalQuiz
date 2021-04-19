package com.mertrizakaradeniz.animalquiz.models

enum class BoardSize(val numCards: Int) {
    LEVEL_1(2),
    LEVEL_2(3),
    LEVEL_3(4),
    LEVEL_4(6),
    LEVEL_5(8);

    fun getWidth(): Int {
        return when (this) {
            LEVEL_1 -> 1
            LEVEL_2 -> 1
            LEVEL_3 -> 2
            LEVEL_4 -> 2
            LEVEL_5 -> 2
        }
    }

    fun getHeight(): Int {
        return numCards / getWidth()
    }
}
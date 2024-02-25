package org.example.model

enum class Color {
    WHITE, BLACK, NONE;

    fun otherColor(): Color =
        when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
            NONE -> NONE
        }
}
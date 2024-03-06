package floppychess.tools

private fun legalField(field: String): Boolean =
    field.length == 2 && field[0] in 'a'..'h' && field[1] in '1'..'8'

fun getKnightMoveFields(fieldString: String):List<String> {
    check(legalField(fieldString)) {"Illegal chess board field: $fieldString"}
    val field = Field.ofFieldString(fieldString)
    val direction = listOf(
        Field(-2, 1), Field(-1, 2), Field(1, 2), Field(2, 1),
        Field(-2, -1), Field(-1, -2), Field(1, -2), Field(2, -1)
    )

    return direction
        .map{dir -> field+dir}
        .filter { candidateField -> candidateField.isLegalField() }
        .map {legalField -> legalField.toString()}
}

fun getKingMoveFields(fieldString: String):List<String> {
    check(legalField(fieldString)) {"Illegal chess board field: $fieldString"}
    val field = Field.ofFieldString(fieldString)
    val direction = listOf(
        Field(-1, 0), Field(-1, 1), Field(0, 1), Field(1, 1),
        Field(1, 0), Field(1, -1), Field(0, -1), Field(-1, -1)
    )

    return direction
        .map{dir -> field+dir}
        .filter { candidateField -> candidateField.isLegalField() }
        .map {legalField -> legalField.toString()}
}

private fun getSlidingMoves(field: Field, dir: Field): List<String> {
    val result = mutableListOf<Field>()
    var nextField = field + dir
    while (nextField.isLegalField()) {
        result += nextField
        nextField += dir
    }
    return result.map {legalField -> legalField.toString()}
}

fun getBishopMoveFields(fieldString: String):List<List<String>> {
    check(legalField(fieldString)) {"Illegal chess board field: $fieldString"}
    val field = Field.ofFieldString(fieldString)
    val direction = listOf(
        Field(-1, 1), Field(1, 1), Field(1, -1),  Field(-1, -1)
    )

    return direction.map { dir -> getSlidingMoves(field, dir) }
}

fun getRookMoveFields(fieldString: String):List<List<String>> {
    check(legalField(fieldString)) { "Illegal chess board field: $fieldString" }
    val field = Field.ofFieldString(fieldString)
    val direction = listOf(
        Field(-1, 0), Field(0, 1), Field(1, 0), Field(0, -1)
    )

    return direction.map { dir -> getSlidingMoves(field, dir) }
}

fun getPawnMoveFields(fieldString: String, colorIsWhite: Boolean):List<String> {
    return getPawnMoveFields(fieldString, if (colorIsWhite) 1 else 6, if (colorIsWhite) 1 else -1)
}

fun getPawnCaptureFields(fieldString: String, colorIsWhite: Boolean):List<String> {
    return getPawnCaptureFields(fieldString, if (colorIsWhite) 1 else -1)
}

private fun getPawnMoveFields(fieldString: String, startRow: Int, direction: Int):List<String> {
    check(legalField(fieldString)) { "Illegal chess board field: $fieldString" }
    val field = Field.ofFieldString(fieldString)
    return when (field.row) {
        0, 7 -> emptyList()
        startRow -> {
            listOf(Field(0, 1*direction), Field(0, 2*direction))
                .map{dir -> field+dir}
                .filter { candidateField -> candidateField.isLegalField() }
                .map {legalField -> legalField.toString()}
        }
        else -> {
            listOf(Field(0, 1*direction))
                .map{dir -> field+dir}
                .filter { candidateField -> candidateField.isLegalField() }
                .map {legalField -> legalField.toString()}
        }
    }
}

private fun getPawnCaptureFields(fieldString: String, direction: Int):List<String> {
    check(legalField(fieldString)) { "Illegal chess board field: $fieldString" }
    val field = Field.ofFieldString(fieldString)
    return if (field.row == 0 || field.row == 7)
        emptyList()
    else
        listOf(Field(-1, 1*direction), Field(1, 1*direction))
        .map{dir -> field+dir}
        .filter { candidateField -> candidateField.isLegalField() }
        .map {legalField -> legalField.toString()}
}


private data class Field(val col: Int, val row: Int) {
    override fun toString(): String = ('a' + col).toString() + ('1' + row).toString()

    fun isLegalField(): Boolean = (col in 0..7) && (row in 0..7)
    operator fun plus(other: Field): Field =
        Field(this.col+other.col, this.row+other.row)

    companion object {
        fun ofFieldString(fieldString: String): Field =
            Field(fieldString[0] - 'a', fieldString[1] - '1')
    }
}
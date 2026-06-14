package com.example.tetrispractico.presentacion.models

import androidx.compose.ui.graphics.Color


enum class TetrominoType(val color: Color, val shape: Array<IntArray>) {
    I(Color.Cyan, arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(1, 1, 1, 1),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0)
    )),
    J(Color.Blue, arrayOf(
        intArrayOf(1, 0, 0),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 0)
    )),
    L(Color(0xFFFFA500), arrayOf( // Naranja
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 0)
    )),
    O(Color.Yellow, arrayOf(
        intArrayOf(1, 1),
        intArrayOf(1, 1)
    )),
    S(Color.Green, arrayOf(
        intArrayOf(0, 1, 1),
        intArrayOf(1, 1, 0),
        intArrayOf(0, 0, 0)
    )),
    T(Color(0xFF800080), arrayOf( // Morado
        intArrayOf(0, 1, 0),
        intArrayOf(1, 1, 1),
        intArrayOf(0, 0, 0)
    )),
    Z(Color.Red, arrayOf(
        intArrayOf(1, 1, 0),
        intArrayOf(0, 1, 1),
        intArrayOf(0, 0, 0)
    ))
}


data class ActivePiece(
    var type: TetrominoType,
    var x: Int, // Posición en la cuadrícula (columnas)
    var y: Int, // Posición en la cuadrícula (filas)
    var shape: Array<IntArray> = type.shape
) {
    // Convierte la matriz matemática en los bloques visuales de Jetpack Compose
    fun getBlocks(): List<Block> {
        val blocks = mutableListOf<Block>()
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] == 1) {
                    blocks.add(Block(x + col, y + row, type.color))
                }
            }
        }
        return blocks
    }
}
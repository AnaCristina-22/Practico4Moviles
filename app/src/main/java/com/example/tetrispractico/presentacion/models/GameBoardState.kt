package com.example.tetrispractico.presentacion.models

import androidx.compose.ui.graphics.Color

data class Block(val x: Int, val y: Int, val color: Color)

data class GameBoardState(
    val grid: List<List<Color>> = List(20) { List(10) { Color.Transparent } },
    val fallingPiece: List<Block> = emptyList(),
    val nextPiecePreview: List<Block> = emptyList()
)
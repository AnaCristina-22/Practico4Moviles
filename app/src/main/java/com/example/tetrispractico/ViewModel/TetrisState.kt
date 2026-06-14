package com.example.tetrispractico.ViewModel




sealed class TetrisState {
    object Idle : TetrisState()
    object Waiting : TetrisState()
    object Playing : TetrisState()
    data class Won(val score: Int, val lines: Int, val message: String = "") : TetrisState()
    data class Lost(val message: String) : TetrisState()
}
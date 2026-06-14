package com.example.tetrispractico.data.repository

import com.example.tetrispractico.data.remote.SocketHandler
import io.socket.client.Socket


class TetrisRepository(private val socketHandler: SocketHandler) {

    fun connect(url: String) {
        socketHandler.connect(url)
    }

    fun disconnect() {
        socketHandler.disconnect()
    }

    fun getSocket(): Socket? {
        return socketHandler.getSocket()
    }
}
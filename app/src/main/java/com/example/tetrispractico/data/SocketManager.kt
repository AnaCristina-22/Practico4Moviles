package com.example.tetrispractico.data.remote

import io.socket.client.IO
import io.socket.client.Socket

object SocketManager {
    private var socket: Socket? = null

    fun connect() {
        if (socket == null) {
            // IP de la PC
            socket = IO.socket("http://192.168.1.221:3000")
            socket?.connect()
        }
    }

    fun getSocket(): Socket? = socket
}
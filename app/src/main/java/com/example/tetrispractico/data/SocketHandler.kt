package com.example.tetrispractico.data.remote

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI

class SocketHandler {
    private var socket: Socket? = null

    fun connect(url: String) {
        if (socket == null) {
            socket = IO.socket(URI.create(url))
            socket?.connect()
        }
    }

    fun getSocket(): Socket? = socket

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }
}

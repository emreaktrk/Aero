package com.kocsistem.aero.io

class IO {

    companion object {
        fun create(url: String): Link {
            return Link(url)
        }
    }
}

package com.kocsistem.aero.io

import android.content.Context

class IO private constructor(context: Context? = null) {

    private val reference = ContextReference(context)

    companion object {
        fun create(): IO {
            return IO()
        }

        fun create(context: Context?): IO {
            return IO(context)
        }
    }
}

package com.kocsistem.aero.io

import android.net.Uri

class Link(private val url: String) {

    internal val uri: Uri.Builder = Uri.Builder()

    override fun toString(): String {
        return url + uri.build().toString()
    }
}
package com.kocsistem.aero.io

import com.android.volley.RequestQueue
import com.android.volley.Response

class Answer<T>(val parser: Parser<*>, private val builder: RequestBuilder) {

    var successListener: Response.Listener<T>? = null
    var errorListener: Response.ErrorListener? = null

    fun successListener(successListener: Response.Listener<T>?): Answer<T> {
        this.successListener = successListener

        return this
    }

    fun errorListener(errorListener: Response.ErrorListener): Answer<T> {
        this.errorListener = errorListener

        return this
    }

    fun async(queue: RequestQueue) {
        queue.add(builder.build())
    }

    fun sync() {

    }
}
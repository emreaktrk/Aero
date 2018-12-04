package com.kocsistem.aero.io

import com.android.volley.Response

class Answer<T>(val parser: Parser<*>) {

    var successListener: Response.Listener<T>? = null
        private set

    var errorListener: Response.ErrorListener? = null
        private set

    fun successListener(successListener: Response.Listener<T>?): Answer<T> {
        this.successListener = successListener

        return this
    }

    fun errorListener(errorListener: Response.ErrorListener): Answer<T> {
        this.errorListener = errorListener

        return this
    }

    fun transform(): Transformation {
        return Transformation(this)
    }
}
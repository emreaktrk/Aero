package com.kocsistem.aero.io

import com.android.volley.Response

class Answer<T>(val parser: Parser<*>) {

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

    fun transform(): Transformation {
        return Transformation(this)
    }
}
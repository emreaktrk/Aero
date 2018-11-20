package com.kocsistem.aero.io

import android.support.annotation.GuardedBy
import com.android.volley.Request
import com.android.volley.Response

abstract class BaseRequest<T>(
    @MethodType method: Int,
    url: String?,
    @GuardedBy("mLock") var successListener: Response.Listener<T>?,
    errorListener: Response.ErrorListener?
) :
    Request<T>(method, url, errorListener) {

    override fun deliverResponse(response: T) {
        successListener?.onResponse(response)
    }
}

abstract class RequestBuilder<T> {

    internal var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
    internal var url: String? = null
    internal var successListener: Response.Listener<T>? = null
    internal var errorListener: Response.ErrorListener? = null

    fun methodType(@MethodType methodType: Int): RequestBuilder<T> {
        this.methodType = methodType

        return this
    }

    fun url(url: String): RequestBuilder<T> {
        this.url = url

        return this
    }

    fun successListener(successListener: Response.Listener<T>?): RequestBuilder<T> {
        this.successListener = successListener

        return this
    }

    fun errorListener(errorListener: Response.ErrorListener): RequestBuilder<T> {
        this.errorListener = errorListener

        return this
    }

    abstract fun build(): BaseRequest<T>
}



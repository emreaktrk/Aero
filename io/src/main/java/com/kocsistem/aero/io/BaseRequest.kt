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

    var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
    var url: String? = null
    var successListener: Response.Listener<T>? = null
    var errorListener: Response.ErrorListener? = null

    open fun methodType(@MethodType methodType: Int): RequestBuilder<T> {
        this.methodType = methodType

        return this
    }

    open fun url(url: String): RequestBuilder<T> {
        this.url = url

        return this
    }

    open fun successListener(successListener: Response.Listener<T>?): RequestBuilder<T> {
        this.successListener = successListener

        return this
    }

    open fun errorListener(errorListener: Response.ErrorListener): RequestBuilder<T> {
        this.errorListener = errorListener

        return this
    }


    abstract fun build(): BaseRequest<T>
}



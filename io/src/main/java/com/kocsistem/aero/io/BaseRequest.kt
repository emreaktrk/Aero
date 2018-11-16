package com.kocsistem.aero.io

import android.support.annotation.GuardedBy
import com.android.volley.Request
import com.android.volley.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

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

fun Request<*>.generic(): Type {
    val self = this::class.java.genericInterfaces[0] as ParameterizedType
    return self.actualTypeArguments[0]
}


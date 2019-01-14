package com.kocsistem.aero.io

import android.support.annotation.GuardedBy
import com.android.volley.Request
import com.android.volley.Response

abstract class BaseRequest(
        @MethodType method: Int,
        url: String?,
        @GuardedBy("mLock") var successListener: Response.Listener<Any>?,
        errorListener: Response.ErrorListener?
) :
        Request<Any>(method, url, errorListener) {

    override fun deliverResponse(response: Any) {
        successListener?.onResponse(response)
    }
}

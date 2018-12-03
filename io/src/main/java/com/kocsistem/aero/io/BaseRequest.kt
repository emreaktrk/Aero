@file:Suppress("UNCHECKED_CAST")

package com.kocsistem.aero.io

import android.support.annotation.GuardedBy
import com.android.volley.Request
import com.android.volley.Response
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

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

abstract class RequestBuilder {

    internal var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
    internal var url: String? = null
    internal lateinit var answer: Answer<*>

    open fun methodType(@MethodType methodType: Int): RequestBuilder {
        this.methodType = methodType

        return this
    }

    open fun url(url: String): RequestBuilder {
        this.url = url

        return this
    }

    fun asJsonObject(): Answer<JSONObject> {
        this.answer = Answer<JSONObject>(JSONObjectParser(), this)

        return answer as Answer<JSONObject>
    }

    fun asJsonArray(): Answer<JSONArray> {
        this.answer = Answer<JSONObject>(JSONArrayParser(), this)

        return answer as Answer<JSONArray>
    }

    fun asRaw(): Answer<String?> {
        this.answer = Answer<JSONObject>(RawParser(), this)

        return answer as Answer<String?>
    }

    fun <T> asClass(clazz: Class<T>): Answer<T> {
        this.answer = Answer<T>(GsonParser(clazz), this)

        return answer as Answer<T>
    }

    fun <T> asType(token: TypeToken<T>): Answer<T> {
        this.answer = Answer<T>(GsonParser(token), this)

        return answer as Answer<T>
    }

    abstract fun build(): BaseRequest
}



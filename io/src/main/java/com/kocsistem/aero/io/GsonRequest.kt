package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class GsonRequest<T>(private val builder: GsonRequestBuilder<T>) :
    BaseRequest<T>(
        builder.methodType,
        builder.url,
        builder.successListener,
        builder.errorListener
    ) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {

        if (response != null) {
            if (response.data != null) {
                return try {
                    val charset = HttpHeaderParser.parseCharset(response.headers)
                    val headers = HttpHeaderParser.parseCacheHeaders(response)
                    val json = String(response.data, Charset.forName(charset))
                    val result = builder.parser!!.parse(json)

                    @Suppress("UNCHECKED_CAST")
                    Response.success(result as T, headers)
                } catch (e: JsonParseException) {
                    Response.error(BodyParseError())
                } catch (e: UnsupportedEncodingException) {
                    Response.error(CharsetError())
                }
            }

            return Response.error(NullDataError())
        }

        return Response.error(NullResponseError())
    }
}

class GsonRequestBuilder<T> : RequestBuilder<T>() {

    var parser: Parser<*>? = null

    fun asJsonObject(): RequestBuilder<T> {
        this.parser = JSONArrayParser()

        return this
    }

    fun asJsonArray(): GsonRequestBuilder<T> {
        this.parser = JSONArrayParser()

        return this
    }

    fun asRaw(): GsonRequestBuilder<T> {
        this.parser = RawParser()

        return this
    }

    fun asType(token: TypeToken<*>): GsonRequestBuilder<T> {
        this.parser = GsonParser(token)

        return this
    }

    override fun build(): BaseRequest<T> {
        return GsonRequest(this)
    }
}
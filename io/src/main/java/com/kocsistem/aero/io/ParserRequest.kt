package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class ParserRequest<T>(private val builder: ParserRequestBuilder<T>) :
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

class ParserRequestBuilder<T> : RequestBuilder<T>() {

    var parser: Parser<*>? = null

    fun asJsonObject(): RequestBuilder<T> {
        this.parser = JSONArrayParser()

        return this
    }

    fun asJsonArray(): RequestBuilder<T> {
        this.parser = JSONArrayParser()

        return this
    }

    fun asRaw(): ParserRequestBuilder<T> {
        this.parser = RawParser()

        return this
    }

    fun asClass(clazz: Class<T>): ParserRequestBuilder<T> {
        this.parser = GsonParser(clazz)

        return this
    }

    fun asType(token: TypeToken<T>): ParserRequestBuilder<T> {
        this.parser = GsonParser(token)

        return this
    }

    override fun build(): BaseRequest<T> {
        return ParserRequest(this)
    }
}
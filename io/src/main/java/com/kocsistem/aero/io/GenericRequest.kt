@file:Suppress("UNCHECKED_CAST")

package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class ParserRequest(private val builder: RequestBuilder) :
    BaseRequest(
        builder.methodType,
        builder.link.toString(),
        builder.answer.successListener as Response.Listener<Any>?,
        builder.answer.errorListener
    ) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<Any> {
        if (response != null) {
            if (response.data != null) {
                return try {
                    val charset = HttpHeaderParser.parseCharset(response.headers)
                    val headers = HttpHeaderParser.parseCacheHeaders(response)
                    val json = String(response.data, Charset.forName(charset))
                    val result = builder.answer.parser.parse(json)

                    Response.success(result, headers)
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

class RequestBuilder {

    internal var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
    internal lateinit var link: Link
    internal lateinit var answer: Answer<*>
    internal var payload: Payload<*>? = null

    companion object {
        fun create(@MethodType methodType: Int, url: String): RequestBuilder {
            return RequestBuilder()
                .methodType(methodType)
                .url(url)
        }
    }

    private fun methodType(@MethodType methodType: Int): RequestBuilder {
        this.methodType = methodType

        return this
    }

    private fun url(url: String): RequestBuilder {
        this.link = Link(url)

        return this
    }

    fun addQueryParameter(key: String, value: String): RequestBuilder {
        this.link.uri.appendQueryParameter(key, value)

        return this
    }

    fun addPath(path: String): RequestBuilder {
        this.link.uri.appendPath(path)

        return this
    }

    fun addPath(path: String, value: String): RequestBuilder {
        this.link.uri.appendPath(path).appendPath(value)

        return this
    }

    fun setBody(payload: Payload<*>): RequestBuilder {
        this.payload = payload

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

    fun asBinary(): Answer<ByteArray> {
        this.answer = Answer<ByteArray>(BinaryParser(), this)

        return answer as Answer<ByteArray>
    }

    fun <T> asClass(clazz: Class<T>): Answer<T> {
        this.answer = Answer<T>(GsonParser(clazz), this)

        return answer as Answer<T>
    }

    fun <T> asType(token: TypeToken<T>): Answer<T> {
        this.answer = Answer<T>(GsonParser(token), this)

        return answer as Answer<T>
    }

    fun build(): BaseRequest {
        return ParserRequest(this)
    }
}
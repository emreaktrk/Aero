@file:Suppress("UNCHECKED_CAST")

package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParseException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class ParserRequest(private val builder: ParserRequestBuilder) :
    BaseRequest(
        builder.methodType,
        builder.url,
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

class ParserRequestBuilder : RequestBuilder() {

    override fun methodType(methodType: Int): ParserRequestBuilder {
        super.methodType(methodType)

        return this
    }

    override fun url(url: String): ParserRequestBuilder {
        super.url(url)

        return this
    }

    override fun build(): BaseRequest {
        return ParserRequest(this)
    }
}
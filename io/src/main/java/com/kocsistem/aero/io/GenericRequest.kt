@file:Suppress("UNCHECKED_CAST")

package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParseException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class GenericRequest(private val transformation: Transformation) :
        BaseRequest(
                transformation.answer.parser.payload.link.methodType,
                transformation.answer.parser.payload.link.toString(),
                transformation.answer.successListener as Response.Listener<Any>?,
                transformation.answer.errorListener
        ) {

    override fun getHeaders(): MutableMap<String, String> {
        return transformation.answer.parser.payload.link.headers
    }

    override fun getBodyContentType(): String {
        transformation.answer.parser.payload.contentType?.let {
            return "$it; charset=$paramsEncoding"
        }

        return super.getBodyContentType()
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<Any> {
        if (response != null) {
            if (response.data != null) {
                return try {
                    val charset = HttpHeaderParser.parseCharset(response.headers)
                    val headers = HttpHeaderParser.parseCacheHeaders(response)
                    val json = String(response.data, Charset.forName(charset))
                    val result = transformation.answer.parser.parse(json)

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
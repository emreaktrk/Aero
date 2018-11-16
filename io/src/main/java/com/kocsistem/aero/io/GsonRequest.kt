package com.kocsistem.aero.io

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class GsonRequest<T>(
    methodType: Int,
    url: String?,
    successListener: Response.Listener<T>?,
    errorListener: Response.ErrorListener?
) : BaseRequest<T>(methodType, url, successListener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        if (response != null) {
            if (response.data != null) {
                return try {
                    val charset = HttpHeaderParser.parseCharset(response.headers)
                    val headers = HttpHeaderParser.parseCacheHeaders(response)

                    val json = String(response.data, Charset.forName(charset))
                    val type = generic()
                    val result: T = Gson().fromJson(json, type)

                    Response.success(result, headers)
                } catch (e: JsonSyntaxException) {
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
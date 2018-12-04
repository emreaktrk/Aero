package com.kocsistem.aero.io

import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject

class Link(val url: String) {

    internal var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
        private set
    internal val uri: Uri.Builder = Uri.Builder()
    internal var headers: MutableMap<String, String> = mutableMapOf()
        private set

    fun methodType(@MethodType methodType: Int): Link {
        this.methodType = methodType

        return this
    }

    fun addHeader(header: Header): Link {
        this.headers.plus(header)

        return this
    }

    fun addHeaders(headers: Map<String, String>): Link {
        this.headers.plus(headers)

        return this
    }

    fun addHeaders(headers: Collection<Header>): Link {
        this.headers.plus(headers)

        return this
    }

    fun setHeaders(headers: MutableMap<String, String>): Link {
        this.headers = headers

        return this
    }

    fun setHeaders(headers: Collection<Header>): Link {
        this.headers = headers.toMap().toMutableMap()

        return this
    }

    fun addQueryParameter(key: String, value: String): Link {
        this.uri.appendQueryParameter(key, value)

        return this
    }

    fun addPath(path: String): Link {
        this.uri.appendPath(path)

        return this
    }

    fun addPath(path: String, value: String): Link {
        this.uri.appendPath(path).appendPath(value)

        return this
    }

    fun setJsonPojoBody(pojo: Any): JsonPojoBody {
        return JsonPojoBody(this, pojo)
    }

    fun setJsonObjectBody(`object`: JSONObject): JsonObjectBody {
        return JsonObjectBody(this, `object`)
    }

    fun setJsonArrayBody(array: JSONArray): JsonArrayBody {
        return JsonArrayBody(this, array)
    }

    fun setUrlFormEncoded(forms: Map<String, String>): UrlFormEncoded {
        return UrlFormEncoded(this, forms)
    }

    fun setMultipart(parts: Map<String, Multipart.DataPart>): Multipart {
        return Multipart(this, parts)
    }

    override fun toString(): String {
        return url + uri.build().toString()
    }
}

typealias Header = Pair<String, String>
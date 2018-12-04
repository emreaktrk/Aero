package com.kocsistem.aero.io

import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject

class Link(val url: String) {

    internal var methodType: Int = MethodType.DEPRECATED_GET_OR_POST
    internal val uri: Uri.Builder = Uri.Builder()

    fun methodType(@MethodType methodType: Int): Link {
        this.methodType = methodType

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
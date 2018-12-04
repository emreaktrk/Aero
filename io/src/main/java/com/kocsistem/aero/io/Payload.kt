package com.kocsistem.aero.io

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

abstract class Payload<T>(val link: Link) {

    val contentType: String?
        get() = contentType()

    protected abstract fun body(): T

    protected abstract fun contentType(): String?

    fun asRaw(): RawParser {
        return RawParser(this)
    }

    fun asBinary(): BinaryParser {
        return BinaryParser(this)
    }

    fun asJSONArray(): JSONArrayParser {
        return JSONArrayParser(this)
    }

    fun asJSONObject(): JSONObjectParser {
        return JSONObjectParser(this)
    }

    fun <T> asClass(clazz: Class<T>): GsonParser<T> {
        return GsonParser(this, clazz)
    }

    fun <T> asType(token: TypeToken<T>): GsonParser<T> {
        return GsonParser(this, token)
    }
}

class NothingPayload(link: Link) : Payload<Nothing?>(link) {
    override fun body(): Nothing? {
        return null
    }

    override fun contentType(): String? {
        return null
    }
}

abstract class JsonBody(link: Link) : Payload<ByteArray>(link)

class JsonPojoBody(link: Link, private val pojo: Any) : JsonBody(link) {
    override fun body(): ByteArray {
        return Gson().toJson(pojo).toByteArray()
    }

    override fun contentType(): String {
        return "application/json"
    }
}

class JsonObjectBody(link: Link, private val `object`: JSONObject) : JsonBody(link) {
    override fun body(): ByteArray {
        return `object`.toString().toByteArray()
    }

    override fun contentType(): String {
        return "application/json"
    }
}

class JsonArrayBody(link: Link, private val array: JSONArray) : JsonBody(link) {
    override fun body(): ByteArray {
        return array.toString().toByteArray()
    }

    override fun contentType(): String {
        return "application/json"
    }
}

class FormData(link: Link, form: Map<String, String>) : Payload<Map<String, String>>(link) {
    override fun body(): Map<String, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentType(): String? {
        return null
    }
}

class UrlFormEncoded(link: Link, form: Map<String, String>) : Payload<Map<String, String>>(link) {
    override fun body(): Map<String, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentType(): String {
        return "application/x-www-form-urlencoded"
    }
}


class Multipart(link: Link, parts: Map<String, DataPart>) : Payload<Map<String, Multipart.DataPart>>(link) {
    override fun body(): Map<String, DataPart> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contentType(): String? {
        return null
    }

    inner class DataPart
}
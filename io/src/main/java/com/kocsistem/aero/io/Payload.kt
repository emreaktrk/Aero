package com.kocsistem.aero.io

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

abstract class Payload<T>(val link: Link) {
    abstract fun body(): T

    fun asRaw(): RawParser {
        return RawParser(this)
    }

    fun asBinary(): BinaryParser {
        return BinaryParser(this)
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

abstract class JsonBody(link: Link) : Payload<ByteArray>(link)

class JsonPojoBody(link: Link, private val pojo: Any) : JsonBody(link) {
    override fun body(): ByteArray {
        return Gson().toJson(pojo).toByteArray()
    }
}

class JsonObjectBody(link: Link, private val `object`: JSONObject) : JsonBody(link) {
    override fun body(): ByteArray {
        return `object`.toString().toByteArray()
    }
}

class JsonArrayBody(link: Link, private val array: JSONArray) : JsonBody(link) {
    override fun body(): ByteArray {
        return array.toString().toByteArray()
    }
}

class UrlFormEncoded(link: Link, form: Map<String, String>) : Payload<Map<String, String>>(link) {
    override fun body(): Map<String, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class Multipart(link: Link, parts: Map<String, DataPart>) : Payload<Map<String, Multipart.DataPart>>(link) {
    override fun body(): Map<String, DataPart> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class DataPart
}
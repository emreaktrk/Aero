package com.kocsistem.aero.io

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

abstract class Parser<T>(val payload: Payload<*>) {

    abstract fun parse(body: String?): T

    fun answer(): Answer<T> {
        return Answer(this)
    }
}

class RawParser(body: Payload<*>) : Parser<String?>(body) {

    override fun parse(body: String?): String? {
        return body
    }
}

class BinaryParser(body: Payload<*>) : Parser<ByteArray?>(body) {

    override fun parse(body: String?): ByteArray? {
        body?.let {
            return it.toByteArray()
        }

        return null
    }
}

class JSONObjectParser(body: Payload<*>) : Parser<JSONObject?>(body) {

    override fun parse(body: String?): JSONObject? {
        body?.let {
            return JSONObject(it)
        }

        return null
    }
}

class JSONArrayParser(body: Payload<*>) : Parser<JSONArray?>(body) {

    override fun parse(body: String?): JSONArray? {
        body?.let {
            return JSONArray(it)
        }

        return null
    }
}

class GsonParser<T> : Parser<T?> {

    private val token: TypeToken<T>

    constructor(body: Payload<*>, token: TypeToken<T>) : super(body) {
        this.token = token
    }

    constructor(body: Payload<*>, clazz: Class<T>) : super(body) {
        this.token = TypeToken.get(clazz)
    }

    @Throws(JsonParseException::class)
    override fun parse(body: String?): T? {
        body?.let {
            return Gson().fromJson(it, token.type)
        }

        return null
    }
}
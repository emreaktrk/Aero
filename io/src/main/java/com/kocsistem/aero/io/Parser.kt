package com.kocsistem.aero.io

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

abstract class Parser<T> {

    abstract fun parse(body: String?): T
}

class RawParser : Parser<String?>() {

    override fun parse(body: String?): String? {
        return body
    }
}

class JSONObjectParser : Parser<JSONObject?>() {

    override fun parse(body: String?): JSONObject? {
        body?.let {
            return JSONObject(it)
        }

        return null
    }
}

class JSONArrayParser : Parser<JSONArray?>() {

    override fun parse(body: String?): JSONArray? {
        body?.let {
            return JSONArray(it)
        }

        return null
    }
}

class GsonParser<T>(private val token: TypeToken<T>) : Parser<T?>() {

    @Throws(JsonParseException::class)
    override fun parse(body: String?): T? {
        body?.let {
            return Gson().fromJson(it, token.type)
        }

        return null
    }
}
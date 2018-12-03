package com.kocsistem.aero.io

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

abstract class Payload<T> {
    abstract fun body(): T
}

abstract class JsonBody : Payload<ByteArray>()

class JsonPojoBody(private val `object`: Any) : JsonBody() {
    override fun body(): ByteArray {
        return Gson().toJson(`object`).toByteArray()
    }
}

class JsonObjectBody(private val `object`: JSONObject) : JsonBody() {
    override fun body(): ByteArray {
        return `object`.toString().toByteArray()
    }
}

class JsonArrayBody(private val `object`: JSONArray) : JsonBody() {
    override fun body(): ByteArray {
        return `object`.toString().toByteArray()
    }
}

class UrlFormEndcoded : Payload<Map<String, String>>() {
    override fun body(): Map<String, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class Multipart : Payload<Map<String, Multipart.DataPart>>() {
    override fun body(): Map<String, DataPart> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class DataPart
}
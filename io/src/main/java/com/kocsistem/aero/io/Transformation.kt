package com.kocsistem.aero.io

import com.android.volley.RequestQueue

class Transformation(val answer: Answer<*>) {

    fun async(queue: RequestQueue) {
        queue.add(GenericRequest(this))
    }

    fun sync() {

    }
}
package com.kocsistem.aero.io

import android.support.test.runner.AndroidJUnit4
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.gson.reflect.TypeToken
import com.kocsistem.aero.core.InitProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class IOInstrumentedTest {

    private lateinit var mQueue: RequestQueue

    @Before
    fun setup() {
        mQueue = Volley.newRequestQueue(InitProvider.internalContext)
    }

    @Test
    fun todos() {
        val latch = CountDownLatch(1)
        IO
            .create("https://jsonplaceholder.typicode.com")
            .addPath("todos", "1")
            .asType(object : TypeToken<User>() {})
            .answer()
            .successListener(Response.Listener { latch.countDown() })
            .errorListener(Response.ErrorListener { latch.countDown() })
            .transform()
            .async(mQueue)

        latch.await()
    }

    @Test
    fun photos() {
        val latch = CountDownLatch(1)
        IO
            .create("https://jsonplaceholder.typicode.com")
            .addPath("albums", "1")
            .addPath("photos")
            .asJSONArray()
            .answer()
            .successListener(Response.Listener { latch.countDown() })
            .errorListener(Response.ErrorListener { latch.countDown() })
            .transform()
            .async(mQueue)

        latch.await()
    }

    @Test
    fun post() {
        val latch = CountDownLatch(1)
        IO
            .create("https://jsonplaceholder.typicode.com")
            .addPath("posts")
            .methodType(MethodType.POST)
            .setJsonPojoBody(Post("foo", "boo", 1))
            .asJSONObject()
            .answer()
            .successListener(Response.Listener { latch.countDown() })
            .errorListener(Response.ErrorListener { latch.countDown() })
            .transform()
            .async(mQueue)

        latch.await()
    }
}

package com.kocsistem.aero.core

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoreInstrumentedTest {

    @Test
    fun hasContext() {
        assertNotNull(InitProvider.internalContext)
    }
}

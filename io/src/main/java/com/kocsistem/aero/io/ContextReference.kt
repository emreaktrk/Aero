package com.kocsistem.aero.io

import android.content.Context
import com.kocsistem.aero.core.InitProvider
import java.lang.ref.WeakReference

class ContextReference(referent: Context?) : WeakReference<Context>(referent) {

    @Throws(ContextError::class)
    override fun get(): Context? {
        if (super.get() != null) {
            return super.get()
        }

        if (InitProvider.internalContext != null) {
            return InitProvider.internalContext
        }

        throw ContextError()
    }
}
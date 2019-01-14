package com.kocsistem.aero.io

import android.support.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
        MethodType.DEPRECATED_GET_OR_POST,
        MethodType.GET,
        MethodType.POST,
        MethodType.PUT,
        MethodType.DELETE,
        MethodType.HEAD,
        MethodType.OPTIONS,
        MethodType.TRACE,
        MethodType.PATCH
)
annotation class MethodType {
    companion object {
        const val DEPRECATED_GET_OR_POST = -1
        const val GET = 0
        const val POST = 1
        const val PUT = 2
        const val DELETE = 3
        const val HEAD = 4
        const val OPTIONS = 5
        const val TRACE = 6
        const val PATCH = 7
    }
}
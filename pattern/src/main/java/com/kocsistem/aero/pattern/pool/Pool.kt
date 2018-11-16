package com.kocsistem.aero.pattern.pool

import java.util.*


abstract class Pool<T>(private val expirationTime: Int = 30000) {

    private val locked: Hashtable<T, Long> = Hashtable()
    private val unlocked: Hashtable<T, Long> = Hashtable()

    protected abstract fun create(): T

    protected abstract fun validate(t: T): Boolean

    protected abstract fun expire(t: T)

    @Synchronized
    fun checkOut(): T {
        val now = System.currentTimeMillis()
        var key: T

        if (unlocked.size > 0) {
            val keys = unlocked.keys()

            while (keys.hasMoreElements()) {
                key = keys.nextElement()
                if (now - unlocked[key]!! > expirationTime) {
                    unlocked.remove(key)
                    expire(key)
                } else {
                    if (validate(key)) {
                        unlocked.remove(key)
                        locked[key] = now
                        return key
                    } else {
                        unlocked.remove(key)
                        expire(key)
                    }
                }
            }
        }

        key = create()
        locked[key] = now

        return key
    }

    @Synchronized
    fun checkIn(t: T) {
        locked.remove(t)
        unlocked[t] = System.currentTimeMillis()
    }
}
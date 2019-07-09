package com.example.test3

import android.app.Application
import io.realm.Realm

class WifiInfoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
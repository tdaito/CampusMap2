package com.example.test3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        back_button.setOnClickListener { finish() }

        realm = Realm.getDefaultInstance()

        val wifiInfos = realm.where<WifiInfo>().findAll()
        listView.adapter = WifiInfoAdapter(wifiInfos)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

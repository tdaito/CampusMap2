package com.example.test3

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WifiInfo : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var ssid1: String = ""
    var level1: Int = 0
    var ssid2: String = ""
    var level2: Int = 0
    var ssid3: String = ""
    var level3: Int = 0
}
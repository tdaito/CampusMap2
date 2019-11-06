package com.example.test3

import android.Manifest
import android.content.pm.PackageManager //packagemanager使う時に必要
import android.os.Build //Build使う時に必要
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.ListActivity
import android.net.wifi.WifiManager
import android.util.Log //Log使う時に必要
import android.widget.ArrayAdapter

import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import android.net.wifi.ScanResult
import android.widget.Toast

import android.content.Context.WIFI_SERVICE//@Nonnull使う時に必要
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

import android.content.Context
import android.content.Intent
import android.view.View
import io.realm.RealmConfiguration
import com.example.test3.DatabaseActivity as DatabaseActivity

import android.os.Handler
import java.util.*
import kotlin.math.sqrt

import android.widget.AdapterView
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0
    internal var CSVstr = ""

    private lateinit var realm: Realm

    private var floor:Int = 1
    private var place: String=""

    private var goalPoint=0
    private var goalName=""


    private val spinnerItems = arrayOf("B.Bアンドリアナ教授室","井坂教授室","井村教授室","石浦教授室","猪口教授室","今岡教授室","大崎教授室","大杉教授室","大間知教授室","小笠原教授室","岡村教授室","尾鼻教授室","片寄教授室","川端教授室","北村教授室","楠瀬教授室","栗田教授室","黒瀬教授室","河野教授室","工藤教授室","阪上教授室","作元教授室","澤田教授室","重藤教授室","示野教授室","住教授室","高橋和教授室","多賀教授室","武田教授室","田中教授室","田辺教授室","谷口教授室","玉井教授室","千代延教授室","徳山教授室","中井教授室","長田教授室","西谷教授室","畠山教授室","藤原伸教授室","藤原司教授室","北條教授室","前川教授室","松田教授室","巳波教授室","矢ケ崎教授室","山口教授室","山田教授室","山田英教授室","劉教授室")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView2.visibility = View.INVISIBLE
        imageView3.visibility = View.INVISIBLE

        val adapter = ArrayAdapter(applicationContext,
            android.R.layout.simple_spinner_item, spinnerItems)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                // Kotlin Android Extensions
                destination.text = item
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }

        /*Handler().postDelayed(Runnable {
            logScanResults()
        }, 1000)*/

        db_button.setOnClickListener { startActivity<DatabaseActivity>() }

        //データベースの再構築
        /*Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)*/

        realm = Realm.getDefaultInstance()

        val wifiInfos = realm.where<WifiInfo>().findAll()

        //データの全消去
        /*realm.executeTransaction {
            wifiInfos.deleteAllFromRealm()
        }*/

        //データの追加はこの下に記述
        /*realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室２６（打合せ室）"
            wifiInfo.ssid1 = "cc:e1:d5:3d:99:78"
            wifiInfo.level1 = -58
            wifiInfo.ssid2 = "d8:30:62:2e:12:31"
            wifiInfo.level2 = -44
            wifiInfo.ssid3 = "00:16:01:c6:eb:dd"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:62"
            wifiInfo.level4 = -75
            wifiInfo.ssid5 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level5 = -72
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室３０"
            wifiInfo.ssid1 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level1 = -56
            wifiInfo.ssid2 = "6c:70:9f:eb:4e:62"
            wifiInfo.level2 = -39
            wifiInfo.ssid3 = "00:3a:9d:b4:2f:a7"
            wifiInfo.level3 = -61
            wifiInfo.ssid4 = "98:01:a7:e8:7c:6e"
            wifiInfo.level4 = -76
            wifiInfo.ssid5 = "6c:70:9f:eb:4e:63"
            wifiInfo.level5 = -55
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "小笠原教授室"
            wifiInfo.ssid1 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "00:16:01:c6:eb:dd"
            wifiInfo.level2 = -62
            wifiInfo.ssid3 = "84:af:ec:d6:53:f0"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:63"
            wifiInfo.level4 = -66
            wifiInfo.ssid5 = "84:af:ec:d6:53:f7"
            wifiInfo.level5 = -68
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "川端教授室"
            wifiInfo.ssid1 = "18:c2:bf:5b:ba:22"
            wifiInfo.level1 = -42
            wifiInfo.ssid2 = "98:01:a7:e8:7c:6e"
            wifiInfo.level2 = -54
            wifiInfo.ssid3 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level3 = -63
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:62"
            wifiInfo.level4 = -65
            wifiInfo.ssid5 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level5 = -74
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "藤原伸教授室"
            wifiInfo.ssid1 = "cc:e1:d5:3d:99:78"
            wifiInfo.level1 = -54
            wifiInfo.ssid2 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level2 = -67
            wifiInfo.ssid3 = "00:16:01:c6:eb:dd"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "d8:30:62:2e:12:31"
            wifiInfo.level4 = -57
            wifiInfo.ssid5 = "98:f1:99:8c:42:1b"
            wifiInfo.level5 = -68
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "武田教授室"
            wifiInfo.ssid1 = "d8:30:62:2e:12:31"
            wifiInfo.level1 = -56
            wifiInfo.ssid2 = "00:0d:02:d6:17:c6"
            wifiInfo.level2 = -59
            wifiInfo.ssid3 = "98:f1:99:8c:42:1b"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "9e:f1:99:8c:42:1b"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "cc:e1:d5:3d:99:78"
            wifiInfo.level5 = -69
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "片寄教授室"
            wifiInfo.ssid1 = "98:01:a7:e8:7c:6e"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "98:01:a7:e8:7c:6f"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "00:1d:73:22:61:1e"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:62"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level5 = -63
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "北村教授室"
            wifiInfo.ssid1 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "00:3a:9d:b4:2f:a7"
            wifiInfo.level2 = -51
            wifiInfo.ssid3 = "6c:70:9f:eb:4e:62"
        wifiInfo.level3 = -53
        wifiInfo.ssid4 = "18:c2:bf:5b:ba:22"
        wifiInfo.level4 = -67
        wifiInfo.ssid5 = "98:01:a7:e8:7c:6e"
        wifiInfo.level5 = -66
        wifiInfo.floor =3
    }*/
        /*realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "玉井教授室"
            wifiInfo.ssid1 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level1 = -54
            wifiInfo.ssid2 = "f0:99:bf:0b:79:06"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "dc:fb:02:c9:4e:e4"
            wifiInfo.level3 = -69
            wifiInfo.ssid4 = "00:3a:9d:80:ff:9e"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "bc:3d:85:ee:70:89"
            wifiInfo.level5 = -70
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "栗田教授室"
            wifiInfo.ssid1 = "18:c2:bf:91:b7:31"
            wifiInfo.level1 = -38
            wifiInfo.ssid2 = "74:03:bd:2d:a4:d9"
            wifiInfo.level2 = -54
            wifiInfo.ssid3 = "00:3a:9d:80:ff:9e"
            wifiInfo.level3 = -58
            wifiInfo.ssid4 = "6c:70:9f:eb:83:64"
            wifiInfo.level4 = -53
            wifiInfo.ssid5 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level5 = -67
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "高橋和教授室"
            wifiInfo.ssid1 = "6c:70:9f:dc:d2:26"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "98:f1:99:86:82:1a"
            wifiInfo.level2 = -48
            wifiInfo.ssid3 = "6c:70:9f:eb:83:64"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "18:c2:bf:91:b7:31"
            wifiInfo.level4 = -73
            wifiInfo.ssid5 = "00:3a:9d:80:ff:9e"
            wifiInfo.level5 = -76
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "山田英教授室"
            wifiInfo.ssid1 = "f0:99:bf:0b:79:06"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "dc:fb:02:c9:4e:e4"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level3 = -67
            wifiInfo.ssid4 = "bc:3d:85:ee:70:89"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "00:3a:9d:80:ff:9e"
            wifiInfo.level5 = -78
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "住教授室"
            wifiInfo.ssid1 = "6c:70:9f:eb:83:64"
            wifiInfo.level1 = -41
            wifiInfo.ssid2 = "18:c2:bf:91:b7:31"
            wifiInfo.level2 = -51
            wifiInfo.ssid3 = "6c:70:9f:eb:83:65"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "18:c2:bf:91:b7:34"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "74:03:bd:2d:a4:da"
            wifiInfo.level5 = -62
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "石浦教授室"
            wifiInfo.ssid1 = "6c:70:9f:eb:83:64"
            wifiInfo.level1 = -53
            wifiInfo.ssid2 = "6c:70:9f:dc:d2:26"
            wifiInfo.level2 = -53
            wifiInfo.ssid3 = "6c:70:9f:dc:d2:27"
            wifiInfo.level3 = -63
            wifiInfo.ssid4 = "98:f1:99:86:82:1b"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "74:03:bd:2d:a4:d9"
            wifiInfo.level5 = -70
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "大間知教授室"
            wifiInfo.ssid1 = "f0:99:bf:0b:79:06"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "bc:3d:85:ee:70:89"
            wifiInfo.level2 = -61
            wifiInfo.ssid3 = "f0:99:bf:0b:79:07"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level4 = -58
            wifiInfo.ssid5 = "b0:6e:bf:9e:7a:0c"
            wifiInfo.level5 = -65
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "田中教授室"
            wifiInfo.ssid1 = "a4:12:42:aa:67:22"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "a4:12:42:aa:67:23"
            wifiInfo.level2 = -52
            wifiInfo.ssid3 = "74:03:bd:2d:a4:d9"
            wifiInfo.level3 = -52
            wifiInfo.ssid4 = "b6:12:42:aa:67:22"
            wifiInfo.level4 = -53
            wifiInfo.ssid5 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level5 = -56
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "畠山教授室"
            wifiInfo.ssid1 = "f0:99:bf:0b:79:06"
            wifiInfo.level1 = -52
            wifiInfo.ssid2 = "dc:fb:02:c9:4e:e4"
            wifiInfo.level2 = -61
            wifiInfo.ssid3 = "bc:3d:85:ee:70:89"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "b0:6e:bf:9e:7a:08"
            wifiInfo.level4 = -71
            wifiInfo.ssid5 = "18:c2:bf:91:b7:31"
            wifiInfo.level5 = -76
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "井坂教授室"
            wifiInfo.ssid1 = "62:84:bd:0d:24:f4"
            wifiInfo.level1 = -62
            wifiInfo.ssid2 = "60:84:bd:0d:24:f0"
            wifiInfo.level2 = -62
            wifiInfo.ssid3 = "62:84:bd:0d:24:f5"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "6c:e4:da:24:27:d6"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "88:57:ee:fa:6c:e0"
            wifiInfo.level5 = -77
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室１０"
            wifiInfo.ssid1 = "34:76:c5:89:9e:dc"
            wifiInfo.level1 = -52
            wifiInfo.ssid2 = "88:57:ee:fa:6c:e0"
            wifiInfo.level2 = -66
            wifiInfo.ssid3 = "36:76:c5:99:9e:dc"
            wifiInfo.level3 = -61
            wifiInfo.ssid4 = "74:03:bd:6a:b6:50"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "62:84:bd:0d:24:f4"
            wifiInfo.level5 = -74
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "阪上教授室"
            wifiInfo.ssid1 = "6c:e4:da:24:27:d6"
            wifiInfo.level1 = -54
            wifiInfo.ssid2 = "74:03:bd:6a:b6:50"
            wifiInfo.level2 = -58
            wifiInfo.ssid3 = "88:57:ee:fa:6c:e0"
            wifiInfo.level3 = -58
            wifiInfo.ssid4 = "62:84:bd:0d:24:f5"
            wifiInfo.level4 = -59
            wifiInfo.ssid5 = "62:84:bd:0d:24:f4"
            wifiInfo.level5 = -60
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "重藤教授室"
            wifiInfo.ssid1 = "88:57:ee:fa:6c:e0"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "88:57:ee:16:24:c3"
            wifiInfo.level2 = -57
            wifiInfo.ssid3 = "74:03:bd:6a:b6:50"
            wifiInfo.level3 = -55
            wifiInfo.ssid4 = "62:84:bd:0d:24:f4"
            wifiInfo.level4 = -59
            wifiInfo.ssid5 = "62:84:bd:0d:24:f5"
            wifiInfo.level5 = -60
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "多賀教授室"
            wifiInfo.ssid1 = "00:24:36:ab:9a:1d"
            wifiInfo.level1 = -46
            wifiInfo.ssid2 = "00:24:36:ab:9a:1e"
            wifiInfo.level2 = -52
            wifiInfo.ssid3 = "62:84:bd:0d:24:f5"
            wifiInfo.level3 = -71
            wifiInfo.ssid4 = "62:84:bd:0d:24:f4"
            wifiInfo.level4 = -71
            wifiInfo.ssid5 = "60:84:bd:0d:24:f0"
            wifiInfo.level5 = -72
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "田辺教授室"
            wifiInfo.ssid1 = "34:76:c5:89:9e:dc"
            wifiInfo.level1 = -55
            wifiInfo.ssid2 = "36:76:c5:99:9e:dc"
            wifiInfo.level2 = -56
            wifiInfo.ssid3 = "88:57:ee:fa:6c:e0"
            wifiInfo.level3 = -53
            wifiInfo.ssid4 = "74:03:bd:6a:b6:50"
            wifiInfo.level4 = -57
            wifiInfo.ssid5 = "74:03:bd:6a:b6:57"
            wifiInfo.level5 = -65
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "巳波教授室"
            wifiInfo.ssid1 = "00:24:36:ab:9a:1d"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "62:84:bd:0d:24:f4"
            wifiInfo.level2 = -74
            wifiInfo.ssid3 = "60:84:bd:0d:24:f0"
            wifiInfo.level3 = -75
            wifiInfo.ssid4 = "62:84:bd:0d:24:f5"
            wifiInfo.level4 = -76
            wifiInfo.ssid5 = "88:57:ee:fa:6c:e0"
            wifiInfo.level5 = -77
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "矢ケ崎教授室"
            wifiInfo.ssid1 = "74:03:bd:6a:b6:50"
            wifiInfo.level1 = -59
            wifiInfo.ssid2 = "62:84:bd:0d:24:f4"
            wifiInfo.level2 = -58
            wifiInfo.ssid3 = "60:84:bd:0d:24:f0"
            wifiInfo.level3 = -58
            wifiInfo.ssid4 = "62:84:bd:0d:24:f5"
            wifiInfo.level4 = -58
            wifiInfo.ssid5 = "88:57:ee:fa:6c:e0"
            wifiInfo.level5 = -58
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室２６（打合せ室）"
            wifiInfo.ssid1 = "cc:e1:d5:3d:99:78"
            wifiInfo.level1 = -56
            wifiInfo.ssid2 = "d8:30:62:2e:12:31"
            wifiInfo.level2 = -48
            wifiInfo.ssid3 = "00:16:01:c6:eb:dd"
            wifiInfo.level3 = -65
            wifiInfo.ssid4 = "9e:f1:99:8c:42:1b"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level5 = -69
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室３０"
            wifiInfo.ssid1 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level1 = -50
            wifiInfo.ssid2 = "6c:70:9f:eb:4e:62"
            wifiInfo.level2 = -42
            wifiInfo.ssid3 = "00:3a:9d:b4:2f:a7"
            wifiInfo.level3 = -54
            wifiInfo.ssid4 = "98:01:a7:e8:7c:6e"
            wifiInfo.level4 = -72
            wifiInfo.ssid5 = "6c:70:9f:eb:4e:63"
            wifiInfo.level5 = -62
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "小笠原教授室"
            wifiInfo.ssid1 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "00:16:01:c6:eb:dd"
            wifiInfo.level2 = -62
            wifiInfo.ssid3 = "84:af:ec:d6:53:f0"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:63"
            wifiInfo.level4 = -66
            wifiInfo.ssid5 = "84:af:ec:d6:53:f7"
            wifiInfo.level5 = -68
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "川端教授室"
            wifiInfo.ssid1 = "18:c2:bf:5b:ba:22"
            wifiInfo.level1 = -42
            wifiInfo.ssid2 = "98:01:a7:e8:7c:6e"
            wifiInfo.level2 = -54
            wifiInfo.ssid3 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level3 = -63
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:62"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level5 = -74
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "藤原伸教授室"
            wifiInfo.ssid1 = "cc:e1:d5:3d:99:78"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "cc:e1:d5:3d:93:1c"
            wifiInfo.level2 = -64
            wifiInfo.ssid3 = "00:16:01:c6:eb:dd"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "d8:30:62:2e:12:31"
            wifiInfo.level4 = -57
            wifiInfo.ssid5 = "98:f1:99:8c:42:1b"
            wifiInfo.level5 = -68
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "武田教授室"
            wifiInfo.ssid1 = "d8:30:62:2e:12:31"
            wifiInfo.level1 = -56
            wifiInfo.ssid2 = "00:0d:02:d6:17:c6"
            wifiInfo.level2 = -59
            wifiInfo.ssid3 = "98:f1:99:8c:42:1b"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "9e:f1:99:8c:42:1b"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "cc:e1:d5:3d:99:78"
            wifiInfo.level5 = -69
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "片寄教授室"
            wifiInfo.ssid1 = "98:01:a7:e8:7c:6e"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "98:01:a7:e8:7c:6f"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "00:1d:73:22:61:1e"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "6c:70:9f:eb:4e:62"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level5 = -63
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "北村教授室"
            wifiInfo.ssid1 = "00:3a:9d:b4:2f:a6"
            wifiInfo.level1 = -47
            wifiInfo.ssid2 = "00:3a:9d:b4:2f:a7"
            wifiInfo.level2 = -50
            wifiInfo.ssid3 = "6c:70:9f:eb:4e:62"
            wifiInfo.level3 = -51
            wifiInfo.ssid4 = "18:c2:bf:5b:ba:22"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "98:01:a7:e8:7c:6e"
            wifiInfo.level5 = -67
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室４１"
            wifiInfo.ssid1 = "60:84:bd:f1:25:23"
            wifiInfo.level1 = -53
            wifiInfo.ssid2 = "cc:e1:d5:75:a2:80"
            wifiInfo.level2 = -67
            wifiInfo.ssid3 = "18:c2:bf:50:68:c2"
            wifiInfo.level3 = -66
            wifiInfo.ssid4 = "00:f7:6f:d0:7c:44"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "10:da:43:c2:9d:7a"
            wifiInfo.level5 = -67
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "山口教授室"
            wifiInfo.ssid1 = "60:84:bd:f1:25:23"
            wifiInfo.level1 = -55
            wifiInfo.ssid2 = "18:c2:bf:50:68:c3"
            wifiInfo.level2 = -53
            wifiInfo.ssid3 = "34:76:c5:06:b3:b8"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "06:76:c5:06:b3:b8"
            wifiInfo.level4 = -62
            wifiInfo.ssid5 = "88:1f:a1:34:a4:94"
            wifiInfo.level5 = -56
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "示野教授室"
            wifiInfo.ssid1 = "dc:a4:ca:ba:c3:0a"
            wifiInfo.level1 = -46
            wifiInfo.ssid2 = "88:1f:a1:34:a4:94"
            wifiInfo.level2 = -47
            wifiInfo.ssid3 = "00:f7:6f:d0:7c:44"
            wifiInfo.level3 = -48
            wifiInfo.ssid4 = "10:da:43:c2:9d:7a"
            wifiInfo.level4 = -52
            wifiInfo.ssid5 = "cc:e1:d5:75:a2:80"
            wifiInfo.level5 = -54
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "松田教授室"
            wifiInfo.ssid1 = "18:c2:bf:50:68:c2"
            wifiInfo.level1 = -60
            wifiInfo.ssid2 = "34:3d:c4:33:e1:80"
            wifiInfo.level2 = -62
            wifiInfo.ssid3 = "88:1f:a1:34:a4:94"
            wifiInfo.level3 = -72
            wifiInfo.ssid4 = "60:84:bd:f1:25:23"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "10:da:43:c2:9d:79"
            wifiInfo.level5 = -68
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "西谷教授室"
            wifiInfo.ssid1 = "98:f1:99:bc:0f:66"
            wifiInfo.level1 = -59
            wifiInfo.ssid2 = "88:1f:a1:34:a4:94"
            wifiInfo.level2 = -47
            wifiInfo.ssid3 = "98:f1:99:bc:0f:67"
            wifiInfo.level3 = -55
            wifiInfo.ssid4 = "cc:e1:d5:75:a2:80"
            wifiInfo.level4 = -52
            wifiInfo.ssid5 = "00:f7:6f:d0:7c:44"
            wifiInfo.level5 = -54
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "大崎教授室"
            wifiInfo.ssid1 = "98:f1:99:bc:0f:66"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "88:1f:a1:34:a4:94"
            wifiInfo.level2 = -44
            wifiInfo.ssid3 = "98:f1:99:bc:0f:67"
            wifiInfo.level3 = -52
            wifiInfo.ssid4 = "cc:e1:d5:75:a2:80"
            wifiInfo.level4 = -53
            wifiInfo.ssid5 = "00:f7:6f:d0:7c:44"
            wifiInfo.level5 = -50
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室"
            wifiInfo.ssid1 = "88:1f:a1:34:a4:94"
            wifiInfo.level1 = -42
            wifiInfo.ssid2 = "60:84:bd:f1:25:23"
            wifiInfo.level2 = -53
            wifiInfo.ssid3 = "10:da:43:c2:9d:7a"
            wifiInfo.level3 = -56
            wifiInfo.ssid4 = "00:f7:6f:d0:7c:44"
            wifiInfo.level4 = -56
            wifiInfo.ssid5 = "18:c2:bf:50:68:c3"
            wifiInfo.level5 = -58
            wifiInfo.floor =4
        }
        //ここから新規データ（西側）
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "英語教授室１"
            wifiInfo.ssid1 = "cc:e1:d5:e8:9b:c8"
            wifiInfo.level1 = -70
            wifiInfo.ssid2 = "12:66:82:a8:b6:3f"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "10:66:82:a8:b6:3f"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "b0:c7:45:d0:8a:d0"
            wifiInfo.level4 = -83
                wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "英語教授室２"
            wifiInfo.ssid1 = "cc:e1:d5:e8:9b:c8"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "12:66:82:a8:b6:3f"
            wifiInfo.level2 = -71
            wifiInfo.ssid3 = "10:66:82:a8:b6:3f"
            wifiInfo.level3 = -73
            wifiInfo.ssid4 = "88:57:ee:6b:4a:28"
            wifiInfo.level4 = -84
            wifiInfo.ssid5 = "18:c2:bf:cd:54:c0"
            wifiInfo.level5 = -85
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "尾鼻教授室"
            wifiInfo.ssid1 = "34:76:c5:17:4e:64"
            wifiInfo.level1 = -67
            wifiInfo.ssid2 = "06:76:c5:17:4e:64"
            wifiInfo.level2 = -70
            wifiInfo.ssid3 = "b0:c7:45:d0:8a:d0"
            wifiInfo.level3 = -76
            wifiInfo.ssid4 = "88:57:ee:6b:4a:29"
            wifiInfo.level4 = -79
            wifiInfo.ssid5 = "88:57:ee:6b:4a:28"
            wifiInfo.level5 = -82
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "B.Bアンドリアナ教授室"
            wifiInfo.ssid1 = "34:76:c5:17:90:8a"
            wifiInfo.level1 = -67
            wifiInfo.ssid2 = "06:76:c5:17:90:8a"
            wifiInfo.level2 = -76
            wifiInfo.ssid3 = "6c:70:9f:dc:d2:26"
            wifiInfo.level3 = -76
            wifiInfo.ssid4 = "34:76:c5:90:80:2e"
            wifiInfo.level4 = -79
            wifiInfo.ssid5 = "bc:5c:4c:ab:70:fc"
            wifiInfo.level5 = -85
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "劉教授室"
            wifiInfo.ssid1 = "34:76:c5:17:90:8a"
            wifiInfo.level1 = -68
            wifiInfo.ssid2 = "06:76:c5:17:90:8a"
            wifiInfo.level2 = -70
            wifiInfo.ssid3 = "6c:70:9f:dc:d2:26"
            wifiInfo.level3 = -75
            wifiInfo.ssid4 = "34:76:c5:90:80:2e"
            wifiInfo.level4 = -81
            wifiInfo.ssid5 = "00:3a:9d:80:ff:9e"
            wifiInfo.level5 = -83
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "徳山教授室"
            wifiInfo.ssid1 = "6e:ff:7b:ad:61:38"
            wifiInfo.level1 = -58
            wifiInfo.ssid2 = "6e:ff:7b:ad:61:39"
            wifiInfo.level2 = -59
            wifiInfo.ssid3 = "68:ff:7b:ad:61:39"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "a4:12:42:94:dd:8f"
            wifiInfo.level4 = -63
            wifiInfo.ssid5 = "6e:ff:7b:ad:61:3a"
            wifiInfo.level5 = -65
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "作元教授室"
            wifiInfo.ssid1 = "6e:ff:7b:ad:61:38"
            wifiInfo.level1 = -46
            wifiInfo.ssid2 = "30:5a:3a:c6:74:d0"
            wifiInfo.level2 = -57
            wifiInfo.ssid3 = "a4:12:42:94:dd:8e"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "68:ff:7b:ad:61:38"
            wifiInfo.level4 = -58
            wifiInfo.ssid5 = "36:76:c5:1b:ad:42"
            wifiInfo.level5 = -75
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "谷口教授室"
            wifiInfo.ssid1 = "30:5a:3a:c6:74:d0"
            wifiInfo.level1 = -45
            wifiInfo.ssid2 = "30:5a:3a:c6:74:d4"
            wifiInfo.level2 = -50
            wifiInfo.ssid3 = "6e:ff:7b:ad:61:39"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "68:ff:7b:ad:61:39"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "6e:ff:7b:ad:61:38"
            wifiInfo.level5 = -62
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "澤田教授室"
            wifiInfo.ssid1 = "30:5a:3a:c6:74:d0"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "34:76:c5:1b:ad:42"
            wifiInfo.level2 = -61
            wifiInfo.ssid3 = "36:76:c5:1b:ad:42"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "06:76:c5:1e:f9:f8"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "6e:ff:7b:ad:61:38"
            wifiInfo.level5 = -72
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "楠瀬教授室"
            wifiInfo.ssid1 = "36:76:c5:1b:ad:42"
            wifiInfo.level1 = -39
            wifiInfo.ssid2 = "34:76:c5:1b:ad:42"
            wifiInfo.level2 = -43
            wifiInfo.ssid3 = "30:5a:3a:c6:74:d0"
            wifiInfo.level3 = -68
            wifiInfo.ssid4 = "00:24:a5:79:0b:c0"
            wifiInfo.level4 = -67
            wifiInfo.ssid5 = "b0:be:76:82:f3:02"
            wifiInfo.level5 = -79
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "岡村教授室"
            wifiInfo.ssid1 = "36:76:c5:1b:ad:42"
            wifiInfo.level1 = -53
            wifiInfo.ssid2 = "34:76:c5:1b:ad:42"
            wifiInfo.level2 = -51
            wifiInfo.ssid3 = "00:24:a5:79:0b:c0"
            wifiInfo.level3 = -72
            wifiInfo.ssid4 = "30:5a:3a:c6:74:d0"
            wifiInfo.level4 = -71
            wifiInfo.ssid5 = "b0:be:76:82:f3:02"
            wifiInfo.level5 = -79
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "客員教授室"
            wifiInfo.ssid1 = "98:f1:99:3d:8d:a2"
            wifiInfo.level1 = -48
            wifiInfo.ssid2 = "74:a3:4a:9e:8f:32"
            wifiInfo.level2 = -50
            wifiInfo.ssid3 = "9a:f1:99:3d:8d:a2"
            wifiInfo.level3 = -52
            wifiInfo.ssid4 = "34:76:c5:0d:0c:4e"
            wifiInfo.level4 = -63
            wifiInfo.ssid5 = "34:76:c5:0d:0c:4f"
            wifiInfo.level5 = -64
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "工藤教授室"
            wifiInfo.ssid1 = "98:f1:99:3d:8d:a2"
            wifiInfo.level1 = -55
            wifiInfo.ssid2 = "cc:e1:d5:77:4b:7c"
            wifiInfo.level2 = -56
            wifiInfo.ssid3 = "98:f1:99:3d:8d:a3"
            wifiInfo.level3 = -57
            wifiInfo.ssid4 = "34:76:c5:0d:0c:4e"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "34:76:c5:0d:0c:4f"
            wifiInfo.level5 = -61
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "山田教授室"
            wifiInfo.ssid1 = "74:a3:4a:9e:8f:32"
            wifiInfo.level1 = -45
            wifiInfo.ssid2 = "cc:e1:d5:77:4b:7c"
            wifiInfo.level2 = -55
            wifiInfo.ssid3 = "34:76:c5:0d:0c:4e"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "98:f1:99:3d:8d:a3"
            wifiInfo.level4 = -59
            wifiInfo.ssid5 = "34:76:c5:0d:0c:4f"
            wifiInfo.level5 = -60
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "河野教授室"
            wifiInfo.ssid1 = "06:76:c5:1e:f9:f8"
            wifiInfo.level1 = -34
            wifiInfo.ssid2 = "34:76:c5:1e:f9:f9"
            wifiInfo.level2 = -44
            wifiInfo.ssid3 = "34:76:c5:0d:0c:4f"
            wifiInfo.level3 = -60
            wifiInfo.ssid4 = "34:76:c5:1e:f9:f8"
            wifiInfo.level4 = -65
            wifiInfo.ssid5 = "dc:fb:02:03:55:40"
            wifiInfo.level5 = -65
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "長田教授室"
            wifiInfo.ssid1 = "34:76:c5:1e:f9:f8"
            wifiInfo.level1 = -58
            wifiInfo.ssid2 = "06:76:c5:1e:f9:f8"
            wifiInfo.level2 = -59
            wifiInfo.ssid3 = "c0:25:a2:81:75:12"
            wifiInfo.level3 = -59
            wifiInfo.ssid4 = "dc:fb:02:03:55:40"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "34:76:c5:1e:f9:f9"
            wifiInfo.level5 = -61
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "井村教授室"
            wifiInfo.ssid1 = "c0:25:a2:81:75:12"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "34:76:c5:1e:f9:f8"
            wifiInfo.level2 = -52
            wifiInfo.ssid3 = "06:76:c5:1e:f9:f8"
            wifiInfo.level3 = -63
            wifiInfo.ssid4 = "34:76:c5:1e:f9:f9"
            wifiInfo.level4 = -63
            wifiInfo.ssid5 = "c0:25:a2:81:75:13"
            wifiInfo.level5 = -63
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "前川教授室"
            wifiInfo.ssid1 = "dc:fb:02:d1:0b:c0"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "44:ad:d9:0e:32:31"
            wifiInfo.level2 = -57
            wifiInfo.ssid3 = "44:ad:d9:0e:32:33"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "44:ad:d9:0e:32:35"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "88:1f:a1:34:e9:74"
            wifiInfo.level5 = -74
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "千代延教授室"
            wifiInfo.ssid1 = "88:1f:a1:34:e9:74"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "44:ad:d9:0e:32:35"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "44:ad:d9:0e:32:31"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "34:76:c5:9a:3d:5e"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "44:ad:d9:0e:32:33"
            wifiInfo.level5 = -70
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "山根教授室"
            wifiInfo.ssid1 = "88:1f:a1:34:e9:74"
            wifiInfo.level1 = -47
            wifiInfo.ssid2 = "34:76:c5:9a:3d:5e"
            wifiInfo.level2 = -58
            wifiInfo.ssid3 = "36:76:c5:98:3d:5e"
            wifiInfo.level3 = -62
            wifiInfo.ssid4 = "80:ea:96:ed:3d:96"
            wifiInfo.level4 = -69
            wifiInfo.ssid5 = "dc:fb:02:c8:49:98"
            wifiInfo.level5 = -69
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "大杉教授室"
            wifiInfo.ssid1 = "dc:fb:02:c8:49:98"
            wifiInfo.level1 = -42
            wifiInfo.ssid2 = "80:ea:96:ed:3d:96"
            wifiInfo.level2 = -52
            wifiInfo.ssid3 = "88:1f:a1:34:e9:74"
            wifiInfo.level3 = -63
            wifiInfo.ssid4 = "36:76:c5:98:3d:5e"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "44:ad:d9:0e:32:33"
            wifiInfo.level5 = -69
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "黒瀬教授室"
            wifiInfo.ssid1 = "80:ea:96:ed:3d:96"
            wifiInfo.level1 = -57
            wifiInfo.ssid2 = "dc:fb:02:c8:49:98"
            wifiInfo.level2 = -52
            wifiInfo.ssid3 = "80:ea:96:ed:3d:97"
            wifiInfo.level3 = -66
            wifiInfo.ssid4 = "34:76:c5:90:27:4a"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "36:76:c5:98:3d:5e"
            wifiInfo.level5 = -69
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "藤原司教授室"
            wifiInfo.ssid1 = "44:ad:d9:0e:32:33"
            wifiInfo.level1 = -71
            wifiInfo.ssid2 = "44:ad:d9:0e:32:31"
            wifiInfo.level2 = -72
            wifiInfo.ssid3 = "4c:32:75:c7:78:2e"
            wifiInfo.level3 = -71
            wifiInfo.ssid4 = "88:1f:a1:34:e9:74"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "36:76:c5:98:3d:5e"
            wifiInfo.level5 = -75
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "今岡教授室"
            wifiInfo.ssid1 = "34:76:c5:90:27:4a"
            wifiInfo.level1 = -44
            wifiInfo.ssid2 = "4c:32:75:c7:4e:d6"
            wifiInfo.level2 = -48
            wifiInfo.ssid3 = "4e:d6:4e:c7:75:30"
            wifiInfo.level3 = -51
            wifiInfo.ssid4 = "44:ad:d9:0e:32:33"
            wifiInfo.level4 = -70
            wifiInfo.ssid5 = "44:ad:d9:0e:32:31"
            wifiInfo.level5 = -71
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "教授室４９"
            wifiInfo.ssid1 = "34:76:c5:90:27:4a"
            wifiInfo.level1 = -49
            wifiInfo.ssid2 = "4e:d6:4e:c7:75:30"
            wifiInfo.level2 = -55
            wifiInfo.ssid3 = "4c:32:75:c7:4e:d6"
            wifiInfo.level3 = -55
            wifiInfo.ssid4 = "34:76:c5:90:27:4b"
            wifiInfo.level4 = -60
            wifiInfo.ssid5 = "b6:6e:bf:9e:66:50"
            wifiInfo.level5 = -66
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "入口"
            wifiInfo.ssid1 = "18:c2:bf:50:68:c2"
            wifiInfo.level1 = -72
            wifiInfo.ssid2 = "88:57:ee:fa:6c:e0"
            wifiInfo.level2 = -77
            wifiInfo.ssid3 = "34:76:c5:26:34:12"
            wifiInfo.level3 = -77
            wifiInfo.ssid4 = "36:76:c5:26:34:12"
            wifiInfo.level4 = -78
            wifiInfo.ssid5 = "74:03:bd:6a:b6:50"
            wifiInfo.level5 = -80
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "1F-1"
            wifiInfo.ssid1 = "50:c4:dd:53:77:07"
            wifiInfo.level1 = -64
            wifiInfo.ssid2 = "98:f1:99:ee:b8:33"
            wifiInfo.level2 = -64
            wifiInfo.ssid3 = "52:c4:dd:53:77:0c"
            wifiInfo.level3 = -65
            wifiInfo.ssid4 = "50:c4:dd:5f:b1:19"
            wifiInfo.level4 = -65
            wifiInfo.ssid5 = "52:c4:dd:53:77:0d"
            wifiInfo.level5 = -66
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "1F-3"
            wifiInfo.ssid1 = "cc:e1:d5:e8:9b:c8"
            wifiInfo.level1 = -75
            wifiInfo.ssid2 = "88:57:ee:6b:4a:28"
            wifiInfo.level2 = -77
            wifiInfo.ssid3 = "88:57:ee:6b:4a:29"
            wifiInfo.level3 = -78
            wifiInfo.ssid4 = "88:57:ee:6b:4a:2d"
            wifiInfo.level4 = -85
            wifiInfo.ssid5 = "88:57:ee:6b:4a:2c"
            wifiInfo.level5 = -85
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "1F-4"
            wifiInfo.ssid1 = "00:16:01:2f:9e:3b"
            wifiInfo.level1 = -72
            wifiInfo.ssid2 = "fa:8f:ca:69:9d:b4"
            wifiInfo.level2 = -76
            wifiInfo.ssid3 = "84:af:ec:b7:ae:40"
            wifiInfo.level3 = -78
            wifiInfo.floor =1
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "2F-1"
            wifiInfo.ssid1 = "34:3d:c4:71:0c:80"
            wifiInfo.level1 = -80
            wifiInfo.ssid2 = "c0:25:a2:bf:e1:e6"
            wifiInfo.level2 = -84
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "2F-3"
            wifiInfo.ssid1 = "68:ff:7b:ad:61:38"
            wifiInfo.level1 = -71
            wifiInfo.ssid2 = "6e:ff:7b:ad:61:38"
            wifiInfo.level2 = -71
            wifiInfo.ssid3 = "a4:12:42:94:dd:8e"
            wifiInfo.level3 = -76
            wifiInfo.ssid4 = "30:5a:3a:c6:74:d0"
            wifiInfo.level4 = -80
            wifiInfo.ssid5 = "44:ad:d9:0e:32:33"
            wifiInfo.level5 = -80
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "2F-4"
            wifiInfo.ssid1 = "cc:e1:d5:ed:e2:dc"
            wifiInfo.level1 = -75
            wifiInfo.ssid2 = "cc:e1:d5:ee:4c:0c"
            wifiInfo.level2 = -76
            wifiInfo.ssid3 = "cc:e1:d5:ed:e2:dd"
            wifiInfo.level3 = -79
            wifiInfo.floor =2
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "3F-1"
            wifiInfo.ssid1 = "84:af:ec:ea:3e:17"
            wifiInfo.level1 = -74
            wifiInfo.ssid2 = "84:af:ec:ea:3e:10"
            wifiInfo.level2 = -73
            wifiInfo.ssid3 = "00:0d:02:d6:17:c6"
            wifiInfo.level3 = -77
            wifiInfo.ssid4 = "cc:e1:d5:3d:99:78"
            wifiInfo.level4 = -78
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "3F-2"
            wifiInfo.ssid1 = "44:ad:d9:0e:1c:21"
            wifiInfo.level1 = -73
            wifiInfo.ssid2 = "44:ad:d9:0e:1c:23"
            wifiInfo.level2 = -73
            wifiInfo.ssid3 = "44:ad:d9:0e:1c:2e"
            wifiInfo.level3 = -74
            wifiInfo.ssid4 = "44:ad:d9:0e:1c:2f"
            wifiInfo.level4 = -75
            wifiInfo.ssid5 = "44:ad:d9:0e:1c:2c"
            wifiInfo.level5 = -75
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "3F-3"
            wifiInfo.ssid1 = "9a:f1:99:3d:8d:a2"
            wifiInfo.level1 = -63
            wifiInfo.ssid2 = "44:ad:d9:0e:32:33"
            wifiInfo.level2 = -67
            wifiInfo.ssid3 = "34:76:c5:1e:f9:f8"
            wifiInfo.level3 = -68
            wifiInfo.ssid4 = "06:76:c5:1e:f9:f8"
            wifiInfo.level4 = -68
            wifiInfo.ssid5 = "98:f1:99:3d:8d:a2"
            wifiInfo.level5 = -71
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "3F-4"
            wifiInfo.ssid1 = "dc:fb:02:a5:78:58"
            wifiInfo.level1 = -59
            wifiInfo.ssid2 = "34:76:c5:90:27:4a"
            wifiInfo.level2 = -63
            wifiInfo.ssid3 = "00:0a:79:f2:51:96"
            wifiInfo.level3 = -70
            wifiInfo.ssid4 = "a4:12:42:12:ff:7e"
            wifiInfo.level4 = -74
            wifiInfo.ssid5 = "8c:3b:ad:18:48:40"
            wifiInfo.level5 = -76
            wifiInfo.floor =3
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "4F-1"
            wifiInfo.ssid1 = "18:c2:bf:93:40:a4"
            wifiInfo.level1 = -68
            wifiInfo.ssid2 = "18:c2:bf:93:40:a1"
            wifiInfo.level2 = -75
            wifiInfo.ssid3 = "02:1b:77:01:0d:d6"
            wifiInfo.level3 = -78
            wifiInfo.ssid4 = "34:76:c5:53:85:a4"
            wifiInfo.level4 = -81
            wifiInfo.ssid5 = "00:f7:6f:d0:7c:44"
            wifiInfo.level5 = -81
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "4F-2"
            wifiInfo.ssid1 = "44:ad:d9:0e:1c:21"
            wifiInfo.level1 = -51
            wifiInfo.ssid2 = "44:ad:d9:0e:1c:23"
            wifiInfo.level2 = -53
            wifiInfo.ssid3 = "44:ad:d9:0e:1c:25"
            wifiInfo.level3 = -53
            wifiInfo.ssid4 = "44:ad:d9:0e:1c:2f"
            wifiInfo.level4 = -64
            wifiInfo.ssid5 = "44:ad:d9:0e:1c:2e"
            wifiInfo.level5 = -64
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "4F-3"
            wifiInfo.ssid1 = "44:ad:d9:0e:32:33"
            wifiInfo.level1 = -65
            wifiInfo.ssid2 = "44:ad:d9:0e:32:31"
            wifiInfo.level2 = -68
            wifiInfo.ssid3 = "dc:fb:02:d1:0b:c0"
            wifiInfo.level3 = -80
            wifiInfo.ssid4 = "44:ad:d9:0e:32:35"
            wifiInfo.level4 = -83
            wifiInfo.ssid5 = "98:f1:99:3d:8d:a2"
            wifiInfo.level5 = -83
            wifiInfo.floor =4
        }
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "4F-4"
            wifiInfo.ssid1 = "34:76:c5:90:27:4a"
            wifiInfo.level1 = -59
            wifiInfo.ssid2 = "4c:32:75:c7:4e:d6"
            wifiInfo.level2 = -61
            wifiInfo.ssid3 = "4e:d6:4e:c7:75:30"
            wifiInfo.level3 = -64
            wifiInfo.ssid4 = "34:76:c5:90:27:4b"
            wifiInfo.level4 = -75
            wifiInfo.ssid5 = "b6:6e:bf:9e:66:50"
            wifiInfo.level5 = -73
            wifiInfo.floor =4
        }*/
        //データの追加はここまで

        //データベースのデータを取得
        /* val level = realm.where<WifiInfo>()
           .findAll()
        textView.setText(level[0]?.level1.toString())*/

        //繰り返しプログラム（10秒間）
       /* val handler = Handler()
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post(object : Runnable {
                    override fun run() {
                        logScanResults()

                        //ここに繰り返したいプログラム
                    }
                })
            }
        }, 0, 10000)*/
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 既に許可されているか確認
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 許可されていなかったらリクエストする
                // ダイアログが表示される
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                )
                return
            }
        }
        logScanResults()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 許可された場合
            logScanResults()
        } else {
            // 許可されなかった場合
            // 何らかの対処が必要
        }
    }

    private fun logScanResults() {
        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val scanResults = wm.scanResults


        /*処理時間計測
        val start:Long = System.currentTimeMillis()
        val end:Long = System.currentTimeMillis()
        System.out.println((end-start).toString()+"ms")*/



        val ssid = arrayOfNulls<String>(scanResults.size)  //受信した全てのWiFiのSSID
        val macadress = arrayOfNulls<String>(scanResults.size)  //受信した全てのWiFiのMACｱﾄﾞﾚｽ
        val rssi = IntArray(scanResults.size)  //受信した全てのWiFiの受信強度

        var i:Int=0
        for (scanResult in scanResults) {
            rssi[i]=scanResult.level
            macadress[i]=scanResult.BSSID
            i++
        }

        val wifiInfos = realm.where<WifiInfo>().findAll()

        var sum_of_square: Int=0
        var min: Int=1000000000
        var a:Int=0
        var b:Int=0
        var c:Int=0
        var d:Int=0
        var e:Int=0
        var j:Int=0

        i=0

        for (wifiInfo in wifiInfos) {
            for (scanResult in scanResults) {
                if (wifiInfo.ssid1 == macadress[i]) {
                    sum_of_square += (wifiInfo.level1 - rssi[i]) * (wifiInfo.level1 - rssi[i])
                    a++
                }
                if (wifiInfo.ssid2 == macadress[i]) {
                    sum_of_square += (wifiInfo.level2 - rssi[i]) * (wifiInfo.level2 - rssi[i])
                    b++
                }
                if (wifiInfo.ssid3 == macadress[i]) {
                    sum_of_square += (wifiInfo.level3 - rssi[i]) * (wifiInfo.level3 - rssi[i])
                    c++
                }
                if (wifiInfo.ssid4 == macadress[i]) {
                    sum_of_square += (wifiInfo.level4 - rssi[i]) * (wifiInfo.level4 - rssi[i])
                    d++
                }
                if (wifiInfo.ssid5 == macadress[i]) {
                    sum_of_square += (wifiInfo.level5 - rssi[i]) * (wifiInfo.level5 - rssi[i])
                    e++
                }
                i++
            }
            if (a==0){
                sum_of_square+=wifiInfo.level1*wifiInfo.level1
            }
            if (b==0){
                sum_of_square+=wifiInfo.level2*wifiInfo.level2
            }
            if (c==0){
                sum_of_square+=wifiInfo.level3*wifiInfo.level3
            }
            if (d==0){
                sum_of_square+=wifiInfo.level4*wifiInfo.level4
            }
            if (e==0){
                sum_of_square+=wifiInfo.level5*wifiInfo.level5
            }
            a=0
            b=0
            c=0
            d=0
            e=0

            if (sum_of_square<min){
                j++
                min=sum_of_square
                place=wifiInfo.name
                floor=wifiInfo.floor
            }
            sum_of_square=0
            i=0
        }
        textView.setText(place)

        imageView2.visibility = View.INVISIBLE
        var x:Float=0F
        var y:Float=0F
        if(floor==1){
            imageView.setImageResource(R.drawable.first)

            imageView2.visibility = View.VISIBLE
            if (place=="高橋和教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-150F)
                x=20F
                y=-150F
            }
            if (place=="石浦教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-120F)
                x=20F
                y=120F
            }
            if (place=="住教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-80F)
                x= 20F
                y=-80F
            }
            if (place=="栗田教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-50F)
                x=20F
                y=-50F
            }
            if (place=="田中教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-20F)
                x=20F
                y=-20F
            }
            if (place=="玉井教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(10F)
                x=20F
                y=10F
            }
            if (place=="大間知教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(50F)
                x=20F
                y=50F
            }
            if (place=="山田英教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(80F)
                x=20F
                y=80F
            }
            if (place=="畠山教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(110F)
                x=20F
                y=110F
            }
            if (place=="英語教授室１"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-280F)
                x=-420F
                y=-280F
            }
            if (place=="英語教授室２"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-310F)
                x=-420F
                y=-310F
            }
            if (place=="尾鼻教授室"){
                imageView2.setTranslationX(-130F)
                imageView2.setTranslationY(-300F)
                x=-130F
                y=-300F
            }
            if (place=="B.Bアンドリアナ教授室"){
                imageView2.setTranslationX(-160F)
                imageView2.setTranslationY(-90F)
                x=-160F
                y=-90F
            }
            if (place=="劉教授室"){
                imageView2.setTranslationX(-130F)
                imageView2.setTranslationY(-90F)
                x=-130F
                y=-90F
            }
            if (place=="1F-1"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(200F)
                x=-50F
                y=200F
            }
            if (place=="1F-2"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(-300F)
                x=-50F
                y=-300F
            }
            if (place=="1F-3"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(-300F)
                x=-355F
                y=-300F
            }
            if (place=="1F-4"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(200F)
                x=-355F
                y=200F
            }
            if (place=="入口"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(40F)
                x=-420F
                y=40F
            }
        }

        if(floor==2){
            imageView.setImageResource(R.drawable.second)

            imageView2.visibility = View.VISIBLE
            if (place=="多賀教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-150F)
                x=20F
                y=-150F
            }
            if (place=="巳波教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-120F)
                x=20F
                y=-120F
            }
            if (place=="井坂教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-80F)
                x=20F
                y=-80F
            }
            if (place=="？教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-50F)
                x=20F
                y=-50F
            }
            if (place=="阪上教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-20F)
                x=20F
                y=-20F
            }
            if (place=="矢ケ崎教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(10F)
                x=20F
                y=10F
            }
            if (place=="重藤教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(50F)
                x=20F
                y=50F
            }
            if (place=="田辺教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(80F)
                x=20F
                y=80F
            }
            if (place=="？教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(110F)
                x=20F
                y=110F
            }
            if (place=="徳山教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-350F)
                x=-420F
                y=-350F
            }
            if (place=="作元教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-320F)
                x=-420F
                y=-320F
            }
            if (place=="谷口教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-280F)
                x=-420F
                y=-280F
            }
            if (place=="澤田教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-250F)
                x=-420F
                y=-250F
            }
            if (place=="楠瀬教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-220F)
                x=-420F
                y=-220F
            }
            if (place=="岡村教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-180F)
                x=-420F
                y=-180F
            }
            if (place=="2F-1"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(200F)
                x=-50F
                y=200F
            }
            if (place=="2F-2"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(-300F)
                x=-50F
                y=-300F
            }
            if (place=="2F-3"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(-300F)
                x=-355F
                y=-300F
            }
            if (place=="2F-4"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(200F)
                x=-355F
                y=200F
            }
        }

        if(floor==3){
            imageView.setImageResource(R.drawable.three)

            imageView2.visibility = View.VISIBLE
            if (place=="片寄教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(-130F)
                x=130F
                y=-130F
            }
            if (place=="川端教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(-90F)
                x=130F
                y=-90F
            }
            if (place=="北村教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(-50F)
                x=130F
                y=-50F
            }
            if (place=="教授室３０"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(-10F)
                x=130F
                y=-10F
            }
            if (place=="中井教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(20F)
                x=130F
                y=20F
            }
            if (place=="小笠原教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(60F)
                x=130F
                y=60F
            }
            if (place=="藤原伸教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(100F)
                x=130F
                y=100F
            }
            if (place=="教授室２６（打合せ室）") {
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(130F)
                x = 130F
                y = 130F
                /*if (goalName=="北村教授室"||goalName=="藤原伸教授室"||goalName=="小笠原教授室"||goalName=="片寄教授室"||goalName=="川端教授室"||goalName=="中井教授室"||goalName=="武田教授室"){
                    navi.setText(goalName+"この列にあります")
                }*/
                if (destination.text.toString() == "北村教授室"||goalName=="藤原伸教授室"||goalName=="小笠原教授室"||goalName=="片寄教授室"||goalName=="川端教授室"||goalName=="中井教授室"||goalName=="武田教授室") {
                    navi.setText(destination.text.toString() + "はこの並びにあります")
                }
            }
            if (place=="武田教授室"){
                imageView2.setTranslationX(130F)
                imageView2.setTranslationY(170F)
                x=130F
                y=170F
                if (destination.text.toString() == "北村教授室"||goalName=="藤原伸教授室"||goalName=="小笠原教授室"||goalName=="片寄教授室"||goalName=="川端教授室"||goalName=="中井教授室"||goalName=="武田教授室") {
                    navi.setText(destination.text.toString()+ "はこの並びにあります")
                }
            }

            if (place=="客員教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-350F)
                x=-380F
                y=-350F
            }
            if (place=="工藤教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-310F)
                x=-380F
                y=-310F
            }
            if (place=="山田教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-280F)
                x=-380F
                y=-280F
            }
            if (place=="河野教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-240F)
                x=-380F
                y=-240F
            }
            if (place=="長田教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-200F)
                x=-380F
                y=-200F
            }
            if (place=="井村教授室"){
                imageView2.setTranslationX(-380F)
                imageView2.setTranslationY(-170F)
                x=-380F
                y=-170F
            }
            if (place=="3F-1"){
                imageView2.setTranslationX(50F)
                imageView2.setTranslationY(260F)
                x=50F
                y=260F
            }
            if (place=="3F-2"){
                imageView2.setTranslationX(50F)
                imageView2.setTranslationY(-320F)
                x=-50F
                y=-320F
            }
            if (place=="3F-3"){
                imageView2.setTranslationX(-300F)
                imageView2.setTranslationY(-320F)
                x=-300F
                y=-320F
            }
            if (place=="3F-4"){
                imageView2.setTranslationX(-300F)
                imageView2.setTranslationY(260F)
                x=-300F
                y=260F
            }
        }

        if(floor==4){
            imageView.setImageResource(R.drawable.fourth)
            if(goalPoint==floor && goalName=="示野教授室"){
                imageView3.setTranslationX(20F)
                imageView3.setTranslationY(-80F)
                navi.setText(goalName+"はこのフロアです")
            }
            if(goalPoint==floor && goalName=="西谷教授室"){
                imageView3.setTranslationX(20F)
                imageView3.setTranslationY(-50F)
                navi.setText(goalName+"はこのフロアです")
            }

            imageView2.visibility = View.VISIBLE
            if (place=="教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-150F)
                x=20F
                y=-150F
            }
            if (place=="教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-120F)
                x=20F
                y=-120F
            }
            if (place=="示野教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-80F)
                x=20F
                y=-80F
            }
            if (place=="西谷教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-50F)
                x=20F
                y=-50F
            }
            if (place=="大崎教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(-20F)
                x=20F
                y=-20F
            }
            if (place=="猪口教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(10F)
                x=20F
                y=10F
            }
            if (place=="山口教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(50F)
                x=20F
                y=50F
            }
            if (place=="松田教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(80F)
                x=20F
                y=80F
            }
            if (place=="教授室"){
                imageView2.setTranslationX(20F)
                imageView2.setTranslationY(110F)
                x=20F
                y=110F
            }
            if (place=="前川教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-290F)
                x=-420F
                y=-290F
            }
            if (place=="千代延教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-230F)
                x=-420F
                y=-260F
            }
            if (place=="山根教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-190F)
                x=-420F
                y=-230F
            }
            if (place=="大杉教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-150F)
                x=-420F
                y=-150F
            }
            if (place=="黒瀬教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-120F)
                x=-420F
                y=-120F
            }
            if (place=="藤原司教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(-90F)
                x=-420F
                y=-90F
            }
            if (place=="今岡教授室"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(180F)
                x=-420F
                y=-180F
            }
            if (place=="教授室４９"){
                imageView2.setTranslationX(-420F)
                imageView2.setTranslationY(210F)
                x=-420F
                y=210F
            }
            if (place=="4F-1"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(200F)
                x=-50F
                y=200F
            }
            if (place=="4F-2"){
                imageView2.setTranslationX(-50F)
                imageView2.setTranslationY(-300F)
                x=-50F
                y=-300F
            }
            if (place=="4F-3"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(-300F)
                x=-355F
                y=-300F
            }
            if (place=="4F-4"){
                imageView2.setTranslationX(-355F)
                imageView2.setTranslationY(200F)
                x=-355F
                y=200F
            }
        }

        button.setOnClickListener {
            var tmp:Array<Float> = arrayOf(0F,0F,0F,0F)
            var goal:Int=0
            var distance:Float=100000F
            for (wifiInfo in wifiInfos) {
                if(destination.text.toString()==wifiInfo.name) {
                    if(floor==1){
                        imageView3.visibility = View.VISIBLE
                        if (wifiInfo.floor == floor) {
                            navi.setText(destination.text.toString()+"はこのフロアです")
                            if (wifiInfo.name=="高橋和教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-150F)
                            }
                            if (wifiInfo.name=="石浦教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-120F)
                            }
                            if (wifiInfo.name=="住教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-80F)
                            }
                            if (wifiInfo.name=="栗田教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-50F)
                            }
                            if (wifiInfo.name=="田中教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-20F)
                            }
                            if (wifiInfo.name=="玉井教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(10F)
                            }
                            if (wifiInfo.name=="大間知教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(50F)
                            }
                            if (wifiInfo.name=="山田英教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(80F)
                            }
                            if (wifiInfo.name=="畠山教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(110F)
                            }
                        }else{
                            navi.setText(wifiInfo.name+"は"+wifiInfo.floor.toString()+"階です")
                            tmp[0]= sqrt((-50F-x)*(-50F-x)+(200F-y)*(200F-y))
                            tmp[1]= sqrt((-50F-x)*(-50F-x)+(-300F-y)*(-300F-y))
                            tmp[2]= sqrt((-355F-x)*(-355F-x)+(-300F-y)*(-300F-y))
                            tmp[3]= sqrt((-355F-x)*(-355F-x)+(200F-y)*(200F-y))
                            for (i in 0..3){
                                if (tmp[i]<distance){
                                    distance=tmp[i]
                                    goal=i
                                }
                            }
                            if(goal==0){
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(200F)
                            }
                            if(goal==1){
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(-300F)
                            }
                            if(goal==2){
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(-300F)
                            }
                            if(goal==3){
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(200F)
                            }
                        }
                    }

                    if(floor==2){
                        imageView3.visibility = View.VISIBLE
                        if (wifiInfo.floor == floor) {
                            navi.setText(destination.text.toString()+"はこのフロアです")
                            if (wifiInfo.name=="多賀教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-150F)
                            }
                            if (wifiInfo.name=="巳波教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-120F)
                            }
                            if (wifiInfo.name=="井坂教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-80F)
                            }
                            if (wifiInfo.name=="？教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-50F)
                            }
                            if (wifiInfo.name=="阪上教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-20F)
                            }
                            if (wifiInfo.name=="矢ケ崎教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(10F)
                            }
                            if (wifiInfo.name=="重藤教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(50F)
                            }
                            if (wifiInfo.name=="田辺教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(80F)
                            }
                            if (wifiInfo.name=="？教授室"){
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(110F)
                            }
                        }else{
                            navi.setText(wifiInfo.name+"は"+wifiInfo.floor.toString()+"階です")
                            tmp[0]= sqrt((-50F-x)*(-50F-x)+(200F-y)*(200F-y))
                            tmp[1]= sqrt((-50F-x)*(-50F-x)+(-300F-y)*(-300F-y))
                            tmp[2]= sqrt((-355F-x)*(-355F-x)+(-300F-y)*(-300F-y))
                            tmp[3]= sqrt((-355F-x)*(-355F-x)+(200F-y)*(200F-y))
                            for (i in 0..3){
                                if (tmp[i]<distance){
                                    distance=tmp[i]
                                    goal=i
                                }
                            }
                            if(goal==0){
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(200F)
                            }
                            if(goal==1){
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(-300F)
                            }
                            if(goal==2){
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(-300F)
                            }
                            if(goal==3){
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(200F)
                            }
                        }
                    }
                    if (floor == 3) {
                        imageView3.visibility = View.VISIBLE
                        if (wifiInfo.floor == floor) {
                            navi.setText(destination.text.toString() + "はこのフロアです")
                            if (wifiInfo.name == "北村研") {
                                imageView3.setTranslationX(10F)
                                imageView3.setTranslationY(160F)
                            }
                            if (wifiInfo.name == "川端研") {
                                imageView3.setTranslationX(10F)
                                imageView3.setTranslationY(80F)
                            }
                            if (wifiInfo.name == "片寄研") {
                                imageView3.setTranslationX(10F)
                                imageView3.setTranslationY(0F)
                            }
                            if (wifiInfo.name == "井村研") {
                                imageView3.setTranslationX(-80F)
                                imageView3.setTranslationY(-60F)
                            }
                            if (wifiInfo.name == "長田研") {
                                imageView3.setTranslationX(-170F)
                                imageView3.setTranslationY(-60F)
                            }
                            if (wifiInfo.name == "片寄教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(-130F)
                            }
                            if (wifiInfo.name == "川端教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(-90F)
                            }
                            if (wifiInfo.name == "北村教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(-50F)
                                break
                            }
                            if (wifiInfo.name == "教授室３０") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(-10F)
                            }
                            if (wifiInfo.name == "中井教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(20F)
                            }
                            if (wifiInfo.name == "小笠原教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(60F)
                            }
                            if (wifiInfo.name == "藤原伸教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(100F)
                            }
                            if (wifiInfo.name == "教授室２６（打合せ室）") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(130F)
                            }
                            if (wifiInfo.name == "武田教授室") {
                                imageView3.setTranslationX(130F)
                                imageView3.setTranslationY(170F)
                            }
                            if (wifiInfo.name == "客員教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-350F)
                            }
                            if (wifiInfo.name == "工藤教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-310F)
                            }
                            if (wifiInfo.name == "山田教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-280F)
                            }
                            if (wifiInfo.name == "河野教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-240F)
                            }
                            if (wifiInfo.name == "長田教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-200F)
                            }
                            if (wifiInfo.name == "井村教授室") {
                                imageView3.setTranslationX(-380F)
                                imageView3.setTranslationY(-170F)
                            }
                        } else {
                            goalPoint = wifiInfo.floor
                            goalName = wifiInfo.name
                            navi.setText(wifiInfo.name + "は" + wifiInfo.floor.toString() + "階です")
                            tmp[0] = sqrt((50F - x) * (50F - x) + (260F - y) * (260F - y))
                            tmp[1] = sqrt((50F - x) * (50F - x) + (-320F - y) * (-320F - y))
                            tmp[2] = sqrt((-300F - x) * (-300F - x) + (-320F - y) * (-320F - y))
                            tmp[3] = sqrt((-300F - x) * (-300F - x) + (260F - y) * (260F - y))
                            for (i in 0..3) {
                                if (tmp[i] < distance) {
                                    distance = tmp[i]
                                    goal = i
                                }
                            }
                            if (goal == 0) {
                                imageView3.setTranslationX(50F)
                                imageView3.setTranslationY(260F)
                            }
                            if (goal == 1) {
                                imageView3.setTranslationX(50F)
                                imageView3.setTranslationY(-320F)
                            }
                            if (goal == 2) {
                                imageView3.setTranslationX(-300F)
                                imageView3.setTranslationY(-320F)
                            }
                            if (goal == 3) {
                                imageView3.setTranslationX(-300F)
                                imageView3.setTranslationY(260F)
                            }
                        }
                    }
                    if (floor == 4) {
                        imageView3.visibility = View.VISIBLE
                        if (wifiInfo.floor == floor) {
                            navi.setText(destination.text.toString() + "はこのフロアです")
                            if (wifiInfo.name == "示野教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-80F)
                            }
                            if (wifiInfo.name == "西谷教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(-50F)
                            }
                            if (wifiInfo.name == "大崎教授室") {
                                imageView2.setTranslationX(20F)
                                imageView2.setTranslationY(-20F)
                            }
                            if (wifiInfo.name == "猪口教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(10F)
                            }
                            if (wifiInfo.name == "山口教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(50F)
                            }
                            if (wifiInfo.name == "松田教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(80F)
                            }
                            if (wifiInfo.name == "教授室") {
                                imageView3.setTranslationX(20F)
                                imageView3.setTranslationY(110F)
                            }
                        } else {
                            navi.setText(wifiInfo.name + "は" + wifiInfo.floor.toString() + "階です")
                            tmp[0] = sqrt((-50F - x) * (-50F - x) + (200F - y) * (200F - y))
                            tmp[1] = sqrt((-50F - x) * (-50F - x) + (-300F - y) * (-300F - y))
                            tmp[2] = sqrt((-355F - x) * (-355F - x) + (-300F - y) * (-300F - y))
                            tmp[3] = sqrt((-355F - x) * (-355F - x) + (200F - y) * (200F - y))
                            for (i in 0..3) {
                                if (tmp[i] < distance) {
                                    distance = tmp[i]
                                    goal = i
                                }
                            }
                            if (goal == 0) {
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(200F)
                            }
                            if (goal == 1) {
                                imageView3.setTranslationX(-50F)
                                imageView3.setTranslationY(-300F)
                            }
                            if (goal == 2) {
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(-300F)
                            }
                            if (goal == 3) {
                                imageView3.setTranslationX(-355F)
                                imageView3.setTranslationY(200F)
                            }
                        }
                    }
                }
            }
        }

        for (scanResult in scanResults) {
            Log.d(TAG, scanResult.toString())
        }

        Toast.makeText(applicationContext, "WiFi情報取得", Toast.LENGTH_LONG).show()

        //時間処理
        /*Handler().postDelayed(Runnable {
            logScanResults()
        }, 10000)*/

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    companion object {
        private val TAG = "MainActivity"
    }

    // オプションメニューを作成する
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // menuにcustom_menuレイアウトを適用
        menuInflater.inflate(R.menu.main, menu)
        // オプションメニュー表示する場合はtrue
        return true
    }

    // メニュー選択時の処理　今回はトースト表示
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        // 押されたメニューのIDで処理を振り分ける
        when (menuItem.itemId) {
            R.id.f1 -> {
                imageView.setImageResource(R.drawable.first)
                imageView2.visibility = View.INVISIBLE
                if (floor==1) imageView2.visibility = View.VISIBLE
                return true
            }

            R.id.f2 -> {
                imageView.setImageResource(R.drawable.second)
                imageView2.visibility = View.INVISIBLE
                if (floor==2) imageView2.visibility = View.VISIBLE
                return true
            }

            R.id.f3 -> {
                imageView.setImageResource(R.drawable.three)
                imageView2.visibility = View.INVISIBLE
                if (floor==3) imageView2.visibility = View.VISIBLE
                return true
            }

            R.id.f4 -> {
                imageView.setImageResource(R.drawable.fourth)
                imageView2.visibility = View.INVISIBLE
                if (floor==4) imageView2.visibility = View.VISIBLE
                return true
            }
        }
        return true
    }


}


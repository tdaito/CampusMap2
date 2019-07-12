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
import java.util.Date
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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View
import io.realm.RealmConfiguration
import com.example.test3.DatabaseActivity as DatabaseActivity

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0
    internal var CSVstr = ""

    private lateinit var realm: Realm

    private var floor:Int = 1
    private var place: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val myView = MyView(this)
        setContentView(myView)*/

        imageView2.visibility = View.INVISIBLE

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
        realm.executeTransaction {
            wifiInfos.deleteAllFromRealm()
        }

        //データの追加
        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "北村研"
            wifiInfo.ssid1 = "KITAMURA-LAB2"
            wifiInfo.level1 = -36
            wifiInfo.ssid2 = "KawLab"
            wifiInfo.level2 = -55
            wifiInfo.ssid3 = "Fujiwara lab 3"
            wifiInfo.level3 = -64
            wifiInfo.floor = 3
        }

        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "川端研"
            wifiInfo.ssid1 = "KawLab"
            wifiInfo.level1 = -46
            wifiInfo.ssid2 = "KawLab-11a"
            wifiInfo.level2 = -48
            wifiInfo.ssid3 = "KITAMURA-LAB2"
            wifiInfo.level3 = -60
            wifiInfo.floor = 3
        }

        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "片寄研"
            wifiInfo.ssid1 = "aterm-3659c5-g"
            wifiInfo.level1 = -57
            wifiInfo.ssid2 = "Buffalo-G-931C"
            wifiInfo.level2 = -59
            wifiInfo.ssid3 = "DIRECT-UcSURFACE-KITAMURmsWW"
            wifiInfo.level3 = -63
            wifiInfo.floor = 3
        }

        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "井村研"
            wifiInfo.ssid1 = "imura-lab-wifi-a"
            wifiInfo.level1 = -53
            wifiInfo.ssid2 = "NagataLabAirStation2_G"
            wifiInfo.level2 = -56
            wifiInfo.ssid3 = "NagataLab-WiFi"
            wifiInfo.level3 = -62
            wifiInfo.floor = 3
        }

        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "長田研"
            wifiInfo.ssid1 = "NagataLab-WiFi"
            wifiInfo.level1 = -43
            wifiInfo.ssid2 = "NagataLabAirStation2_G"
            wifiInfo.level2 = -44
            wifiInfo.ssid3 = "imura-lab-wifi-g"
            wifiInfo.level3 = -73
            wifiInfo.floor = 3
        }

        realm.executeTransaction {
            val maxId = realm.where<WifiInfo>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1
            val wifiInfo = realm.createObject<WifiInfo>(nextId)

            wifiInfo.name = "領域実習室"
            wifiInfo.ssid1 = "EMP285FE0"
            wifiInfo.level1 = -35
            wifiInfo.ssid2 = "fieldlab"
            wifiInfo.level2 = -47
            wifiInfo.ssid3 = "IST-HSI"
            wifiInfo.level3 = -66
            wifiInfo.floor = 4
        }

        //データベースのデータを取得
        /* val level = realm.where<WifiInfo>()
           .findAll()
        textView.setText(level[0]?.level1.toString())*/

    }

    // Viewを継承したクラス
    /*internal inner class MyView(context: Context) : View(context) {
        private var paint: Paint = Paint()

        // 描画するラインの太さ
        private val lineStrokeWidth = 20f

        init {
        }

        override fun onDraw(canvas: Canvas){

           var bitmap1 : Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.threef)
            canvas.setBitmap(bitmap1)

            //imageView.setImageResource(R.drawable.first)

            // ペイントする色の設定
            paint.color = Color.argb(255, 255, 0, 255)

            // ペイントストロークの太さを設定
            paint.strokeWidth = lineStrokeWidth

            // Styleのストロークを設定する
            paint.style = Paint.Style.STROKE

            // drawRectを使って矩形を描画する、引数に座標を設定
            // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
            canvas.drawRect(300f, 300f, 600f, 600f, paint)



        }
    }*/

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

        val ssid = arrayOfNulls<String>(scanResults.size)  //受信した全てのWiFiのSSID
        val macadress = arrayOfNulls<String>(scanResults.size)  //受信した全てのWiFiのMACｱﾄﾞﾚｽ
        val rssi = IntArray(scanResults.size)  //受信した全てのWiFiの受信強度


        //文字列を" "で区切る
        //val mojiretsu = "An apple on the Table."
        //var wordsp = mojiretsu.split(" ")
        //textView.setText(wordsp[0])

        //var a=scanResults[0].toString().split(",")

        val ssid2 = arrayOfNulls<String>(scanResults.size)
        val macadress2 = arrayOfNulls<String>(scanResults.size)
        val rssi2 = arrayOfNulls<String>(scanResults.size)

        var ssid3 = arrayOfNulls<String>(2)  //受信した全てのWiFiのSSID
        var macadress3 = arrayOfNulls<String>(2)  //受信した全てのWiFiのMACｱﾄﾞﾚｽ
        var rssi3 = arrayOfNulls<String>(2)  //受信した全てのWiFiの受信強度

        //textView.setText(a[0]+a[1])

        var split= arrayOfNulls<String>(scanResults.size)

        var i:Int=0
        for (scanResult in scanResults) {
            split= scanResult.toString().split(",").toTypedArray()
            ssid2[i]=split[0].toString()
            macadress2[i]=split[1].toString()
            rssi2[i]= split[3].toString()

            ssid3= ssid2[i].toString().split(": ").toTypedArray()
            ssid[i]=ssid3[1]
            macadress3= macadress2[i].toString().split(": ").toTypedArray()
            macadress[i]=macadress3[1]
            rssi3= rssi2[i].toString().split(": ").toTypedArray()
            rssi[i]= rssi3[1].toString().toInt()
            i++
        }

        val wifiInfos = realm.where<WifiInfo>().findAll()

        i=0
        var sum_of_square: Int=0
        var min: Int=1000000000
        //var place: String=""

        for (wifiInfo in wifiInfos) {
            for (scanResult in scanResults){
                if (wifiInfo.ssid1==ssid[i]){
                    sum_of_square+=(wifiInfo.level1-rssi[i])*(wifiInfo.level1-rssi[i])
                }
                else if (wifiInfo.ssid2==ssid[i]){
                    sum_of_square+=(wifiInfo.level2-rssi[i])*(wifiInfo.level2-rssi[i])
                }
                else if (wifiInfo.ssid3==ssid[i]){
                    sum_of_square+=(wifiInfo.level3-rssi[i])*(wifiInfo.level3-rssi[i])
                }
                else sum_of_square+=rssi[i]*rssi[i]

                i++
            }

            if (sum_of_square<min){
                min=sum_of_square
                place=wifiInfo.name
                floor=wifiInfo.floor
            }
            i=0
            sum_of_square=0
        }
        textView.setText(place)

        // val myView = MyView(this)
        // setContentView(myView)



        /*if (floor==3) {
            imageView2.visibility = View.VISIBLE
            if (place == "北村研") {
                imageView2.setTranslationX(10F)
                imageView2.setTranslationY(160F)
            }

            //val a:Float = imageView2.getY()
            //imageView2.translationY=10F
        }*/

        for (scanResult in scanResults) {
            Log.d(TAG, scanResult.toString())
        }

        Toast.makeText(applicationContext, "WiFi情報取得", Toast.LENGTH_LONG).show()

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
                //floor=1
                imageView2.visibility = View.INVISIBLE
                return true
            }

            R.id.f2 -> {
                imageView.setImageResource(R.drawable.second)
                //floor=2
                imageView2.visibility = View.INVISIBLE
                return true
            }

            R.id.f3 -> {
                imageView.setImageResource(R.drawable.three)
                //floor=3
                imageView2.visibility = View.INVISIBLE
                if (floor==3){
                    imageView2.visibility = View.VISIBLE
                    if (place=="北村研"){
                        imageView2.setTranslationX(10F)
                        imageView2.setTranslationY(160F)
                    }
                    if (place=="川端研"){
                        imageView2.setTranslationX(10F)
                        imageView2.setTranslationY(80F)
                    }
                    if (place=="片寄研"){
                        imageView2.setTranslationX(10F)
                        imageView2.setTranslationY(0F)
                    }
                    if (place=="井村研"){
                        imageView2.setTranslationX(-80F)
                        imageView2.setTranslationY(-60F)
                    }
                    if (place=="長田研"){
                        imageView2.setTranslationX(-170F)
                        imageView2.setTranslationY(-60F)
                    }
                }

                return true
            }

            R.id.f4 -> {
                imageView.setImageResource(R.drawable.fourth)
                //floor=4
                imageView2.visibility = View.INVISIBLE
                return true
            }
        }
        return true
    }


}

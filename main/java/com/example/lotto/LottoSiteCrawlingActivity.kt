
  package com.example.lotto

import android.content.ClipData
import android.content.Intent
import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.OneShotPreDrawListener.add
import kotlinx.android.synthetic.main.activity_lotto_site_crawling.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Character.toString
import java.lang.StringBuilder
import java.util.Arrays.toString
import java.util.Objects.toString

class LottoSiteCrawlingActivity : AppCompatActivity() {
    var items : ArrayList<ClipData.Item> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto_site_crawling)

        fun getWeb(){
            Thread(Runnable {
                val stb = StringBuilder()
                try {
                    // try {
                    val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
                    val doc = Jsoup.connect(url).get()//타임아웃 10초

                    /*var tableData: Elements = doc.select("div.win_result h4 strong") //회차 id값 가져오기
                    */
                    //val tableData : Elements = doc.select("table tbody tr")
                    val roundNum: Elements = doc.select("div.win_result h4 strong")
                    for (round in roundNum) {
                        val element = roundNum
                        stb.append(round.text() + "\n")
                    }


                    val roundDate: Elements = doc.select("div.win_result p.desc")
                    for (round_date in roundDate) {
                        val element = roundDate
                        stb.append(round_date.text() + "\n")
                    }

                    val BallData: Elements = doc.select("div.num.win p ")
                    for (ball in BallData) {
                        val elements = BallData.select("span")
                        stb.append(ball.text() + "\n")
                    }

                    val bonusBallData: Elements = doc.select("div.num.bonus p ")
                    for (bonusball in bonusBallData) {
                        val elements = bonusBallData.select("span")
                        stb.append(bonusball.text() + "\n")
                    }
                } catch (e: IOException) {

                }
            }).start()
        }
    }
}



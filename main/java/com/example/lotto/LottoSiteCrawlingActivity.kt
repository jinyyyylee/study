
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
import kotlin.math.round

  class LottoSiteCrawlingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto_site_crawling)
        getWeb()
        sitebt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun getWeb() {
        Thread(Runnable {
            val list : MutableList<String> = mutableListOf()
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
                    list.add(round.text())
                }


                val roundDate: Elements = doc.select("div.win_result p.desc")
                for (round_date in roundDate) {
                    val element = roundDate
                    list.add(round_date.text())

                }

                val BallData: Elements = doc.select("div.num.win p span ")

                for (ball in BallData) {
                    val ball1 = BallData[0]
                    val ball2 = BallData[1]
                    val ball3 = BallData[2]
                    val ball4 = BallData[3]
                    val ball5 = BallData[4]
                    val ball6 = BallData[5]
                    list.add(ball.text().trim())
                }

                val bonusBallData: Elements = doc.select("div.num.bonus p ")
                for (bonusball in bonusBallData) {
                    val elements = bonusBallData.select("span")
                    list.add(bonusball.text())
                }
                val moneyData : Elements = doc.select("table tbody tr td.tar strong")
                for(moneyD in moneyData){
                    val money_1 = moneyData[0]
                    val money_2 = moneyData[1]
                    val money_3 = moneyData[2]
                    val money_4 = moneyData[3]
                    val money_5 = moneyData[4]
                    list.add(moneyD.text())
                }
                /*val personData : Elements = doc.select("table tbody td")
                for (personD in personData){
                    // val person_1 = personData[2]
                    list.add(personD.text())
                }*/

                val tr_list : Elements = doc.select("table tbody tr")
                for(tr in tr_list){
                    val i = 0
                    for ( (i, td) in tr.select("td").withIndex()) {
                        if (i == 2) {
                            list.add(td.text())
                        }
                    }

                }

            } catch (e: IOException) {

            }
            this@LottoSiteCrawlingActivity.runOnUiThread ( Runnable {
                this.result_title.text = list[0] + " 당첨결과"
                this.date.text = list[1]
                this.ballnum1.text = list[2]
                this.ballnum2.text = list[3]
                this.ballnum3.text = list[4]
                this.ballnum4.text = list[5]
                this.ballnum5.text = list[6]
                this.ballnum6.text = list[7]
                this.bonusnum.text = list[8]
                this.money1.text = list[9]
                this.money2.text = list[10]
                this.money3.text = list[11]
                this.money4.text = list[12]
                this.money5.text = list[13]
                this.person1.text = list[14]
                this.person2.text = list[15]
                this.person3.text = list[16]
                this.person4.text = list[17]
                this.person5.text = list[18]
            })
        }).start()
    }
}



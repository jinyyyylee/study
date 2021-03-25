package com.example.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lottoselection.*
import kotlinx.android.synthetic.main.activity_lottoselection.*
import java.util.*


class LottoSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottoselection)

        gorandom.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)

            intent.putIntegerArrayListExtra("result", ArrayList(LottoNumberMaker.getShuffleLottoNumbers()))

            startActivity(intent)

        }
        goconstellation.setOnClickListener {
            startActivity(Intent(this, ConstellationActivity::class.java))
        }
        goname.setOnClickListener {
            startActivity(Intent(this, NameActivity::class.java))
        }
    }

    //난수 출력하기
/*    fun getRandomLottoNumber(): Int {
        return Random().nextInt(45) + 1
    }

    fun getRandomLottoNumbers(): MutableList<Int> {
        //무작위로 생성된 로또 번호를 저장할 가변 리스트 생성
        val lottoNumbers = mutableListOf<Int>()

        for (i in 1..6) {

            var number = 0
            do {
                number = getRandomLottoNumber()
            } while (lottoNumbers.contains(number))

            lottoNumbers.add(number)

        }
        return lottoNumbers
    }*/

    //셔플함수 사용하기
    //중복된 숫자가 없다
    fun getShuffleLottoNumbers():MutableList<Int>{
        val list = mutableListOf<Int>()
        for(number in 1..45){
            list.add(number)
        }

        list.shuffle()

        return list.subList(0, 6)
    }
}
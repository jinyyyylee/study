package com.example.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_constellation.*
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {

    val lottoImageStartId = R.drawable.lotto_01
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //전달받은 배열을 가져온다
        val result = intent.getIntegerArrayListExtra("result")
        //전달받은 이름을 가져온다
        val name = intent.getStringExtra("name")

        val constellation = intent.getStringExtra("constellation")
        //결과화면 기본 텍스트
        textView.text = "랜덤으로 생성된 \n 로또번호입니다."
        //name이 전달된 경우 결과화면의 텍스트 변경
        if(!TextUtils.isEmpty(name)){
            textView.text="${name}님의 \n${SimpleDateFormat("yyyy년 MM월 dd일 hh시").format(Date())}\n로또 번호입니다"
        }
        if(!TextUtils.isEmpty(constellation)){
            textView.text="${constellation} 님의 \n${SimpleDateFormat("yyyy년 MM월 dd일").format(Date())}\n로또 번호입니다"
        }
        //전달받은 결과가 있는 경우에만 실행
        result?.let{
            //결과에 맞게 로또 공 이미지 업데이트
            //전달받은 결과는 정렬되어 있지 않으므로 정렬해서 전달한다.
            updateLottoBallImage(result.sortedBy { it })
        }

        resultbt1.setOnClickListener {
            var intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    //결과에 따라 로또공의 이미지를 업데이트한다.
    fun updateLottoBallImage(result: List<Int>){
        //결과의 사이즈가  6개 미만인 경우 에러가 발생할 수 있으므로 바로 리턴한다.
        if(result.size < 6) return

        //lottoview1 이미지부터 순서대로 이미지 아이디가 있기 때문에
        lottoview1.setImageResource(lottoImageStartId+(result[0]-1))
        lottoview2.setImageResource(lottoImageStartId+(result[1]-1))
        lottoview3.setImageResource(lottoImageStartId+(result[2]-1))
        lottoview4.setImageResource(lottoImageStartId+(result[3]-1))
        lottoview5.setImageResource(lottoImageStartId+(result[4]-1))
        lottoview6.setImageResource(lottoImageStartId+(result[5]-1))
    }
}
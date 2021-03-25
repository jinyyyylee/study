package com.example.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_name.*
import java.text.SimpleDateFormat
import java.util.*

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        namebt1.setOnClickListener {
            if(TextUtils.isEmpty(EText.text.toString())) {
                Toast.makeText(this@NameActivity,"입력된 이름이 없습니다. 이름을 입력해주세요",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent = Intent(this, ResultActivity::class.java)

            //intent의 결과 데이터를 전송
            //Int의 list를 전달하므로 putIntegerArrayListExtra 사용
            intent.putIntegerArrayListExtra("result",ArrayList(LottoNumberMaker.getLottoNumbersFromHash(EText.text.toString())))

            intent.putExtra("name",EText.text.toString())

            startActivity(intent)
        }
        namebt2.setOnClickListener {
            //startActivity(Intent(this,MainActivity::class.java))
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
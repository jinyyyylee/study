package com.example.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bt1.setOnClickListener {
            val intent = Intent(this, LottoSelection::class.java)
            startActivity(intent)
        }
        main_bt2.setOnClickListener {
            val intent = Intent(this, LottoSiteCrawlingActivity::class.java)
            startActivity(intent)
        }
    }
}
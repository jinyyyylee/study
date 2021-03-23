package com.example.lotto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_constellation.*
import java.util.*

class ConstellationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellation)


        constellationbt.setOnClickListener {

            val intent = Intent(this,ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result",ArrayList(LottoNumberMaker.getLottoNumbersFromHash(makeConstellationString(
                    datepicker.month,datepicker.dayOfMonth))))
            intent.putExtra("constellation",makeConstellationString(datepicker.month, datepicker.dayOfMonth))

            startActivity(intent)
        }
        //현재 DatePicker의 월, 일 정보로 별자리 텍스트 변경
        constext.text = makeConstellationString(datepicker.month, datepicker.dayOfMonth)
        //DatePicker의 날짜가 변화하면 별자리를 보여주는 텍스트뷰도 변경
        val calendar = Calendar.getInstance()
        datepicker.init( calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.YEAR),
        object:CalendarView.OnDateChangeListener, DatePicker.OnDateChangedListener{
            override fun onDateChanged(view: DatePicker?,  monthOfYear: Int, dayOfMonth: Int, year: Int) {
                constext.text = makeConstellationString(datepicker.month, datepicker.dayOfMonth)
            }

            override fun onSelectedDayChange(view: CalendarView, month: Int, dayOfMonth: Int, year: Int) {
            }
        })
    }
    fun makeConstellationString(month: Int, day :Int):String{
        //전달받은 월,일 정보를 기반으로 정수형태의 값을 만든다
        val target = "${month+1}${String.format("%02d", day)}".toInt()
        when(target){
            in 101..119 ->return "염소자리"
            in 120..218 ->return "물병자리"
            in 219..320 ->return "물고기자리"
            in 321..419 ->return "양자리"
            in 420..520 ->return "황소자리"
            in 521..621 ->return "쌍둥이자리"
            in 622..722 ->return "게자리"
            in 723..822 ->return "사자자리"
            in 823..823 ->return "처녀자리"
            in 924..1022 ->return "천칭자리"
            in 1023..1122 ->return "전갈자리"
            in 1123..1224 ->return "사수자리"
            in 1225..1231 ->return "염소자리"
            else ->return "기타 별자리"

        }
    }
}
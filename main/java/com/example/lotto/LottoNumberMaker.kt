package com.example.lotto

import java.text.SimpleDateFormat
import java.util.*

object LottoNumberMaker {



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
    }
    fun getRandomLottoNumber(): Int {
        return Random().nextInt(45) + 1
    }
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
    fun getLottoNumbersFromHash(name:String):MutableList<Int>{
        val list = mutableListOf<Int>()

        for(number in 1..45){
            list.add(number)
        }
        //SimpleDateFormat은 날짜의 시간값을 포맷화된 텍스트 형태로 바꿔주는 클래스
        //date의 문자열과 이름 문자열을 합쳐준다.
        val targetString = SimpleDateFormat("yyyy-MM-dd-HH",Locale.KOREA).format(Date())+name
        //list를 무작위로 섞고 이때 섞는 기준을 Random(SEED)를 사용한다.
        //SEED가 같다면 동일한 난수를 배출
        list.shuffle(Random(targetString.hashCode().toLong()))

        return list.subList(0, 6)
    }
}
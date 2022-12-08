package com.example.fullstackapplication

//데이터 클래스로 정의  생성자 게터세터 자동으로 정리 data
data class ContactVO(val name : String, val age: Int, val tel : String) {


    // 만약, FireBase의 ReadlTime Database 용으로
    // 사용되는 VO Class 라면, 반드시!! 기본 생성자가 정의되어야 한다!

    //코틀린에서의 기본생성자
    constructor(): this("",0,"")
}
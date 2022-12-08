package com.example.fullstackapplication

data class ChatVO(val name: String, val msg: String ) {

    //반드시 FireBase ReadlTime DataBase 사용하기 위해서
    //기본 생성자 필수

    constructor() : this("","")
}
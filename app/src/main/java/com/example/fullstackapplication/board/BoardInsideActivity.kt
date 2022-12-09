package com.example.fullstackapplication.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.fullstackapplication.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    lateinit var imgIn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        val tvInTitle=findViewById<TextView>(R.id.tvInTitle)
        val tvInTime=findViewById<TextView>(R.id.tvInTime)
        val tvInContent=findViewById<TextView>(R.id.tvInContent)
         imgIn=findViewById<ImageView>(R.id.imgIn)

        val content= intent.getStringExtra("content")
        val time= intent.getStringExtra("time")
        val title= intent.getStringExtra("title")
        val key= intent.getStringExtra("key")

        tvInContent.text=content.toString()
        tvInTime.text=time.toString()
        tvInTitle.text=title.toString()

        //게시물의 uid값으로 이름을 지정
        getImageData(key.toString())
    }
    // Image를 가져오는 함수 만들기
    fun getImageData(key : String){
        val storageReference = Firebase.storage.reference.child("$key.png")

        storageReference.downloadUrl.addOnCompleteListener { task->
            if (task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imgIn)

            }
        }


    }
}
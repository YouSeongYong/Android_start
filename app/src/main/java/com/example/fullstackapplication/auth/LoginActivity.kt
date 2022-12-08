package com.example.fullstackapplication.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fullstackapplication.MainActivity
import com.example.fullstackapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // FirebaseAuth 선언
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //안에서만 할껀지 밖에서 할건지
        val sharedPreferences=getSharedPreferences("autoLogin",Context.MODE_PRIVATE)
        val loginId =sharedPreferences.getString("loginId","")
        val loginPw =sharedPreferences.getString("loginPw","")

        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        // 위와 같이 loginId중복 되도 되는 이유는 프리페어런스  키값이 autoLogin 안에 loginId //loginInfo 안에 loginId
        val loginName=sp.getString("loginId","null")

        //FirebaseAuth 초기화
        auth=Firebase.auth

        val btnLoingLogin=findViewById<Button>(R.id.btnLoginLogin)
        val etLoginEmail=findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPw=findViewById<EditText>(R.id.etLoginPw)
        etLoginEmail.setText(loginId)
        etLoginPw.setText(loginPw)

        btnLoingLogin.setOnClickListener {
            val email=etLoginEmail.text.toString()
                val pw= etLoginPw.text.toString()

            auth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(this){task->
                if( task.isSuccessful){
                    //로그인에 성공
                    Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                    //로그인 성공하면 메인 액티비티로 이동
                    //데이터를 삽입 수정 삭제
                    val editor=sharedPreferences.edit()
                    editor.putString("loginId",email)
                    editor.putString("loginPw",pw)
                        editor.commit()

                    val editorSp=sp.edit()
                    editorSp.putString("loginId",email)
                    editorSp.commit()


                    val intent= Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish() // 메인에서 뒤로가기 누르면 어플 종료
                }else{
                    //로그인에 실패
                    Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
                }
            }

            Toast.makeText(this@LoginActivity,"$email, $pw",Toast.LENGTH_SHORT).show()
        }
    }
}

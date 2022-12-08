package com.example.fullstackapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.fullstackapplication.auth.IntroActivity
import com.example.fullstackapplication.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= Firebase.auth
    //View 들 찾아오기
        val fl=findViewById<FrameLayout>(R.id.fl)
        val bnv=findViewById<BottomNavigationView>(R.id.bnv)
        val imgLogout=findViewById<ImageView>(R.id.imgLogout)

        imgLogout.setOnClickListener {
            auth.signOut()
            // 로그아웃하고나면 IntroActivity로 이동
            val intent=Intent(this,IntroActivity::class.java)
            // 이전에 쌓여있는 Activity를 모두 날려주기
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        //auth에 담겨있는 기능
        // createUserWithEmailandPassword : 회원가입 (email, pw)
        // SignInWithEmailandPassWord : 로그인 (email, pw)
        // SignInAnonymous : 익명로그인 ()
        // signOut() : 로그아웃( 페이지를 이동하는 기능 x)






            bnv.setOnItemSelectedListener { item->
                // item : 내가 클릭한 item 정보

                //아이템에 아이템아이디 뭔지 판단
                when(item.itemId){
                    R.id.tab1 ->{
                        //Fragment1 부분화면으로 fl에 갈아끼워준다
                        // 프로그래먼트가 삭제되고 추가되고 변경되는 거 도움주는거
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl,

//                            ContactFragment()
                            //프래그먼트 1 컨택트 프래그먼트 갈아끼워줄수 있다
                            Fragment1()
                        ).commit()
                    }
                    R.id.tab2 ->{
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl,
                            Fragment2()
                        ).commit()
                    }
                    R.id.tab3 ->{
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl,
                            Fragment3()
                        ).commit()

                    }
                    R.id.tab4 ->{
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl,
                            Fragment4()
                        ).commit()

                    }
                    R.id.tab5 ->{
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fl,
                           ChatFragment()
//                            Fragment5()
                        ).commit()

                    }
                }


                true
            }
    }

}
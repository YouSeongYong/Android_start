package com.example.fullstackapplication.tip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListActivity : AppCompatActivity() {

    lateinit var adapter: ListAdapter //전역변수
    //게시물의 uid 값이 들어갈 가변 배열
    var keyData =ArrayList<String>()
    // bookmark 경로 설정을 위한 선언
    lateinit var bookmarkRef: DatabaseReference
    // bookmark 된 게시물의 정보가 들어갈 배열
    var bookmarkList= ArrayList<String>()


    var auth : FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        // realTime에 Database에 필요한 객체 선언
        val database = Firebase.database
        // database 에 어떤 것을 참조할건지
        val allContent= database.getReference("content")
        bookmarkRef=database.getReference("bookmarklist")
        // Fragment2에서 전체보기를 눌렀을 때 가져올 데이터 담기는곳

        //push() : key 타임스탬프 찍어줌(고유한값을 만들어줌)
//        allContent.push().setValue(
//            ListVO("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png","집","https://philosopher-chan.tistory.com/1243")
//        )

        val rv= findViewById<RecyclerView>(R.id.rv)
        val tvCategory= findViewById<TextView>(R.id.tvCategory)

        // Fragment2에서 intent 를 통해 보낸 데이터를 꺼내주기
        //인텐트 안에 있는 내용을 떼준다
        val category=intent.getStringExtra("category")
        //tvCategory 안에 위에 값을 넣어준다
        tvCategory.text=category



        //TextView
        //RecyclerView ---> Adapter, data(VO), template(xml)

        //Adapter ---> ListAdapter
        // data(VO) ---> ListVO
        // template ---> layout폴더에 list_template.xml

        // 이미지의 id(Int), title (String) ---> VO로 묶어야할 데이터
        // 3-4개 정도 임의로 만들어 놓기
        val data= ArrayList<ListVO>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            Log.d("스냅샷",snapshot.toString())
                Log.d("스냅샷2",snapshot.value.toString())
                for (model in snapshot.children){
                    val item =model.getValue(ListVO::class.java)
                    // model에는 여러가지 게시물이 담겨있고
                    // 1개에 대한 게시물에 접근하기 위해 value를  ListVO
                    if (item != null) {
                        data.add(item)
                    }
                    keyData.add(model.key.toString())
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }
        allContent.addValueEventListener(postListener)
        getBookmarkData()
        // content
        // uid (게시물구분할 수 있는 uid)
        //      imgId
        //      title
        //      url




//        data.add(ListVO("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png","집","https://philosopher-chan.tistory.com/1243"))
//        data.add(ListVO("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdeOvU7%2Fbtq67OezAVX%2FKFfG52YKamCEFfjwRpNQf1%2Fimg.png","스토어","https://philosopher-chan.tistory.com/1243"))
//        data.add(ListVO("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbKtXON%2Fbtq64LJ5BES%2FxKCbKBoFKBVl5qYq7hBFT1%2Fimg.png","꿀팁","https://philosopher-chan.tistory.com/1243"))
//        data.add(ListVO("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FK917N%2Fbtq64SP5gxj%2FNzsfNAykamW7qv1hdusp1K%2Fimg.png","지도보기","https://philosopher-chan.tistory.com/1243"))
        //template.xml -> imageView하나, TextView, 북마크 Image View
//        for(i in 0 until data.size){
//            allContent.push().setValue(
//                data[i]
//            )
//        }

        //Adapter : 리사이클러뷰를 상속받게 만들어주세요
        // ViewHolder, onCreateView, OnbindView, getItemCount

        adapter=ListAdapter(this@ListActivity,data, keyData,bookmarkList)
        // ListActivity에서 내가만든 ListAdapter를 rv에 적용하기
        // 단 GridLayoutManager를 사용해서 두줄로 쌓이게 만들자

        rv.adapter=adapter
        rv.layoutManager=GridLayoutManager(this,2)
    }

    //bookmarklist에 저장되어 있는 데이터를 가져오는 함수
    fun getBookmarkData(){
        val postListener2 = object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bookmarkList.clear()
                // ListActivity를 들릴때 마다
                // 데이터가 누적
                // 1---> 2---> 4
            for(model in snapshot.children){
                Log.d("bookmark",model.toString())
                Log.d("bookmark",model.key.toString())
                // 북마크 게시물의 uid값을 bookmarkList에 저장
                // bookmark.add (model.value.toString())
                bookmarkList.add(model.key.toString())
                Log.d("datasize",bookmarkList.size.toString())
            }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        // bookmarklist 경로에 있는 데이터를 snapshot으로 받아옴
        bookmarkRef.child(auth.currentUser!!.uid).addValueEventListener(postListener2)

    }
}
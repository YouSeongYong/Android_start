package com.example.fullstackapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.R
import com.example.fullstackapplication.tip.BookmarkAdapter
import com.example.fullstackapplication.tip.ListVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Fragment4 : Fragment() {


    // Firebase
    var auth : FirebaseAuth=Firebase.auth
    var database= Firebase.database
    val contentRef = database.getReference("content")
    val bookmarkRef= database.getReference("bookmarklist")

    //ArrayList
    var data= ArrayList<ListVO>()
    var keyData= ArrayList<String>()// ListVO를 포함하는 게시물의 uid 값
    var bookmarkList = ArrayList<String>() // 내가 선택한 게시물 uid


    // Adatper 선언
    lateinit var adapter : BookmarkAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         var view=inflater.inflate(R.layout.fragment_4, container, false)
        val bookmarkRv= view.findViewById<RecyclerView>(R.id.bookmarkRv)

        // 1.전체 컨텐츠 데이터들을 다 가져오자
        getContentData()
        // 2. 사용자가 북마크한 정보를 다 가져오자
        getBookmarkData()// bookList를 다 가지고온 뒤쪽에 getContent
        // 가 실행되고 있음
        // getContentData가 하고 있는 일은
        // 전체 데이터를 가져오는게 아니라
        // bookmarkList에 있는 것만 가져오는 역할
        // 3. 전체 컨텐츠 중에서 사용자가 북마크한 정보만 recyclerView에 반영
        //프래그 먼트라 리콰이어액티비티에 요청
        adapter= BookmarkAdapter(requireActivity(),data,keyData,bookmarkList)
        // 4. adapter 적용
        bookmarkRv.adapter=adapter

        bookmarkRv.layoutManager=GridLayoutManager(requireContext(),2)


        return view
    }

    // 전체보기에 있는 게시물중에서 북마크가 찍힌거(VO)를 가져와야함
   fun getContentData(){
       // content경로에 있는 데이터를 다 가지고 오자
       // uid --> keyData
       // ListVO --> data
       val posterListener=object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
            for(model in snapshot.children){
                val item=model.getValue(ListVO::class.java)
                // bookmarkList에 값이 채워져있어야함
               if(bookmarkList.contains(model.key.toString())){
                   if(item!=null){
                       data.add(item)
                       // data 내가 북마크 찍은 게시물만 담김
                   }
                keyData.add(model.key.toString())
               }
            }
               // adapter 새로 고침
               adapter.notifyDataSetChanged()

           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       }

       contentRef.addValueEventListener(posterListener)
       // snapshot : content 경로에 있는 데이터 전부


   }
    fun getBookmarkData(){
        //bookmarklist경로에 있는 데이터를 다 가지고 오자
        // 게시물의 uid값 ---> bookmarkList
        val postlistener2= object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bookmarkList.clear()
                for(model in snapshot.children){
                    bookmarkList.add(model.key.toString())
                }
                // 어댑터 새로고침 하기
                adapter.notifyDataSetChanged()
                getContentData()
                // bookmarkList에 있는 데이터만 가지고와서 data(ArrayListM<vo)에 담아 주기
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        bookmarkRef.child(auth.currentUser!!.uid).addValueEventListener(postlistener2)
    }


    //반복되는 코드들이 ---> 함수로 사용
}
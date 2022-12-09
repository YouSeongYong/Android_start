package com.example.fullstackapplication.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.R
import com.example.fullstackapplication.board.BoardAdapter
import com.example.fullstackapplication.board.BoardInsideActivity
import com.example.fullstackapplication.board.BoardVO
import com.example.fullstackapplication.board.BoardWriteActivity
import com.example.fullstackapplication.utils.FBdatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class Fragment3 : Fragment() {

    // getBoardData를 통해 받아온 item(BoardVO)을 관리하는 배열
    var boardList =ArrayList<BoardVO>()
    lateinit var  adapter : BoardAdapter
    var keyData= ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //btnWrite 클릭하면 BoardWriteActivity 로 이동
        var view= inflater.inflate(R.layout.fragment_3, container, false)
        val boardRv= view.findViewById<RecyclerView>(R.id.boardRv)
        val btnWrite=view.findViewById<Button>(R.id.btnWrite)

        btnWrite.setOnClickListener {
            val intent= Intent(requireActivity(),BoardWriteActivity::class.java)
            startActivity(intent)
        }

        //1. 한칸에 들어갈 디자인 만들기 (board_list.xml)
        //2. Apater에 보낼 데이터 가져오기
        // Firebase에 있는 board경로에 있는 데이터를 가져오기
        getBoardData()
        // 3. Adapter 만들기 ( data )
        adapter=BoardAdapter(requireContext(),boardList)
        // 4. boardRv에 adapter 적용
        boardRv.adapter=adapter
        boardRv.layoutManager=LinearLayoutManager(requireContext())

        //클릭 이벤트를 호출
        adapter.setOnItemClickListener(object :BoardAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
               // BoardInsideActivity로 넘어가자

                val intent= Intent(requireContext(),BoardInsideActivity::class.java)
                intent.putExtra("title",boardList[position].title)
                intent.putExtra("time",boardList[position].time)
                intent.putExtra("content",boardList[position].content)
                intent.putExtra("key",keyData[position])
                startActivity(intent)
            }

        })



        return view
    }

    // board에 있는 데이터 다 ~~ 가져오는 함수
    fun getBoardData(){

        val postListener=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            //Firebase에서 snapshot으로 데이터를 받아온 경우
                //게시물의 uid
                //      -BoardVO
                for(model in snapshot.children){
                    val item = model.getValue(BoardVO::class.java)
                    if(item!=null){
                        boardList.add(item)
                    }
                    keyData.add(model.key.toString())
                    // img가 key값으로 이름이 설정 되어 있음
                }
                // adapter를 새로고침
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            //오류가 발생했을 경우 실행되는 함수
            }

        }
        // board에 있는 모든 ~~ 데이터가 들어간다 ~
        FBdatabase.getBoardRef().addValueEventListener(postListener)


    }



}
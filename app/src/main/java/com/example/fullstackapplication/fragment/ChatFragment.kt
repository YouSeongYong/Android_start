package com.example.fullstackapplication.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.ChatAdapter
import com.example.fullstackapplication.ChatVO
import com.example.fullstackapplication.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_chat, container, false)


        val etChat= view.findViewById<EditText>(R.id.etChat)
        val btnChat= view.findViewById<Button>(R.id.btnChat)

        // AdapterView 6단계
        // 1. Container 결정

        val rvChat= view.findViewById<RecyclerView>(R.id.rvChat)

        // 2. Template 결정
        // chat_list.xml

        //3. Item 결정
        // ChatVO
        val chatList=ArrayList<ChatVO>()
        // 4. Adapter 결정
        //프리페어런스 가져오기
        val sp= activity?.getSharedPreferences("loginInfo",Context.MODE_PRIVATE)

        val loginId =sp?.getString("loginId","null") as String

        val adapter=ChatAdapter(requireContext(), chatList ,loginId)

        //5. Container 에 Adapter 부착
        rvChat.adapter=adapter
        rvChat.layoutManager=LinearLayoutManager(requireContext())

        // 6. Event처리

        val db= Firebase.database
        val chatRef= db.getReference("chat")



        btnChat.setOnClickListener {

            val msg=etChat.text.toString()
            // Firebase RealTime Database 의
            // chat 경로에 chatVO class를 setValue
            val chat= ChatVO(loginId,msg)

            chatRef.push().setValue(chat)
//            etChat.text=null
            etChat.setText("")
        }

        chatRef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem= snapshot.getValue<ChatVO>() as ChatVO
                chatList.add(chatItem)
                //어탭터 새로고침
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        return view


    }


}
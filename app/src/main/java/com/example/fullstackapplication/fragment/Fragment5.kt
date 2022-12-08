package com.example.fullstackapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.ContactAdapter
import com.example.fullstackapplication.ContactVO
import com.example.fullstackapplication.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class Fragment5 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_5, container, false)

        //AdapterView 6단계
        // 1.Container 결정

        val rvContact= view.findViewById<RecyclerView>(R.id.rvContact)

        // 2.Template 결정
        // Contact_list.xml

        // 3. Item 결정
        // ContactVO

        val contactList= ArrayList<ContactVO>()
        val db= Firebase.database
        val contact2 =db.getReference("contact2")




        // 4. Adapter 결정
        // requireContext() : Fragment 의 페이지 정보 호출
        val adapter = ContactAdapter(requireContext(),contactList)


        // 5. Container 에 Adapter 부착
        rvContact.adapter=adapter
        rvContact.layoutManager=LinearLayoutManager(requireContext())

        contact2.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val contact= snapshot.getValue<ContactVO>() as ContactVO
                contactList.add(contact)
                //추가가 완료된 이후
                // 어댑터 새로고침
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
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
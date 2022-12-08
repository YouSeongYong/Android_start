package com.example.fullstackapplication.tip

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fullstackapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListAdapter(var context:Context, var data:ArrayList<ListVO>, var keyData:ArrayList<String>,var bookmarkList:ArrayList<String>):
    //BaseAdapter --> 일반 ListView
    // RecyclerView --> RecyclerViewAdapter 상속

    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    val database = Firebase.database
    val auth : FirebaseAuth= Firebase.auth
//온크리에이트에서 리턴 보낸  인플레이터 저장
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

      val tvTitle : TextView
      val imgContext : ImageView
      val imgBookmark : ImageView

      init{
          //온크리에이트에서 인플레이트 해서 보내줘서 파인드뷰 사용가능
          tvTitle=itemView.findViewById(R.id.tvTitle)
          imgContext=itemView.findViewById(R.id.imgContent)
          imgBookmark=itemView.findViewById(R.id.imgMark)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate : xml코드를 눈에보이는 View객체로 바꿔서 ViewHolder로 보내주는 역할
        val layoutInflater=LayoutInflater.from(context)
        // getSystemService  <--사용하는 이유 : 하드웨어가 가지고 있는 많은 센서 서비스들이 담겨있음
       val view= layoutInflater.inflate(R.layout.list_template,null)

        //뷰홀더에 뷰를 리턴하겠다
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text=data.get(position).title
//        holder.imgContext.setImageResource((data[position].imgId))
        Glide.with(context).load(data[position].imgId)
            .into(holder.imgContext)

        // 첫번째
        //imgView를 클릭했을때
        holder.imgContext.setOnClickListener {

            val intent=Intent(context,WebViewActivity::class.java)

        // url값을 가지고 WebViewActivity로 넘어간다
            intent.putExtra("url",data.get(position).url )
            context.startActivity(intent)
        }
        // 클릭했을 떄 색깔을 바꾸면 기존에 있던 북마크는 색이 안칠해져있음
        // adapter가 실행이 되는 순간 북마크로 있던 데이터들은 바로 색칠 될수 있게
        if(bookmarkList.contains(keyData[position])){
            holder.imgBookmark.setImageResource(R.drawable.bookmark_color)
        }else{
            holder.imgBookmark.setImageResource(R.drawable.bookmark_white)

        }

        //북마크 모양의 이미지를 클릭했을 떄
        // 해당 게시물의 uid값이 bookmarklist경로로 들어가야함
            holder.imgBookmark.setOnClickListener {
                // Firebase에 있는 bookmarklist 경로로 접근
                val bookmarkRef=database.getReference("bookmarklist")
                bookmarkRef.child(auth.currentUser!!.uid).child(keyData[position]).setValue("good")

                // 이미 저장이 되어있는 게시물인지 아닌지
                // bookmarklist에 해당 게시물이 포함되어 있는지
                if(bookmarkList.contains(keyData[position])){
                    //북마크를 취소
                    // database에서 해당 keyDaya를 삭제
                    bookmarkRef.child(auth.currentUser!!.uid).child(keyData[position]).removeValue()
                    // imgbookmark를 하얗게 만들자
//                    holder.imgBookmark.setImageResource(R.drawable.bookmark_white)
                }else{
                    // 북마크를 추가
                    //database에 해당 keyData를 추가
                    bookmarkRef.child(auth.currentUser!!.uid).child(keyData[position]).setValue("good")
                    //imgbookmark를 까맣게 만들자
//                    holder.imgBookmark.setImageResource(R.drawable.bookmark_color)
                }
            }
    }

    override fun getItemCount(): Int {
    return data.size
    }
}
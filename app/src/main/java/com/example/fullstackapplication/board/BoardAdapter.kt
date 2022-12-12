package com.example.fullstackapplication.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.fullstackapplication.R

// Fragment3에 있는 boardRv에 적용될 Adpater
class BoardAdapter(var context: Context, var boardList : ArrayList<BoardVO>)
    :RecyclerView.Adapter<BoardAdapter.ViewHolder>(){

    // 리스너 커스텀
    interface OnItemClickListener{
        fun onItemClick(view:View,position: Int)
    }

    // 객체 저장 변수 선언
    lateinit var mOnItemClickListener:OnItemClickListener

    // 객체 전달 메서드
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        mOnItemClickListener=onItemClickListener
    }
//안녕
        inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
            val tvRvTitle : TextView
            val tvRvContent : TextView
            val tvRvTime: TextView

            init{
                tvRvTitle=itemView.findViewById(R.id.tvRvTitle)
                tvRvContent=itemView.findViewById(R.id.tvRvContent)
                tvRvTime=itemView.findViewById(R.id.tvRvTime)

                itemView.setOnClickListener{
                    val position= adapterPosition
                    if(position!=RecyclerView.NO_POSITION){
                        mOnItemClickListener.onItemClick(itemView,position)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.board_template,null)

        //파인드 바이 아이디 하는 뷰홀더한테 보내줌
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRvTitle.text=boardList[position].title
        holder.tvRvContent.text=boardList[position].content
        holder.tvRvTime.text=boardList[position].time
    }

    override fun getItemCount(): Int {
       return boardList.size
    }
}
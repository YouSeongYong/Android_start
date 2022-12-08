package com.example.fullstackapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context: Context,val chatList:ArrayList<ChatVO>, val loginId: String)
    :RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

        inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

            val imgChat : ImageView
            val tvChatOtherName : TextView
            val tvChatOtherMsg : TextView
            val tvChatMyMsg : TextView

            init{
                imgChat=itemView.findViewById(R.id.imgChat)
                tvChatOtherName=itemView.findViewById(R.id.tvChatOtherName)
                tvChatOtherMsg=itemView.findViewById(R.id.tvChatOtherMsg)
                tvChatMyMsg=itemView.findViewById(R.id.tvChatMyMsg)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater=LayoutInflater.from(context)
        val  view=layoutInflater.inflate(R.layout.chat_list,null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val name= chatList.get(position).name

        if(loginId==name){
            holder.imgChat.visibility=View.GONE
            holder.tvChatOtherName.visibility=View.GONE
            holder.tvChatOtherMsg.visibility=View.GONE

            holder.tvChatMyMsg.visibility=View.VISIBLE

            holder.tvChatMyMsg.setText(chatList.get(position).msg)
        }else{

            holder.imgChat.visibility=View.VISIBLE
            holder.tvChatOtherName.visibility=View.VISIBLE
            holder.tvChatOtherMsg.visibility=View.VISIBLE

            holder.imgChat.setImageResource(R.drawable.dog)
            holder.tvChatOtherName.setText(chatList.get(position).name)
            holder.tvChatOtherMsg.setText(chatList.get(position).msg)


            holder.tvChatMyMsg.visibility=View.GONE
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}
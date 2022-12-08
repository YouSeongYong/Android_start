package com.example.fullstackapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val context: Context,val contactList: ArrayList<ContactVO>)
    :RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imgContact : ImageView
        val tvContactName : TextView
        val tvContactAge : TextView
        val tvContactTel : TextView
        val btnContactCall : Button

        init{
            imgContact=itemView.findViewById(R.id.imgContact)
            tvContactName=itemView.findViewById(R.id.tvContactName)
            tvContactAge=itemView.findViewById(R.id. tvContactAge)
            tvContactTel=itemView.findViewById(R.id.tvContactTel)
            btnContactCall=itemView.findViewById(R.id.btnContactCall)

            btnContactCall.setOnClickListener {
                val position=adapterPosition
                val tel= contactList.get(position) as String
                Toast.makeText(context,tel,Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater= LayoutInflater.from(context)
        // xml 파일을 변환
        val view= layoutInflater.inflate(R.layout.contact_list,null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgContact.setImageResource(R.drawable.dog)
        holder.tvContactName.setText(contactList.get(position).name)
        holder.tvContactAge.setText(contactList.get(position).age.toString())
        holder.tvContactTel.setText(contactList.get(position).tel)

    }

    override fun getItemCount(): Int {
     return contactList.size
    }
}
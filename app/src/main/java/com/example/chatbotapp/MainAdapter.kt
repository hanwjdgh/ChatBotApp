package com.example.chatbotapp

import android.annotation.TargetApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.RelativeLayout


class MainAdapter(private var context: Int, private var chatlist: List<MainData>) : RecyclerView.Adapter<MainAdapter.ViewHoler>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ViewHoler {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_my_chat, parent, false)
        return ViewHoler(view)
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item : MainData = chatlist[position]

        if(item.index == 0) {
            holder.ailayout.visibility = View.GONE
            holder.mylayout.visibility = View.VISIBLE
            holder.mycontents.text = item.content
            holder.myimage.background = ShapeDrawable(OvalShape())
            holder.myimage.clipToOutline = true
        }

        else{
            holder.mylayout.visibility = View.GONE
            holder.ailayout.visibility = View.VISIBLE
            holder.aicontents.text = item.content
            holder.aiimage.background = ShapeDrawable(OvalShape())
            holder.aiimage.clipToOutline = true
        }
    }

    inner class ViewHoler(view: View?) : RecyclerView.ViewHolder(view!!){
        val mylayout = view!!.findViewById<RelativeLayout>(R.id.myChatLayout)
        val myimage = view!!.findViewById<ImageView>(R.id.myImgView)
        val mycontents = view!!.findViewById<TextView>(R.id.my_content)

        val ailayout = view!!.findViewById<RelativeLayout>(R.id.aiChatLayout)
        val aiimage = view!!.findViewById<ImageView>(R.id.aiImgView)
        val aicontents = view!!.findViewById<TextView>(R.id.ai_content)
    }
}
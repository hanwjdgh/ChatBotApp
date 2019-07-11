package com.example.chatbotapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var chatArray = ArrayList<MainData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chat_list.layoutManager = LinearLayoutManager(this)

        sendMsgBtn.setOnClickListener {
            if(msgEditText.text.toString().isNotEmpty()){
                var data = MainData(0,msgEditText.text.toString())
                chatArray.add(data)
                msgEditText.setText("")

                data = MainData(1,"AI 입니다")
                chatArray.add(data)

                chat_list.scrollToPosition(chatArray.size-1)
                chat_list.adapter = MainAdapter(0, chatArray)
                (chat_list.adapter as MainAdapter).notifyDataSetChanged()
            }
        }
    }
}

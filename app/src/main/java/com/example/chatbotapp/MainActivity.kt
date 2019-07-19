package com.example.chatbotapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.StrictMode
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private var clientSocket: Socket? = null
    private var socketIn:BufferedReader? = null
    private var socketOut:PrintWriter? = null
    private var port = 8888
    private var ip = "192.168.56.1"

    private var myHandler: MyHandler? = null
    private var myThread: MyThread? = null

    var chatArray = ArrayList<MainData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        chat_list.layoutManager = LinearLayoutManager(this)

        try {
            clientSocket = Socket(ip, port)
            socketIn = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            socketOut = PrintWriter(clientSocket!!.getOutputStream(), true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        myHandler = MyHandler()
        myThread = MyThread()
        myThread!!.start()

        sendMsgBtn.setOnClickListener {
            if(msgEditText.text.toString().isNotEmpty()){
                var data = MainData(0,msgEditText.text.toString())
                chatArray.add(data)
                socketOut?.println(msgEditText.text.toString())
                msgEditText.setText("")

                data = MainData(1,"AI 입니다")
                chatArray.add(data)

                chat_list.scrollToPosition(chatArray.size-1)
                chat_list.adapter = MainAdapter(0, chatArray)
                (chat_list.adapter as MainAdapter).notifyDataSetChanged()
            }
        }
    }

    internal inner class MyThread : Thread() {
        override fun run() {
            while (true) {
                try {
                    val data = socketIn!!.readLine()
                    val msg = myHandler!!.obtainMessage()
                    msg.obj = data
                    myHandler!!.sendMessage(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    internal inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
//            setText(msg.obj.toString())
        }
    }
}

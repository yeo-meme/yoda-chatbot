package com.example.chatbotexample

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chatbotexample.databinding.ChatItemEmptyBinding
import com.example.chatbotexample.databinding.ChatItemImageBinding
import com.example.chatbotexample.databinding.ChatItemTalkAdminBinding
import com.example.chatbotexample.databinding.ChatItemTalkUserBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adminTalkList: ArrayList<String>
    private lateinit var userTalkList: ArrayList<String>
    private var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        adminTalk()
        userTalk()
        Handler().postDelayed({
            adminAddView(object : OnEndCallback {
                override fun end() {
                    cnt = 0
                    Handler().postDelayed({ userAddView() }, 1000)
                }
            })
        }, 1000)
    }

    private fun adminTalk() {
        adminTalkList = arrayListOf(
            "안녕하세요",
            "방가워요",
            "질문이 있나요?",
            "없으면 사요나라~"
        )
    }

    private fun userTalk() {
        userTalkList = arrayListOf(
            "갑자기 요다는 뭐죵?",
            "방갑습니다.",
            "질문있는데 없다뇨!",
            "사요나라~!!"
        )
    }

    private fun adminAddView(callback: OnEndCallback) {
        if (adminTalkList.size > cnt) {
            val textView = ChatItemTalkAdminBinding.inflate(layoutInflater, view_container, false).root as TextView
            val emptyView = ChatItemEmptyBinding.inflate(layoutInflater, view_container, false).root
            textView.text = adminTalkList[cnt++]
            with(view_container) {
                addView(textView)
                addView(emptyView)
            }
            Handler().postDelayed({ adminAddView(callback) }, 1000)
        } else {
            with(view_container) {
                val imageView = ChatItemImageBinding.inflate(layoutInflater, view_container, false).root as ImageView
                val emptyView = ChatItemEmptyBinding.inflate(layoutInflater, view_container, false).root
                imageView.setImageResource(R.drawable.test_img)
                addView(imageView)
                addView(emptyView)
                callback.end()
            }
        }
    }

    private fun userAddView() {
        scroll_view.post {
            scroll_view.fullScroll(ScrollView.FOCUS_DOWN)
        }
        if (userTalkList.size > cnt) {
            val linearLayout = ChatItemTalkUserBinding.inflate(layoutInflater, view_container, false).root
            val emptyView = ChatItemEmptyBinding.inflate(layoutInflater, view_container, false).root
            linearLayout.findViewById<TextView>(R.id.tv_text).text = userTalkList[cnt++]
            with(view_container) {
                addView(linearLayout)
                addView(emptyView)
            }
            Handler().postDelayed(this::userAddView, 1000)
        }
    }

}

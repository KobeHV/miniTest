package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import com.hit.software.exam.LoginCheck
import kotlinx.android.synthetic.main.activity_paper.*
import kotlinx.android.synthetic.main.list_item.*

class Paper : AppCompatActivity(){

    var userName:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper)

        userName = intent.getStringExtra("username")

        /**
         * 联网获得数据
         */

        var mPaperList: ArrayList<PaperItem> = arrayListOf()
        /**
         * 新建一些测试数据
         */
        mPaperList.add(PaperItem(1, "Paper1"))//为什么不能用中文？
        mPaperList.add(PaperItem(2, "Paper2"))
        mPaperList.add(PaperItem(3, "Paper3"))
        mPaperList.add(PaperItem(4, "Paper4"))
        mPaperList.add(PaperItem(5, "Paper5"))
        mPaperList.add(PaperItem(6, "Paper6"))
        mPaperList.add(PaperItem(7, "Paper7"))
        mPaperList.add(PaperItem(8, "Paper8"))
        mPaperList.add(PaperItem(9, "Paper9"))
        mPaperList.add(PaperItem(10, "Paper10"))


        var mPaperListAdapter = PaperListAdapter(mPaperList, this)
        paper_list.adapter = mPaperListAdapter

        paper_list.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "您选择的试卷Paper :"+" "+position,Toast.LENGTH_SHORT).show()
            var intent = Intent(this,Question::class.java)
            intent.putExtra("username",userName)
            startActivity(intent) //跳转
        }

    }
}

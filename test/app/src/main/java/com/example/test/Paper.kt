package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_paper.*

class Paper : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper)

        /**
         * 联网获得数据
         */

        var mPaperList:ArrayList<PaperItem> = arrayListOf()
        /**
         * 新建一些测试数据
         */
        mPaperList.add(PaperItem(1,"试卷1")
        mPaperList.add(PaperItem(2,"试卷2")
        mPaperList.add(PaperItem(3,"试卷3")


        var mPaperListAdapter:PaperListAdapter = PaperListAdapter(mPaperList,this)
        paper_list.adapter = mPaperListAdapter
    }
}

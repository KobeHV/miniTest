package com.example.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PaperListAdapter(pData:ArrayList<PaperItem>,pContext: Context) : BaseAdapter() {

    var mData = pData
    var mContext = pContext

    override fun getCount(): Int = mData.size

    override fun getItem(p0: Int): Any = mData[p0]

    override fun getItemId(p0: Int): Long = mData[p0].paperId.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var cv = p1
        var holder:ViewHolder?
        if(p1 == null){
            cv = LayoutInflater.from(mContext).inflate(R.layout.list_item,p2,false)
            holder = ViewHolder()
            holder.mPaperTitle = cv?.findViewById(R.id.paper_list_item_title)
            cv.tag = holder
        }else{
            holder = cv!!.tag as ViewHolder
        }
        holder?.mPaperTitle?.text = mData[p0].paperTitle
        return cv!!
    }
    internal class ViewHolder{//用来保存布局的内容
        var mPaperId:TextView?=null
        var mPaperTitle:TextView?=null

    }
}
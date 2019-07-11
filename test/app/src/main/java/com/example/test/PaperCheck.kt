package com.example.test

import com.hit.software.exam.Http

class PaperCheck (pPaperResult: PaperResult) :GeneralInterface{

    var mPapercheck = pPaperResult
    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println("------>${str.toString()}")
            mPapercheck.getPaperResult(true,str.toString())
        }else{
            println("GetPaper Check --- Something Wrong")
        }
    }
    fun getPaper(){
        Http.send("http://172.20.106.156:8080/exam/GetPaper", this)
    }
    interface PaperResult{
        fun  getPaperResult(ret:Boolean,tipText:String)
    }
}
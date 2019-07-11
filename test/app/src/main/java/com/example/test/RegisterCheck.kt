package com.example.test

import com.hit.software.exam.Http
import org.json.JSONObject

class RegisterCheck(pRegisterResult: RegisterResult) :GeneralInterface {

    var mRegistercheck = pRegisterResult

    override fun response(flag: Boolean, str: String?) {
        if(flag){
            var mJSON = JSONObject(str!!)
            when(mJSON.optString("code","400")){
                "200"->{
                    mRegistercheck.getRegisterResult(true,"200 "+mJSON.optString("result",""))
                }
                "201"->{
                    mRegistercheck.getRegisterResult(false,"201 "+mJSON.optString("result",""))
                }
            }
        }else{
            println("Something Wrong")
        }
    }
    /**
     * 接收两个参数，分别是用户名和密码，将用户名和密码发送到云端，判断，并返回结果
     */
    fun checkUserNameAndPassword(userName:String,userPassword:String = ""){
        val mJson = "{\"username\":\"$userName\",\"userpassword\":\"$userPassword\"}"
        //访问网络
//        Http.send("http://172.20.9.250:8080/exam/register", this,mJson)
        Http.send("http://172.20.106.156:8080/exam/register", this,mJson)
    }

    interface RegisterResult{
        fun  getRegisterResult(ret:Boolean,tipText:String)
    }
}
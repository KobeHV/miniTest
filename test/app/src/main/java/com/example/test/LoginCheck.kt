package com.hit.software.exam

import com.example.test.GeneralInterface
import org.json.JSONObject

class LoginCheck(pLoginResult: LoginResult) :GeneralInterface{

    var mLogincheck = pLoginResult

    override fun response(flag: Boolean, str: String?) {
        if(flag){
            var mJSON = JSONObject(str!!)
//            mJSON.getString("code")  //不建议这样写，没有code会崩
            when(mJSON.optString("code","400")){//没有code，默认400
                "100"->{
                    mLogincheck.getLoginResult(true,"100 "+mJSON.optString("result",""))
                }
                "101"->{
                    mLogincheck.getLoginResult(false,"101 "+mJSON.optString("result",""))
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
//        Http.send("http://172.20.9.250:8080/exam/login", this,mJson)//商用的话，用域名
        Http.send("http://172.20.106.43:8080/exam/login", this,mJson)
    }

    /**
     * 回调，保存服务端返回的消息
     */
    interface LoginResult{
        fun  getLoginResult(ret:Boolean,tipText:String)
    }
}
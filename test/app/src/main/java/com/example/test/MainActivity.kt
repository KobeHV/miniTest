package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.hit.software.exam.LoginCheck
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var userName:String=""
    val mLoginCheck = object : LoginCheck.LoginResult {
        override fun getLoginResult(ret: Boolean, tipText: String) {
            if (!ret) {
                val mm = Message()
                mm.what = 1   //服务端返回 登陆失败  // what is int type
                mm.obj = tipText
                mHandler.sendMessage(mm)
            } else {
                val mm = Message()
                mm.what = 0 // 服务端返回 登陆成功
                mm.obj = tipText
                mHandler.sendMessage(mm)
            }
        }
    }
    val mRegisterCheck = object : RegisterCheck.RegisterResult {
        override fun getRegisterResult(ret: Boolean, tipText: String) {
            if (!ret) {
                val mm = Message()
                mm.what = 3   //服务端返回 注册失败 用户名已存在
                mm.obj = tipText
                mHandler.sendMessage(mm)
            } else {
                val mm = Message()
                mm.what = 2 // 服务端返回 注册成功
                mm.obj = tipText
                mHandler.sendMessage(mm)
            }
        }
    }
    val mHandler = Handler() {
        when (it.what) {
            //登陆成功
            0 -> {
//                println("return-tip: ${it.obj.toString()}")
                putTipText(it.obj.toString())
                var intent = Intent(this, LoginSuccess::class.java)
                intent.putExtra("username",userName)
                startActivity(intent) //跳转
//                this.finish()
            }
            //登陆失败
            1 -> {
//                println("return-tip: ${it.obj.toString()}")
                putTipText(it.obj.toString())
            }
            //注册成功
            2 -> {
//                println("return-tip: ${it.obj.toString()}")
                putTipText(it.obj.toString())
                startActivity(Intent(this, RegisterSuccess::class.java)) //跳转
//                this.finish()
            }
            //注册失败（用户名或者密码太短）
            3 -> {
//                println("return-tip: ${it.obj.toString()}")
                putTipText(it.obj.toString())
            }
            //倒计时
            5 -> {
                putTipText(it.obj.toString())
            }
        }

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {//入口
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFunction()
    }

    /**
     *给按钮添加点击事件
     */
    private fun addFunction() {
        button_login.setOnClickListener {
            checkLogin()
        }
        button_register.setOnClickListener() {
            checkRegister()
        }
    }

    /**
     * 判断登陆是否正确
     */
    private fun checkLogin() {
        userName = text_userid.text.toString()
        val userPassword = text_userpwd.text.toString()

        println("checkLogin: UserName:$userName UserPassword:$userPassword")
        //发送到云端去判断，等待返回结果，正确登陆，否则提示错误
        LoginCheck(mLoginCheck).checkUserNameAndPassword(userName, userPassword)
    }

    /**
     * 判断注册是否正确
     */
    private fun checkRegister() {
        userName = text_userid.text.toString()
        val userPassword = text_userpwd.text.toString()

        if (userName.length < 3 || userPassword.length < 3) {
            val msg = Message()
            msg.what = 3//注册失败
            msg.obj = "用户名或者密码不得短于3个字符！请重新注册"
            mHandler.sendMessage(msg)
        } else {
            println("checkRegister: UserName:$userName UserPassword:$userPassword")
            //发送到云端去判断，等待返回结果，正确登陆，否则提示错误
            RegisterCheck(mRegisterCheck).checkUserNameAndPassword(userName, userPassword)
        }
    }

    fun putTipText(text: String) {
        tip.text = text
    }

    /**
     * 倒计时
     */
    fun countDown(seconds: Int) {
        println("start")
        var mSeconds = seconds

        GlobalScope.launch {
            while (mSeconds > 0) {
                println(mSeconds)
                kotlinx.coroutines.delay(1000)
                /**
                 * 由秒计算剩余时间
                 */
                val h: Int = mSeconds / 3600
                val m: Int = (mSeconds - h * 3600) / 60
                val s: Int = mSeconds - h * 3600 - m * 60

                var tim = "剩余时间:"
                if (h != 0) {
                    tim = tim + h.toString() + "小时 "
                }
                if (m != 0) {
                    tim = tim + m.toString() + "分钟 "
                }
                if (s != 0) {
                    tim = tim + s.toString() + "秒"
                }
                //显示剩余时间
                val msg = Message()
                msg.what = 5
                msg.obj = tim
                mSeconds--
                mHandler.sendMessage(msg)
                mHandler.obtainMessage()

            }
        }

    }
}


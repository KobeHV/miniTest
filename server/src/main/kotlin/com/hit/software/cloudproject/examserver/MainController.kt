package com.hit.software.cloudproject.examserver

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.sql.ResultSet

@Controller
@RequestMapping("/")//挂载目录，接受一些请求
class MainController {

    //定义查询数据库对象
    @Autowired
    var mJdbcTemplate: JdbcTemplate? = null

    @RequestMapping("/")
//    @ResponseBody//应答内容
    fun firstMethod(): String {
        return "index"
    }

    /**
     * 页面登陆地址
     */
    @RequestMapping("/loginget")
    @ResponseBody
    fun loginCheck(name:String,password:String):String{
        return "$name $password : OK"
    }

    /**
     * 登陆成功页面
     */
    @RequestMapping("/main")
    fun mainPage():String{
        return "main"
    }

    /**
     * 登陆验证
     * POST
     */
    @ResponseBody
    @PostMapping("/login")
    fun loginMethod(@RequestBody login: Login): String {
        /**
         * 处理数据
         * 查询数据库
         */
        var hasResult = false
        val sql = "select * from users where UserName = '${login.username}' and UserPassword = '${login.userpassword}'"
        println("sql:$sql")
        //object 这里是回调，返回时间不确定的情况下用回调
        var query = mJdbcTemplate?.query(sql, object : RowCallbackHandler {
            override fun processRow(rs: ResultSet) {
                println("与服务端成功建立连接")
                println("UserName"+rs.getString("UserName"))
                println("UserPassword"+rs.getString("UserPassword"))
                hasResult = true
            }
        })

        /**
         * 连接成功后服务端要返回的内容
         */
        return if (hasResult) {
            //登陆成功
            "{\"code\":\"100\",\"result\":\"Login success\",\"location\":\"main\"}"
        } else {
            //登陆失败
            "{\"code\":\"101\",\"result\":\"Login failure\"}"
        }
    }
}
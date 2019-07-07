package com.hit.software.cloudproject.examserver

class Login {
    /**
     * 注意对应的JSON，应该和这里命名的属性名保持一致 {username":"abc",..."}
     */
    var username:String?=null
    var userpassword:String?=null

    /**
     * 重写toString方法
     */
    override fun toString(): String {
        return this.username+","+this.userpassword
    }
}
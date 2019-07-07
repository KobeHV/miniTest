package com.example.test

interface GeneralInterface {

    /**
     * Http Response call back(回调)
     * 网络调用接口
     */
    fun response(flag: Boolean, str: String? = null)

}
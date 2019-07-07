package com.hit.software.exam

import com.example.test.GeneralInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import okhttp3.RequestBody
import  okhttp3.MediaType.Companion.toMediaType


/**
 * Common http request sender and call backs
 * 使用OKHttp 包
 */
object Http {
    // OkHttpClient
    //定义OkHttp客户端，使用此客户端来发送请求
    private val mOkHttpClient: OkHttpClient = OkHttpClient()


    /**
     * Send http POST request
     * Needs implements call backs
     */
    fun send(
        url: String,
        mHttpCallBack: GeneralInterface? = null,
        postData: String = "",
        mContentType: String = "application/x-www-form-urlencoded"
    ) {
        GlobalScope.launch {
            val cb: Callback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("请求失败")
                    mHttpCallBack?.response(false)
                }

                override fun onResponse(call: Call, response: Response) {
                    println("请求成功")
                    mHttpCallBack?.response(true, response.body?.string())
                }
            }
            mOkHttpClient.newCall(
                Request.Builder()
                    .url(url)
                    .post(RequestBody.create(("application/json; charset=utf-8").toMediaType(), postData))
                    .header("Content-Type", mContentType)
                    .build()
            ).enqueue(cb)
        }
    }


    fun sendGet(
        url: String,
        mHttpCallBack: GeneralInterface? = null,
        mContentType: String = "application/x-www-form-urlencoded"
    ) {

        GlobalScope.launch {
            try {
                val cb: Callback = object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        mHttpCallBack?.response(false)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        mHttpCallBack?.response(true, response.body?.string())
                    }

                }
                /*Use OkHttpClient */
                //mOkHttpClient.setConnectTimeout(mHttpConnectionTimeout, TimeUnit.SECONDS)
                //mOkHttpClient.setReadTimeout(mHttpReadTimeout, TimeUnit.SECONDS)
                mOkHttpClient.newCall(
                    Request.Builder()
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                        .url(url)
                        .header("Content-Type", mContentType)
                        .build()
                ).enqueue(cb)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
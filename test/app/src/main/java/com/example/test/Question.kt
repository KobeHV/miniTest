package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import com.hit.software.exam.Http
import kotlinx.android.synthetic.main.activity_question.*
import org.json.JSONObject

class Question : AppCompatActivity() {

    var userName = ""

    var str_JSON: String = "******initial******"
    val mGetPaper = object : PaperCheck.PaperResult {
        override fun getPaperResult(ret: Boolean, tipText: String) {
            if (!ret) {
                println("获取试题失败！！！！")
                val mm = Message()
                mm.what = 1   //服务端返回 登陆失败  // what is int type
                mm.obj = tipText
                mHandler.sendMessage(mm)
            } else {
                println("获取试题成功！！！！")
                val mm = Message()
                mm.what = 0 // 服务端返回 登陆成功
                mm.obj = tipText
                mHandler.sendMessage(mm)
            }
        }
    }
    val mHandler = Handler() {
        when (it.what) {
            //登陆成功
            0 -> {
                /*
                **回调的含义就是等着，然后程序继续往下执行
                * *所以要在接收消息之后执行的函数必须写在回调里!!!!
                 */
                str_JSON = it.obj.toString()
                println(str_JSON)
                parseJSON()
                addQuestion()
            }
            //登陆失败
            1 -> {
            }
            //倒计时
            5 -> {
            }
        }

        true
    }

    var options: ArrayList<CheckBox> = ArrayList()
    var index: Int = 0
    var questionList: ArrayList<answer> = ArrayList()
    var grade: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        userName = intent.getStringExtra("username")

        zero_visible()
        options.add(optionA)
        options.add(optionB)
        options.add(optionC)
        options.add(optionD)

        println("*******准备获取试题*******")
        PaperCheck(mGetPaper).getPaper()
        println("*******获取试题结束*******")

        for (i in 0..3) {
            options[i].setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    if (p1 == true) {
                        for (j in 0..3) {
                            if (options[j] == p0) {
                                questionList[index].select_option = j + 1//选项别忘了+1
                            } else {
                                options[j].isChecked = false
                            }
                        }
                    }
                }
            })
        }
        next.setOnClickListener() {
            index++;
            zero_visible()
            if (index == questionList.size - 1) {
                two_visible()
            }
            if (index < questionList.size) {
                addQuestion()
            }
        }
        last.setOnClickListener() {
            index--
            zero_visible()
            if (index >= 0 && index < questionList.size) {
                addQuestion()
            }
            if (index == 0) {
                one_visible()
            }

        }
        complete.setOnClickListener() {
            getGrade()
            var gradeJSON = "{\"name\":\"$userName\",\"paperid\":\"11\",\"score\":\"${grade.toInt()}\"}"

            Http.send("http://172.20.106.156:8080/exam/addScores",null,gradeJSON)
            var tip = "恭喜您！您做完了全部试题\n满分100分，您的得分为${grade.toInt()}分"
            var toast : Toast = Toast.makeText(this,tip,Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
//            var intent = Intent(this, GetGrade::class.java)
//            intent.putExtra("grade", tip)
//            startActivity(intent)
        }

    }


    fun addQuestion() {
        questionTitle.text = "${index + 1} : ${questionList[index].question_title}"
        optionA.text = questionList[index].option_a
        optionB.text = questionList[index].option_b
        optionC.text = questionList[index].option_c
        optionD.text = questionList[index].option_d
        for (i in 0..3) {
            options[i].isChecked = false
        }
        var select = questionList[index].select_option
        if (select != 0) {
            options[questionList[index].select_option - 1].isChecked = true
        }

    }

    fun getGrade() {
        var len = questionList.size
        for (i in 0..len - 1) {//数组越界啊！！！！len-1！！！多少次这种错误了！！！！
            var temp: answer = questionList[i]
            if (temp.select_option == temp.correct_option) {
                grade++
            }
        }
        grade = grade / questionList.size * 100
    }

    fun zero_visible() {
        next.setVisibility(View.VISIBLE)
        last.setVisibility(View.VISIBLE)
        complete.setVisibility(View.GONE)
    }

    fun one_visible() {
        next.setVisibility(View.VISIBLE)
        last.setVisibility(View.GONE)
        complete.setVisibility(View.GONE)
    }

    fun two_visible() {
        next.setVisibility(View.GONE)
        last.setVisibility(View.VISIBLE)
        complete.setVisibility(View.VISIBLE)
    }

    fun parseJSON() {
        println("解析JSON···")

        println(str_JSON)

        var mJson = JSONObject(str_JSON)
        var question_list = mJson.optJSONArray("question_list")
        val list_length = question_list.length() - 1

        //解析生成题目
        for (i in 0..list_length) {
            var tempJson = question_list.getJSONObject(i)

            var correct = tempJson.optString("correct_option")
            //添加题目条件
            var question_title = tempJson.getString("question_title")

            //添加选项
            var option_list = tempJson.optJSONArray("option")
            var option_titleA = option_list.getJSONObject(0).optString("A")
            var option_titleB = option_list.getJSONObject(1).optString("B")
            var option_titleC = option_list.getJSONObject(2).optString("C")
            var option_titleD = option_list.getJSONObject(3).optString("D")

            var option_correct: Int
            option_correct = when (correct) {
                "A" -> 1
                "B" -> 2
                "C" -> 3
                "D" -> 4
                else -> 0
            }
            questionList.add(
                answer(
                    question_title.toString(), option_titleA.toString(), option_titleB.toString(),
                    option_titleC.toString(), option_titleD.toString(), option_correct
                )
            )
        }
    }
}
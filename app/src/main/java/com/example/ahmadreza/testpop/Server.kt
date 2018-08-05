package com.example.ahmadreza.testpop

/**
 * Created by ahmadreza on 8/5/18.
 */
class Server {

    var bn_songs = "موضوعات و دسته بندی سایت"
    var an_songs = "<a href=\"#week\">هفته</a>"

    var pt_title_songs = "</strong> بنام <strong>" + "(.*?)" + "</strong> با بالاترین کیفیت</p>"
    var pt_singer_songs = "rel=\"noopener noreferrer\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "</strong>"

    var pt_catg_songs = "rel=\"noopener noreferrer\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "</strong>"
    var pt_catt_songs = "rel=\"noopener noreferrer\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "</strong>"



    private constructor(){
        //connect to the server and get the data and set the all
        println("Server created")

    }

    companion object {
        val st : Server by lazy { Server() }
    }

}
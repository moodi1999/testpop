package com.example.ahmadreza.testpop.Data

/**
 * Created by ahmadreza on 8/5/18.
 */
class Server {

    var bn_songs = "موضوعات و دسته بندی سایت"
    var an_songs = "<a href=\"#week\">هفته</a>"

    var pt_link_songs = "<h2><a href=\"" + "(.*?)" + "\" rel=\"bookmark\" title=\""
    var pt_title_songs = "</strong> بنام <strong>" + "(.*?)" + "</strong> با بالاترین کیفیت</p>"
    var pt_singer_songs = "rel=\"noopener noreferrer\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "</strong>"
    var pt_Img_Url_songs = "\" src=\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "\" alt=\""
    var pt_allcat_songs = "rel=\"category tag\">" + "(.*?)" + "</a></span>"
    var sp_catg_songs = "\"</a>, <a href=\\\"http://pop-music.ir/category/single-music\\\" rel=\\\"category tag\\\">\""
    var sp_catt_songs = "</a></span>"
    var pt_date_songs = "<span class=\"date\">" + "(.*?)" + " </span>"
    var pt_views_songs = "<span class=\"view\">" + "(.*?)" + "</span>"





    private constructor(){
        //connect to the server and get the data and set the all
        println("Server created")

    }

    companion object {
        val st : Server by lazy { Server() }
    }

}
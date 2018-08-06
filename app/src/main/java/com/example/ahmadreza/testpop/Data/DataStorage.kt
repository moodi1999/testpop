package com.example.ahmadreza.testpop.Data

/**
 * Created by ahmadreza on 8/5/18.
 */
class DataStorage {

    var recentWebContent: String = ""

    var bn_songs = "موضوعات و دسته بندی سایت"
    var an_songs = "<a href=\"#week\">هفته</a>"

    var pt_link_songs = "<h2><a href=\"(.*?)\" rel=\"bookmark\" title=\""
    var pt_title_songs = "</strong> بنام <strong>(.*?)</strong> با بالاترین کیفیت</p>"
    var pt_singer_songs = "rel=\"noopener noreferrer\">دانلود آهنگ جدید</a> <strong>" + "(.*?)" + "</strong>"
    var pt_Img_Url_songs = "\" src=\"" + "(.*?)" + "\" alt=\""
    var pt_allcat_songs = "rel=\"category tag\">" + "(.*?)" + "</a></span>"
    var sp_catg_songs = "</a>, <a href=\""
    var sp_catt_songs1 = "rel=\"category tag\">"
    var sp_catt_songs2 = "rel=\"category tag\">"
    var pt_date_songs = "<span class=\"date\">" + "(.*?)" + " </span>"
    var pt_views_songs = "<span class=\"view\">" + "(.*?)" + "</span>"

    var arr_recentData: ArrayList<SongData> = ArrayList()

    private constructor(){
        //connect to the server and get the data and set the all
        println("DataStorage created")

    }

    companion object {
        val instance : DataStorage by lazy { DataStorage() }
    }

}
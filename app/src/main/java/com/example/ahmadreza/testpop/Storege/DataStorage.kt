package com.example.ahmadreza.testpop.Storege

import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.Datas.SongData

/**
 * Created by ahmadreza on 8/5/18.
 */
class DataStorage {

    val Main_URL = "http://pop-music.ir/"


    var recentWebContent: String = ""
    var recentWebContent_Done: Boolean? = null

    var ctegoWebContent: String = ""
    var categoWebContent_Done: Boolean? = null

    // recent Song pattern and split String
    var bn_songs = "موضوعات و دسته بندی سایت"
    var an_songs = "<a href=\"#week\">هفته</a>"

    var pt_link_songs = "<h2><a href=\"(.*?)\" rel=\"bookmark\" title=\""
    var pt_titleAndSinger_songs = "Download(.*?)p>"
    var pt_titleAndSinger_songs2 = "href=\"http://pop-music.ir/tag/download-new(.*?)p>"
    var pt_title_songs = "&#8211;(.*?)</"
    var pt_singer_songs = ">(.*?)</a>"
    var pt_title_fa = "title=\"(.*?)\" src=\""
    var pt_Img_Url_songs = "\" src=\"" + "(.*?)" + "\" alt=\""
    var pt_allcat_songs = "<a href=\"(.*?)\n" +
            "\t<span class=\"date\""
    var pt_each_cat_songs = "rel=\"category tag\">" + "(.*?)" + "</a>"
    var sp_catg_songs = "</a>, <a href=\""
    var sp_catt_songs1 = "rel=\"category tag\">"
    var sp_catt_songs2 = "rel=\"category tag\">"
    var pt_date_songs = "<span class=\"date\">" + "(.*?)" + " </span>"
    var pt_views_songs = "<span class=\"view\">" + "(.*?)" + "</span>"

    // Recent songs
    var arr_recentData: ArrayList<SongData> = ArrayList()

    // Song page Pattern and split
    var sp_song_page1 = "<div class=\"download\">"
    var sp_song_page2 = "<div id=\"related_posts\">"
    var pt_mp3_disc_128 = "<span>(.*?)</h4>"
    var pt_mp3_disc_320 = "<span>\n(.*?)</h4>"
    var pt_mp3_128_320= "href=\"(.*?)\""

    // favorite Songs
    var set_favo: MutableSet<SongData> = mutableSetOf()


    //Categories Pattern and split
    var bn_catego = "دسته بندی ها"
    var an_catego = "بزودی ها"
    var pt_categourlandname = "href=\"(.*?)/a>"
    var pt_categoname = "\" >(.*?)<"
    var sp_categourl = "\""

    var arr_categories: ArrayList<CatgoData> = ArrayList()


    /*var arr_catego_name = arrayListOf<String>("Archives Month", "Album", "Music News", "Coming Sonn", "Voiceles", "Single Music", "Single Music/Epic", "Single Music/Rock", "Single Music/Traditional", "Single Music/Happy", "Single Music/Sad", "Titles", "Remix", "public", "Full Album", "Music Video(Coming soon)", "Sound Track", "Moloody", "Nohe", "Special")*/

    private constructor(){
        //connect to the server and get the data and set them all
        println("DataStorage created")
        var song: SongData = SongData("www.google.com","anfroid","mmamd", "www.picaso.com","good , ok","today","a lot")
        arr_recentData.add(song)
        arr_recentData.add(song)
        arr_recentData.add(song)
        arr_recentData.add(song)

        var cat = CatgoData("آرشیو ماه", "www.google.com")
        arr_categories.add(cat)
        arr_categories.add(cat)
        arr_categories.add(cat)
        arr_categories.add(cat)
    }

    companion object {
        val instance : DataStorage by lazy { DataStorage() }

    }

}
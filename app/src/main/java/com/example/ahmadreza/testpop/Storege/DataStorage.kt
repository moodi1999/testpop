package com.example.ahmadreza.testpop.Storege

import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.Datas.SongData

/**
 * Created by ahmadreza on 8/5/18.
 */
class DataStorage {

    var before_loading: ArrayList<SongData> = ArrayList()

    val Main_URL = "http://pop-music.ir/"
    var page = 0
    var recentWebContent: String = ""
    var recentWebContent_Done: Boolean? = null

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
    var pt_allcat_songs = "class=\"cat\">(.*?)</span>"
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


    //Category Web Content

    var item_categoWebContent: String = ""
    var item_categoWebContent_Done: Boolean? = null

    var arr_categories: ArrayList<CatgoData> = ArrayList()
    var arr_catego_item_Data: ArrayList<SongData> = ArrayList()

    //Categories Pattern and spli

    var bn_songs_catego = "<div class=\"define-post\"><div id=\"yektanet-pos-2\"></div></div>"
    var an_songs_catego = "<a href=\"#week\">هفته</a>"

    var bn_catego = "دسته بندی ها"
    var an_catego = "بزودی ها"
    var pt_categourlandname = "href=\"(.*?)/a>"
    var pt_categoname = "\" >(.*?)<"
    var sp_categourl = "\""

    // Album pt and sp

    val arr_album: ArrayList<AlbumData> = ArrayList()

    var bn_album = "\"<span style=\\\"color: #0000ff;\\\"><a style=\\\"color: #0000ff;\\\" href=\\\"http://pop-music.ir/tag/\""
    var an_album = "</div>\n" +
            "\t\t\t\t<div class=\"pull-left\"></div>\n" +
            "\t\t\t\t<div class=\"clear\"></div>\n" +
            "\t\t\t\t<div class=\"post-tags\">"

    var pt_album_url = "href=\"(.*?)\" target=\"_blank\""
    var pt_album_song_name = "noopener(.*?)</p>"
    var pt_album_song_name2 = "noreferrer\">(.*?)</a>"
    var sp_album_song_name3 = "&#8211;"



    //

    var arr_catego_name = arrayListOf<String>("Album", "Coming Sonn", "Voiceles", "Single Music", "Single Music/Epic", "Single Music/Rock", "Single Music/Traditional", "Single Music/Happy", "Single Music/Sad", "Titles", "Remix", "public", "Music Video(Coming soon)", "Sound Track", "Special")

    var arr_catego_url = arrayListOf<String>("http://pop-music.ir/category/album", "http://pop-music.ir/category/coming-soon", "http://pop-music.ir/category/%d8%a8%db%8c-%da%a9%d9%84%d8%a7%d9%85", "http://pop-music.ir/category/single-music", "http://pop-music.ir/category/single-music/anthem", "http://pop-music.ir/category/single-music/rock", "http://pop-music.ir/category/single-music/traditional-songs", "http://pop-music.ir/category/single-music/happy-song", "http://pop-music.ir/category/single-music/sad-song", "http://pop-music.ir/category/titles", "http://pop-music.ir/category/%d8%b1%d9%85%db%8c%da%a9%d8%b3", "http://pop-music.ir/category/%d8%af%d8%b3%d8%aa%d9%87%e2%80%8c%d8%a8%d9%86%d8%af%db%8c-%d9%86%d8%b4%d8%af%d9%87",  "http://pop-music.ir/category/music-video", "http://pop-music.ir/category/soundtrack", "http://pop-music.ir/category/special")

    private constructor(){
        //connect to the server and get the data and set them all
        println("DataStorage created")
        var song: SongData = SongData("www.google.com","anfroid","mmamd", "www.picaso.com","good , ok","today","a lot")
        before_loading.add(song)
        before_loading.add(song)
        before_loading.add(song)
        before_loading.add(song)
        before_loading.add(song)
        before_loading.add(song)
      /*  var song: SongData = SongData("www.google.com","anfroid","mmamd", "www.picaso.com","good , ok","today","a lot")
        arr_recentData.add(song)
        arr_recentData.add(song)
        arr_recentData.add(song)
        arr_recentData.add(song)

        var cat = CatgoData("آرشیو ماه", "www.google.com")
        arr_categories.add(cat)
        arr_categories.add(cat)
        arr_categories.add(cat)
        arr_categories.add(cat)
    */}

    companion object {
        val instance : DataStorage by lazy { DataStorage() }

    }

}
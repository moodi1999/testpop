package com.example.ahmadreza.testpop.DataFinders

/**
 * Created by ahmadreza on 8/12/18.
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.CategoSongItemADP
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.RecentRecyAdp
import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.catego_first_lay.view.*
import kotlinx.android.synthetic.main.catego_second_lay.view.*
import kotlinx.android.synthetic.main.fragment_recent.view.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class AlbumPageDF(val activity: FragmentActivity?, val songData: SongData) : AsyncTask<String, Unit, ArrayList<AlbumData>>() {

    val Ds = DataStorage.instance

    override fun doInBackground(vararg str: String): ArrayList<AlbumData> {

        val arr: ArrayList<AlbumData> = ArrayList()

        try {
            val con = songData.pageCon
            val test = con.split("<span style=\"color: #0000ff;\"><a style=\"color: #0000ff;\" href=\"http://pop-music.ir/tag/")[1]
            //println(test)

            val test2 = test.split("</div>\n" +
                    "\t\t\t\t<div class=\"pull-left\"></div>\n" +
                    "\t\t\t\t<div class=\"clear\"></div>\n" +
                    "\t\t\t\t<div class=\"post-tags\">")[0]

            println(test2)
            //val n = songData.pageCon.split(Ds.bn_album)[1].split(Ds.an_album)[0]
            val m_url = Pattern.compile(Ds.pt_album_url).matcher(test2)
            val m_song_name = Pattern.compile(Ds.pt_album_song_name).matcher(test2)

            while (m_url.find()){

                var mp3 = ""
                var song_name = ""

                try {
                    mp3 = m_url.group(1)
                }catch (e: Exception){
                    e.printStackTrace()
                    mp3 = "NotFound!"
                }
                var song_name_or = ""
                try {
                    m_song_name.find()
                    song_name_or = m_song_name.group(1)
                    println(song_name_or)
                    val m_song_name2 = Pattern.compile(Ds.pt_album_song_name2).matcher(song_name_or)
                    m_song_name2.find()
                    song_name = m_song_name2.group(1)
                }catch (e: Exception){
                    try {
                        song_name = song_name_or.split("&#8211;")[2]
                    }catch (e: Exception){
                    }
                    e.printStackTrace()
                    mp3 = "NotFound!"
                }
                
                val album = AlbumData(mp3, song_name)
                arr.add(album)
            }

            return arr

        }
        catch (e : Exception){
            e.printStackTrace()
            println("something wrong")
            return arr
        }
    }

    override fun onPostExecute(result: ArrayList<AlbumData>) {
        super.onPostExecute(result)
        print("size of res == ")
        println(result.size)
        (activity as MainActivity).SetPlayer(songData, MusicType.Album ,result)
        println("SongDataFinder.onPostExecute")
    }



}


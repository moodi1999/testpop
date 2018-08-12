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
class AlbumPageDF(val activity: FragmentActivity?, val songData: SongData) : AsyncTask<String, Unit, Unit>() {

    val Ds = DataStorage.instance

    override fun doInBackground(vararg str: String) {


        try {

            val n = songData.pageCon.split(Ds.bn_album)[1].split(Ds.an_album)[0]

            val m_url = Pattern.compile(Ds.pt_album_url).matcher(n)
            val m_song_name = Pattern.compile(Ds.pt_album_song_name).matcher(n)

            while (m_url.find()){

                var url = ""
                var song_name = ""

                try {
                    url = m_url.group(1)
                }catch (e: Exception){
                    e.printStackTrace()
                    url = "NotFound!"
                }

                try {
                    song_name = m_song_name.group(1)
                    val m_song_name2 = Pattern.compile(Ds.pt_album_song_name2).matcher(song_name)
                    m_song_name2.find()
                    song_name = m_song_name2.group(1)
                }catch (e: Exception){
                    song_name = song_name.split(Ds.sp_album_song_name3)[1] + song_name.split(Ds.sp_album_song_name3)[2]
                    e.printStackTrace()
                    url = "NotFound!"
                }

                val album = AlbumData(url, song_name)
                Ds.arr_album!!.add(album)
            }
        }
        catch (e : Exception){
            e.printStackTrace()
            println("something")
        }
    }

    override fun onPostExecute(result: Unit) {
        super.onPostExecute(result)


        println("SongDataFinder.onPostExecute")
    }



}


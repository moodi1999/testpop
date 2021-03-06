package com.example.ahmadreza.testpop.DataFinders

/**
 * Created by ahmadreza on 8/12/18.
 */

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.AlbumRecyAdp
import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.Datas.SongData
import kotlinx.android.synthetic.main.album_lay.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class AlbumPageDF(val activity: FragmentActivity?, val songData: SongData, val type: CallType, val context: Context = activity!!.applicationContext) : AsyncTask<String, Unit, ArrayList<AlbumData>>() {

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

           loop@while (m_url.find()){

               println("in the while")

                var mp3 = ""
                var song_name = ""
               var song_name_or = ""

                try {
                    mp3 = m_url.group(1)
                    println(mp3)
                }catch (e: Exception){
                    //e.printStackTrace()
                    println("mp3 not")
                    mp3 = "NotFound!"
                }

                try {
                    m_song_name.find()
                    song_name_or = m_song_name.group(1)
                    val m_song_name2 = Pattern.compile(Ds.pt_album_song_name2).matcher(song_name_or)
                    m_song_name2.find()
                    song_name = m_song_name2.group(1)
                }catch (e: Exception){
                    try {
                        song_name = song_name_or.split("&#8211;")[2].split("</a>")[0]

                    }catch (e: Exception){
                        try {
                            val m_song_name3 = Pattern.compile("\">(.*?)</a>").matcher(song_name_or)
                            m_song_name3.find()
                            song_name = m_song_name3.group(1)

                        }catch (e: Exception){
                            song_name = "NotFound!"
                            println("name not")
                        }
                    }
                    //e.printStackTrace()
                }
               if (song_name.equals("DirectLink",true)){
                   println("find Direct Link")
                   songData.mp3.add(mp3)
                   continue@loop
               }
                for(i in arr){
                    if (song_name.equals(i.song_name,true) && !song_name.equals("NotFound!",true)){
                        break@loop
                    }
                }

                val album = AlbumData(mp3, song_name)
                arr.add(album)
            }

            return arr

        }
        catch (e : Exception){

            println("something wrong")
            return arr
        }
    }

    override fun onPostExecute(result: ArrayList<AlbumData>) {
        super.onPostExecute(result)
        print("size of res == ")
        println(result.size)
        val ac = activity as MainActivity
        val adaptor = AlbumRecyAdp(activity, songData, result)
        activity.album_recyclerview.adapter = adaptor
        ac.SetPlayer(songData, MusicType.Album ,result)
        ac.dialog?.dismiss()
        if (type == CallType.GetMp3){
            ac.downloadMp3(songData.mp3, context)
        }
        println("SongDataFinder.onPostExecute")

    }



}


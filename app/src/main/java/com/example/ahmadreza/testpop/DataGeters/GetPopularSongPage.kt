package com.example.ahmadreza.testpop.DataGeters

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/14/18.
 */
class GetPopularSongPage(val activity: FragmentActivity?, val songData: SongData, val musicType: MusicType, val callType: CallType = CallType.RECENT) : AsyncTask<Unit, Unit, SongData>() {

    override fun onPreExecute() {
        super.onPreExecute()
        println("GetSongPageCon.onPreExecute")
    }

    override fun doInBackground(vararg unit: Unit?): SongData {

        val url = songData.url
        val xml = StringBuilder()

        try {
            val connection = URL(url).openConnection() as HttpURLConnection

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var charsread: Int
            val inputBuffer = CharArray(500)
            while (true) {
                charsread = reader.read(inputBuffer)
                if (charsread < 0) {
                    break
                }
                if (charsread > 0) {
                    xml.append(String(inputBuffer, 0, charsread))
                }
            }
            reader.close()
            songData.pageCon = xml.toString()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return songData
    }

    override fun onPostExecute(result: SongData) {
        super.onPostExecute(result)

        var  mp3: ArrayList<String> = arrayListOf()
        var title: String = ""
        var singer: String = ""
        var link: String = ""
        var ImgUrl: String = ""
        val Ds = DataStorage.instance
        try {
            val con = result.pageCon

            val other = ((con.split("class=\"cat\">دسته بندی : <a href=\""))[1].split("<div class=\"post-tags\">"))[0]
            val m_titleAndSinger_songs = Pattern.compile(Ds.pt_titleAndSinger_songs).matcher(other)
            val m_fa_titleAndsinger = Pattern.compile(Ds.pt_title_fa).matcher(other)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(other)

            val mp3s = con.split(Ds.sp_song_page1)[1].split(Ds.sp_song_page2)[0]
            val m_mp3s = Pattern.compile(Ds.pt_mp3_128_320).matcher(mp3s)
            val m_disc_320 = Pattern.compile(Ds.pt_mp3_disc_320).matcher(mp3s)
            val m_disc_128 = Pattern.compile(Ds.pt_mp3_disc_128).matcher(mp3s)

            try {
                m_disc_320.find()
                mp3.add(m_disc_320.group(1))
                m_mp3s.find()
                mp3.add(m_mp3s.group(1))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            // 320 Qu

            try {
                m_disc_128.find()
                mp3.add(m_disc_128.group(1))
                m_mp3s.find()
                mp3.add(m_mp3s.group(1))

            } catch (e: Exception) {
                e.printStackTrace()
            }


            try {
                m_ImgUrl.find()
                ImgUrl = m_ImgUrl.group(1)
            } catch (e: Exception) {
                ImgUrl = "Not Found"
                e.printStackTrace()
            }



            var TandS = ""
            try {
                m_titleAndSinger_songs.find()
                m_fa_titleAndsinger.find()
                TandS = m_titleAndSinger_songs.group(1) + "p>"
                val m_titleAndSinger_songs2 = Pattern.compile(Ds.pt_titleAndSinger_songs2).matcher(TandS)
                m_titleAndSinger_songs2.find()
                var TandS2 = m_titleAndSinger_songs2.group(1)

                var m_title = Pattern.compile(Ds.pt_title_songs).matcher(TandS2)
                m_title.find()
                title = m_title.group(1)

                val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(TandS2)
                m_singer.find()
                singer = m_singer.group(1)

            } catch (e: Exception) {
                try {
                    val m_title2 = Pattern.compile("&#8211;(.*?)</").matcher(TandS)
                    m_title2.find()
                    title = m_title2.group(1)

                    val m_singer2 = Pattern.compile("</span>(.*?)&#8211;").matcher(TandS)
                    m_singer2.find()
                    singer = m_singer2.group(1)
                } catch (e: Exception) {
                    try {
                        title = m_fa_titleAndsinger.group(1)
                    } catch (e: Exception) {
                        title = "Not Found!"
                        singer = "Not Found!"
                        e.printStackTrace()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()

            }


        }catch (e: Exception){
            e.printStackTrace()
        }

        val ac = (activity as MainActivity)
        ac.dialog?.dismiss()
        println(ImgUrl)
        ac.SetPlayer(SongData(link, title, singer, ImgUrl, "", "", "",false,"",mp3), MusicType.Single)

        println("GetSongPageCon.onPostExecute: done")
    }
}
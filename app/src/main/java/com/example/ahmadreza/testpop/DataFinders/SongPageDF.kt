package com.example.ahmadreza.testpop.DataFinders

import android.os.AsyncTask
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.util.regex.Pattern


class SongPageDF() : AsyncTask<Int, Unit, Unit>() {

    override fun doInBackground(vararg pos: Int?) {
        val Ds = DataStorage.instance
        try {
            val arr = DataStorage.instance.arr_recentData.get(pos[0]!!)
            val url = Ds.arr_recentData.get(pos[0]!!).pageCon
            val n = url.split(Ds.sp_song_page1)[1].split(Ds.sp_song_page2)[0]

            val m_mp3s = Pattern.compile(Ds.pt_mp3_128_320).matcher(n)
            val m_disc_320 = Pattern.compile(Ds.pt_mp3_disc_320).matcher(n)
            val m_disc_128 = Pattern.compile(Ds.pt_mp3_disc_128).matcher(n)

            try {
                m_disc_320.find()
                arr.mp3.add(m_disc_320.group(1))
                m_mp3s.find()
                arr.mp3.add(m_mp3s.group(1))
            }catch (e: Exception){
                e.printStackTrace()
            }
            // 320 Qu

            try{
                m_disc_128.find()
                arr.mp3.add(m_disc_128.group(1))
                m_mp3s.find()
                arr.mp3.add(m_mp3s.group(1))

            }catch (e: Exception){
                e.printStackTrace()
            }


        }catch (e: Exception){
            e.printStackTrace()

        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)


    }
}
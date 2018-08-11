package com.example.ahmadreza.testpop.DataFinders

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.util.regex.Pattern


class SongPageDF(val activity: FragmentActivity?) : AsyncTask<ArrayList<Int>?, Unit, Int>() {

    override fun doInBackground(vararg pos: ArrayList<Int>?): Int? {
        if (pos[0]!!.get(1) != -1) {
            val Ds = DataStorage.instance
            try {
                val arr = DataStorage.instance.arr_recentData.get(pos[0]!!.get(0))
                val url = Ds.arr_recentData.get(pos[0]!!.get(0)).pageCon
                val n = url.split(Ds.sp_song_page1)[1].split(Ds.sp_song_page2)[0]

                val m_mp3s = Pattern.compile(Ds.pt_mp3_128_320).matcher(n)
                val m_disc_320 = Pattern.compile(Ds.pt_mp3_disc_320).matcher(n)
                val m_disc_128 = Pattern.compile(Ds.pt_mp3_disc_128).matcher(n)

                try {
                    m_disc_320.find()
                    arr.mp3.add(m_disc_320.group(1))
                    m_mp3s.find()
                    arr.mp3.add(m_mp3s.group(1))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // 320 Qu

                try {
                    m_disc_128.find()
                    arr.mp3.add(m_disc_128.group(1))
                    m_mp3s.find()
                    arr.mp3.add(m_mp3s.group(1))

                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
        return pos[0]!!.get(0)
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        var a = (activity as MainActivity)
        a.csetPlayPause(false)
        a.play(DataStorage.instance.arr_recentData.get(result!!).mp3.get(3), true)
    }
}
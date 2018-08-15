package com.example.ahmadreza.testpop.DataFinders

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.util.regex.Pattern


class SongPageDF(val activity: FragmentActivity?, val songData: SongData, val callType: CallType = CallType.RECENT, val context: Context = activity!!.applicationContext) : AsyncTask<ArrayList<Int>?, Unit, SongData>() {

    override fun doInBackground(vararg pos: ArrayList<Int>?): SongData? {
        if (pos[0]!!.get(1) != -1) {
            val Ds = DataStorage.instance
            try {
                val con = songData.pageCon
                val n = con.split(Ds.sp_song_page1)[1].split(Ds.sp_song_page2)[0]

                val m_mp3s = Pattern.compile(Ds.pt_mp3_128_320).matcher(n)
                val m_disc_320 = Pattern.compile(Ds.pt_mp3_disc_320).matcher(n)
                val m_disc_128 = Pattern.compile(Ds.pt_mp3_disc_128).matcher(n)

                try {
                    m_disc_320.find()
                    songData.mp3.add(m_disc_320.group(1))
                    m_mp3s.find()
                    songData.mp3.add(m_mp3s.group(1))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // 320 Qu

                try {
                    m_disc_128.find()
                    songData.mp3.add(m_disc_128.group(1))
                    m_mp3s.find()
                    songData.mp3.add(m_mp3s.group(1))

                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
        return songData
    }

    override fun onPostExecute(result: SongData?) {
        super.onPostExecute(result)
        val ac = (activity as MainActivity)
        ac.dialog?.dismiss()
        if (callType == CallType.GetMp3){
            ac.download(songData.mp3.get(1))
        }
        else{
            ac.SetPlayer(songData, MusicType.Single, ArrayList(), context)
        }

    }
}
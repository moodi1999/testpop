package com.example.ahmadreza.testpop

import android.os.AsyncTask
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class FindData : AsyncTask<String, Unit, ArrayList<SongData>>() {

    override fun doInBackground(vararg str: String): ArrayList<SongData> {

        val songdata = ArrayList<SongData>()
        val sr = Server.st

        val n = ((str[0].split(sr.bn_songs))[1].split(sr.an_songs))[0]

        val m_title = Pattern.compile(sr.pt_title_songs).matcher(n)

        val m_singer = Pattern.compile(sr.pt_singer_songs).matcher(n)

        val m_singer = Pattern.compile(sr.pt_singer_songs).matcher(n)







       return songdata
    }

}
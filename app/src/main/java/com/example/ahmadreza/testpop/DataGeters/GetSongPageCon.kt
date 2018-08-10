package com.example.ahmadreza.testpop.DataGeters

import android.os.AsyncTask
import com.example.ahmadreza.testpop.DataFinders.SongPageDF
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/5/18.
 */
class GetSongPageCon : AsyncTask<Int, Unit, Int>() {

    override fun onPreExecute() {
        super.onPreExecute()
        println("GetSongPageCon.onPreExecute")
    }

    override fun doInBackground(vararg songPo: Int?): Int {

        val url = DataStorage.instance.arr_recentData.get(songPo[0]!!).url
        val xml = StringBuilder()

        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection

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
            DataStorage.instance.arr_recentData.get(songPo[0]!!).pageCon = xml.toString()

        }
        catch (e: Exception){
            e.printStackTrace()
        }

        return songPo[0]!!
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        SongPageDF().execute(result)
        println("GetSongPageCon.onPostExecute: done")
    }
}
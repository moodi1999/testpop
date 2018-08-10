package com.example.ahmadreza.testpop.DataGeters

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.DataFinders.SongPageDF
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/5/18.
 */
class GetSongPageCon(val activity: FragmentActivity?) : AsyncTask<Int, Unit, IntArray>(){

    override fun onPreExecute() {
        super.onPreExecute()
        println("GetSongPageCon.onPreExecute")
    }

    override fun doInBackground(vararg songPo: Int?): Array<Int> {

        val arr = DataStorage.instance.arr_recentData
        if(arr.get(songPo[0]!!).pageCon.equals("")) {
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

            } catch (e: Exception) {
                e.printStackTrace()
            }

            var a = arrayOf(1,2)
            return a
        }
        else{
            return -1
        }
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        SongPageDF(activity).execute(result)
        println("GetSongPageCon.onPostExecute: done")
    }
}
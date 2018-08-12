package com.example.ahmadreza.testpop.DataGeters

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.example.ahmadreza.testpop.DataFinders.SongPageDF
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/12/18.
 */
class GetAlbumContent(val activity: FragmentActivity?, val songData: SongData, val callType: CallType) : AsyncTask<Int, Unit, ArrayList<Int>>(){

    override fun onPreExecute() {
        super.onPreExecute()
        println("GetSongPageCon.onPreExecute")
    }

    override fun doInBackground(vararg songPo: Int?): ArrayList<Int> {

        if(songData.pageCon.equals("")) {

            val xml = StringBuilder()

            try {
                val url = URL(songData.url)
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
                songData.pageCon = xml.toString()

            } catch (e: Exception) {
                e.printStackTrace()
            }


            return arrayListOf(songPo[0]!!, 1)
        }
        else{
            return arrayListOf(songPo[0]!!, -1)
        }
    }

    override fun onPostExecute(result: ArrayList<Int>?) {
        super.onPostExecute(result)

        println("GetSongPageCon.onPostExecute: done")
    }
}
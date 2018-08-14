package com.example.ahmadreza.testpop.DataGeters

import android.content.Context
import android.os.AsyncTask
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Storege.DataStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/5/18.
 */
class DownloadWebContent(val context: Context,val type: CallType) : AsyncTask<String, Unit, String>() {

    val Ds = DataStorage.instance
    override fun onPreExecute() {
        super.onPreExecute()
        println("DownloadWebContent.onPreExecute")
    }

    override fun doInBackground(vararg strs: String?): String {

        val xml = StringBuilder()

       try {

           val connection = URL(strs[0]).openConnection() as HttpURLConnection

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
           return xml.toString()

       }
       catch (e: Exception){
           e.printStackTrace()
           return ":|"
       }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        when(type){
            CallType.RECENT -> {
                if (!result.equals(":|")){
                    Ds.recentWebContent = result!!
                    Ds.recentWebContent_Done = true
                }
                else{
                    Ds.recentWebContent_Done = false
                }
            }

            CallType.CATGORY -> {
                println("got here")
                if (!result.equals(":|")){
                    Ds.item_categoWebContent = result!!
                    Ds.item_categoWebContent_Done = true
                }
                else{
                    Ds.item_categoWebContent_Done = false
                }
            }
        }

        println("DownloadWebContent.onPostExecute")
    }

}
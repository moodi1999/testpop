package com.example.ahmadreza.testpop.Data

import android.os.AsyncTask
import android.util.Log
import com.example.ahmadreza.testpop.Fragments.Recent
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/5/18.
 */
class DownloadWebContent : AsyncTask<String, Unit, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg urls: String?): String {

        val xml = StringBuilder()

       try {
           val url = URL(urls[0])
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
           return xml.toString()

       }
       catch (e: Exception){
           e.printStackTrace()
           return ":|"
       }

       /* try {
            println("got here")
            val doc = Jsoup.connect(urls[0]).get()
            val html = doc.outerHtml()
            println(html)
            println("done it")
            var dataSt = DataStorage.instance
            dataSt.recentWebContent = html
            return html
        }
        catch (e: Exception){
            println(e.printStackTrace())
            return "|:"
        }*/
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        var dataSt = DataStorage.instance
        dataSt.recentWebContent = result!!
    }
}
package com.example.ahmadreza.testpop.Data

import android.os.AsyncTask
import com.example.ahmadreza.testpop.Fragments.Recent
import org.jsoup.Jsoup

/**
 * Created by ahmadreza on 8/5/18.
 */
class DownloadWebContent : AsyncTask<String, Unit, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg urls: String?): String {
        println("doInBackground")

        try {
            println("got here")
            val doc = Jsoup.connect(urls[0]).get()
            val html = doc.outerHtml()
            //println(html)
            println("done it")
            var dataSt = DataStorage.instance
            dataSt.recentWebContent = html
            return html
        }
        catch (e: Exception){
            println(e.printStackTrace())
            return "|:"
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

    }
}
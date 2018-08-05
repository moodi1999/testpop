package com.example.ahmadreza.testpop.Data

import android.os.AsyncTask
import org.jsoup.Jsoup

/**
 * Created by ahmadreza on 8/5/18.
 */
class DownloadWebContent : AsyncTask<String, Unit, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg urls: String?): String {
        println("get herer ???????")
        val doc = Jsoup.connect(urls[0]).get()
        val html = doc.outerHtml()
        println(html)
        return html
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

    }
}
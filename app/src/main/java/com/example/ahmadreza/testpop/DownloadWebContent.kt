package com.example.ahmadreza.testpop

import android.os.AsyncTask
import org.jsoup.Jsoup

/**
 * Created by ahmadreza on 8/5/18.
 */
class DownloadWebContent : AsyncTask<String, Unit, String>() {

    override fun doInBackground(vararg urls: String?): String {

        val doc = Jsoup.connect(urls[0]).get()
        val html = doc.outerHtml()

        return html
    }
}
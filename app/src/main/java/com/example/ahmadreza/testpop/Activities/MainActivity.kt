package com.example.ahmadreza.testpop.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ahmadreza.testpop.Data.DownloadWebContent
import com.example.ahmadreza.testpop.Data.Server
import com.example.ahmadreza.testpop.R

class MainActivity : AppCompatActivity() {

    val Main_URL = "http://pop-music.ir/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val server = Server.st

    }

    fun getData(){
        var content = DownloadWebContent()
        content.execute(Main_URL).get()
    }
}

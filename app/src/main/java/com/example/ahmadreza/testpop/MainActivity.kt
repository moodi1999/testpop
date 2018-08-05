package com.example.ahmadreza.testpop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val Main_URL = "http://pop-music.ir/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getData(){
        var content = DownloadWebContent().execute(Main_URL).get()

    }
}

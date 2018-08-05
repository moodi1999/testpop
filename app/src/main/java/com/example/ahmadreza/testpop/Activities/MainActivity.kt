package com.example.ahmadreza.testpop.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.Data.DownloadWebContent
import com.example.ahmadreza.testpop.Data.Server
import com.example.ahmadreza.testpop.Fragments.Recent
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    val Main_URL = "http://pop-music.ir/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar = supportActionBar

        var viewPad = ViewPageAdaptor(supportFragmentManager)

        viewPad.addFragment(Recent(), "Recent")

        viewpager.adapter = viewPad
        tab_View_pager.setViewPager(viewpager)

        val server = Server.st

    }

    fun getData(){
        var content = DownloadWebContent()
        content.execute(Main_URL).get()
    }
}

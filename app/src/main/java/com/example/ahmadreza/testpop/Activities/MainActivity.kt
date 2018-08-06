package com.example.ahmadreza.testpop.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.Data.DownloadWebContent
import com.example.ahmadreza.testpop.Data.DataStorage
import com.example.ahmadreza.testpop.Fragments.Artists
import com.example.ahmadreza.testpop.Fragments.Categories
import com.example.ahmadreza.testpop.Fragments.Popular
import com.example.ahmadreza.testpop.Fragments.Recent
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val Main_URL = "http://pop-music.ir/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Pop Music")


        val viewPad = ViewPageAdaptor(supportFragmentManager)

        viewPad.addFragment(Recent(), "Recent")
        viewPad.addFragment(Categories(), "Ctegories")
        viewPad.addFragment(Popular(), "Artists")
        viewPad.addFragment(Artists(), "Popular")

        viewpager.adapter = viewPad
        tab_View_pager.setViewPager(viewpager)

        getData()

    }

    fun getData(){
        DownloadWebContent().execute(Main_URL)
        var dataSt = DataStorage.instance

        while (dataSt.recentWebContent == null){

        }
        print(dataSt.recentWebContent)

    }
}

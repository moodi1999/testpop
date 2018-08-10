package com.example.ahmadreza.testpop.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.Fragments.Artists
import com.example.ahmadreza.testpop.Fragments.Categories
import com.example.ahmadreza.testpop.Fragments.Popular
import com.example.ahmadreza.testpop.Fragments.Recent
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.cor_activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_slide_lay)

        uiInit()
        getData()

    }

    fun getData(){
        println("pass 0")
        DownloadWebContent().execute(DataStorage.instance.Main_URL)
        println("pass 1")
    }

    fun uiInit(){
        toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Pop Music")
        toolbar.translationX
        val viewPad = ViewPageAdaptor(supportFragmentManager)

        viewPad.addFragment(Recent(), "Recent")
        viewPad.addFragment(Categories(), "Ctegories")
        viewPad.addFragment(Artists(), "Artists")
        viewPad.addFragment(Popular(), "Popular")

        viewpager.adapter = viewPad
        tab_View_pager.setViewPager(viewpager)

        tab_View_pager.bringToFront()
        toolbar.bringToFront()


    }



}


package com.example.ahmadreza.testpop.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.DataGeters.DataStorage
import com.example.ahmadreza.testpop.Fragments.Artists
import com.example.ahmadreza.testpop.Fragments.Categories
import com.example.ahmadreza.testpop.Fragments.Popular
import com.example.ahmadreza.testpop.Fragments.Recent
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.activity_main.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.LinearInterpolator
import android.opengl.ETC1.getHeight
import android.R.attr.translationY
import android.support.v4.view.ViewCompat.setElevation
import android.os.Build
import android.annotation.TargetApi
import android.opengl.Visibility
import android.view.View
import kotlinx.android.synthetic.main.fragment_recent.*
import android.opengl.ETC1.getHeight
import android.R.attr.translationY
import android.support.v4.view.ViewCompat.setElevation
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.main_slide_lay.*


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


package com.example.ahmadreza.testpop.Adaptors

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by ahmadreza on 8/5/18.
 */
class ViewPageAdaptor(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentTitle.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle.get(position)
    }

    fun addFragment(fragment: Fragment, title: String): Unit {
        fragmentList.add(fragment)
        fragmentTitle.add(title)
    }


}
package com.example.ahmadreza.testpop.DataFinders

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.CategoRecyADP
import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.catego_first_lay.view.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/8/18.
 */
class CategoryDF(val view: View, val context: Context, val activity: FragmentActivity?) : AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg params: Unit?) {
        val Ds = DataStorage.instance
        try {
            if (!Ds.Cat_isCreated) {
                var i = 0
                while (i < 15) {

                    val name = Ds.arr_catego_name.get(i)
                    val link = Ds.arr_catego_url.get(i)

                    var categodata = CatgoData(name, link)
                    Ds.arr_categories.add(categodata)

                    i++
                }
            }
            }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onPostExecute(result: Unit?) {
        println("Category :Data set")
        DataStorage.instance.Cat_isCreated = true
        var adaptor = CategoRecyADP(view, context, DataStorage.instance.arr_categories, activity)
        view.category_recyclerView_fst.adapter = adaptor

        var contextr: Context = view.category_recyclerView_fst.context
        var contoroler: LayoutAnimationController?
        contoroler = AnimationUtils.loadLayoutAnimation(contextr, R.anim.layout_fall_down)
        view.category_recyclerView_fst.setLayoutAnimation(contoroler)
        view.category_recyclerView_fst.getAdapter().notifyDataSetChanged()
        view.category_recyclerView_fst.scheduleLayoutAnimation()
    }
}


/*
 val n = Ds.recentWebContent.split(Ds.bn_catego)[1].split(Ds.an_catego)[0]
            val m_linkandname = Pattern.compile(Ds.pt_categourlandname).matcher(n)
while (m_linkandname.find()){
                val categostr = m_linkandname.group(1)
                var name: String
                var link: String

                try {
                    val m_name = Pattern.compile(Ds.pt_categoname).matcher(categostr)
                    m_name.find()
                    name = m_name.group(1)
                }catch (e: Exception){
                    name = "Not Found!"
                }

                try {
                    link = categostr.split(Ds.sp_categourl)[0]
                }catch (e: Exception){
                    link = "Not Found!"
                }

                var categodata = CatgoData(name, link)
                Ds.arr_categories.add(categodata)
*/
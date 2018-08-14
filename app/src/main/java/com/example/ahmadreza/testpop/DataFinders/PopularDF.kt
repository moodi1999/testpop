package com.example.ahmadreza.testpop.DataFinders

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.CategoRecyADP
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.PopularRecyADP
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.Datas.PopularData
import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import kotlinx.android.synthetic.main.catego_first_lay.view.*
import kotlinx.android.synthetic.main.fragment_popular.view.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/14/18.
 */
class PopularDF(val view: View, val context: Context, val activity: FragmentActivity?) : AsyncTask<Unit, Unit, Unit>() {

    val Ds = DataStorage.instance
    override fun doInBackground(vararg params: Unit?) {

        try {
            while(Ds.recentWebContent_Done == null) {
                    //wait
                }
            if (Ds.week_con.equals("")){
                val con = Ds.recentWebContent
                Ds.week_con = con.split(Ds.sp_popu_week)[1].split(Ds.sp_popu_month)[0]
                Ds.month_con = con.split(Ds.sp_popu_month)[1].split(Ds.sp_popu_year)[0]
                Ds.year_con = con.split(Ds.sp_popu_year)[1].split(Ds.sp_popu_last)[0]

                var arrs = arrayListOf(Ds.week_con, Ds.month_con, Ds.year_con)
                for (i in (0..3)){

                    val content = arrs.get(i)
                    var url = ""
                    var title = ""

                    val m_url = Pattern.compile(Ds.pt_popu_url).matcher(content)
                    val m_title = Pattern.compile(Ds.pt_popu_title).matcher(content)

                    while (m_url.find()){

                        try {
                            url = m_url.group(1)
                        }catch (e: Exception){
                            e.printStackTrace()
                        }

                        try {
                            m_title.find()
                            var title_or = m_title.group(1)
                            title = title_or
                            println(title_or)
                        }catch (e: Exception){
                            title = "NotFound!"
                            e.printStackTrace()
                        }
                        println(title)
                        println(url)
                        val data = PopularData(url, title)

                        if (i == 0){
                            Ds.arr_popu_week.add(data)
                        }
                        else if (i == 1){
                            Ds.arr_popu_month.add(data)
                        }
                        else{
                            Ds.arr_popu_year.add(data)
                        }
                    }
                }


            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onPostExecute(result: Unit?) {
        println("Category :Data set")

        var adaptorw = PopularRecyADP(view, context, Ds.arr_popu_week, activity!!)
        view.week_recyclerview.adapter = adaptorw

        var contextrw: Context = view.week_recyclerview.context
        var contorolerw: LayoutAnimationController?
        contorolerw = AnimationUtils.loadLayoutAnimation(contextrw, R.anim.layout_fall_down)
        view.week_recyclerview.setLayoutAnimation(contorolerw)
        view.week_recyclerview.getAdapter().notifyDataSetChanged()
        view.week_recyclerview.scheduleLayoutAnimation()

        var adaptorm = PopularRecyADP(view, context, Ds.arr_popu_month, activity!!)
        view.month_recyclerview.adapter = adaptorm

        var contextrm: Context = view.month_recyclerview.context
        var contorolerm: LayoutAnimationController?
        contorolerm = AnimationUtils.loadLayoutAnimation(contextrm, R.anim.layout_fall_down)
        view.month_recyclerview.setLayoutAnimation(contorolerm)
        view.month_recyclerview.getAdapter().notifyDataSetChanged()
        view.month_recyclerview.scheduleLayoutAnimation()

        var adaptory = PopularRecyADP(view, context, Ds.arr_popu_year, activity!!)
        view.year_recyclerview.adapter = adaptory

        var contextry: Context = view.year_recyclerview.context
        var contorolery: LayoutAnimationController?
        contorolery = AnimationUtils.loadLayoutAnimation(contextry, R.anim.layout_fall_down)
        view.year_recyclerview.setLayoutAnimation(contorolery)
        view.year_recyclerview.getAdapter().notifyDataSetChanged()
        view.year_recyclerview.scheduleLayoutAnimation()
    }
}


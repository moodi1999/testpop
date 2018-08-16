package com.example.ahmadreza.testpop.DataFinders

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.RecentRecyAdp
import com.example.ahmadreza.testpop.Datas.CallMethod
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.catego_second_lay.view.*
import kotlinx.android.synthetic.main.fragment_recent.view.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class SongDataFinder(val view: View, val context: Context?, val activity: FragmentActivity?, val type: CallType, val methodType: CallMethod) : AsyncTask<String, Unit, ArrayList<SongData>>() {

    val Ds = DataStorage.instance

    override fun doInBackground(vararg str: String): ArrayList<SongData> {

        val arr :ArrayList<SongData> = ArrayList()
        try {
            if (type == CallType.CATGORY){

                while (Ds.item_categoWebContent_Done == null){
                    //wait
                }
            }
            else if(type == CallType.RECENT){
                while(Ds.recentWebContent_Done == null) {
                    //wait
                }
            }

            var content = ""
            var n = ""
            if (type == CallType.RECENT){

                content = Ds.recentWebContent
                n = ((content.split(Ds.bn_songs))[1].split(Ds.an_songs))[0]
            }
            else if (type == CallType.CATGORY){
                println("typeee ${type}")
                content = Ds.item_categoWebContent
                n = ((content.split(Ds.bn_songs_catego))[1].split(Ds.an_songs_catego))[0]
            }
            val m_link = Pattern.compile(Ds.pt_link_songs).matcher(n)
            val m_titleAndSinger_songs = Pattern.compile(Ds.pt_titleAndSinger_songs).matcher(n)
            val m_fa_titleAndsinger = Pattern.compile(Ds.pt_title_fa).matcher(n)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(n)
            val m_allcat = Pattern.compile(Ds.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(Ds.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(Ds.pt_views_songs).matcher(n)

            while (m_allcat.find()){

                var cats = ""
                var title: String = ""
                var singer: String = ""
                var link: String
                var ImgUrl: String
                var date: String
                var views: String



                try{
                    var catstr = m_allcat.group(1)
                    var m_each_cat = Pattern.compile(Ds.pt_each_cat_songs).matcher(catstr)

             /*       while (m_each_cat.find()){
                        try {
                            val cat = m_each_cat.group(1)
                            println("catsss = ${cat}")
                            cats += cat + ","
                        }catch (e: Exception){
                            println("cat1 not found")
                        }
                    }
*/
                    try {
                        m_each_cat.find()
                        var cat1 = m_each_cat.group(1)
                        println("catsss = ${cat1}")
                        cats += cat1
                    }catch (e: Exception){
                        println("cat1 not found")
                    }

                    try {
                        m_each_cat.find()
                        var cat2 = m_each_cat.group(1)
                        println("catsss = ${cat2}")
                        cats += " , " + cat2
                    }catch (e: Exception){
                        println("cat2 not found")
                    }

                    try {
                        m_each_cat.find()
                        var cat3 = m_each_cat.group(1)
                        println("catsss = ${cat3}")
                        cats += " , " + cat3
                    }catch (e: Exception){
                        println("cat3 not found")
                    }

                }
                catch (e: Exception){
                    e.printStackTrace()
                }

                try {
                    m_link.find()
                    link = m_link.group(1)
                }
                catch (e: Exception){
                    e.printStackTrace()
                    link = "http://pop-music.ir/wp-content/themes/PMWP/images/favicon.png"
                }

                try {
                    m_ImgUrl.find()
                    ImgUrl = m_ImgUrl.group(1)
                }
                catch (e: Exception){
                    ImgUrl = "Not Found"
                    e.printStackTrace()
                }

                try {
                    m_date.find()
                    date = m_date.group(1)
                }
                catch (e: Exception){
                    date = "Not Found!"
                    e.printStackTrace()
                }

                try {
                    m_views.find()
                    views = m_views.group(1)
                }
                catch (e: Exception){
                    views = "Not Found!"
                    e.printStackTrace()
                }

                var TandS = ""
                try {
                    m_titleAndSinger_songs.find()
                    m_fa_titleAndsinger.find()
                    TandS = m_titleAndSinger_songs.group(1) + "p>"
                    val m_titleAndSinger_songs2 = Pattern.compile(Ds.pt_titleAndSinger_songs2).matcher(TandS)
                    m_titleAndSinger_songs2.find()
                    var TandS2 = m_titleAndSinger_songs2.group(1)

                    var m_title = Pattern.compile(Ds.pt_title_songs).matcher(TandS2)
                    m_title.find()
                    title = m_title.group(1)

                    val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(TandS2)
                    m_singer.find()
                    singer = m_singer.group(1)

                }
                catch (e: Exception){
                    try {
                        val m_title2 = Pattern.compile("&#8211;(.*?)</").matcher(TandS)
                        m_title2.find()
                        title = m_title2.group(1)

                        val m_singer2 = Pattern.compile("</span>(.*?)&#8211;").matcher(TandS)
                        m_singer2.find()
                        singer = m_singer2.group(1)
                    }catch (e: Exception){
                        try {
                            title = m_fa_titleAndsinger.group(1)
                        }
                        catch (e: Exception){
                            title = "Not Found!"
                            singer = "Not Found!"
                            e.printStackTrace()
                        }
                    }

                }


                val songdata = SongData(link, title, singer, ImgUrl, cats, date, views)
                arr.add(songdata)
                println("${type} : size is = ${arr.size}")
            }

            return arr
        }
        catch (e : Exception){
            e.printStackTrace()
            println("something")
            return arr
        }
    }

    override fun onPostExecute(result: ArrayList<SongData>) {
        super.onPostExecute(result)
        println("SongDataFinder.onPostExecute")
        (activity as MainActivity).dialog?.dismiss()
        if (type == CallType.RECENT)  {
            if (methodType == CallMethod.OnCreat){
                Ds.arr_recentData = result
            }
            else if (methodType == CallMethod.Click){
                Ds.arr_recentData.addAll(result)
            }


            println(Ds.arr_recentData.size)
            print("\nsizeeee")

            val adaptor = RecentRecyAdp(DataStorage.instance.arr_recentData, context, activity)
            view.recent_recyclerView.adapter = adaptor

            var contextr: Context = view.recent_recyclerView.context
            var contoroler: LayoutAnimationController?
            contoroler = AnimationUtils.loadLayoutAnimation(contextr, R.anim.layout_fall_down)
            view.recent_recyclerView.setLayoutAnimation(contoroler)
            view.recent_recyclerView.getAdapter().notifyDataSetChanged()
            view.recent_recyclerView.scheduleLayoutAnimation()
        }
        else if (type == CallType.CATGORY){

            println("got here too size of the ")
            if (methodType == CallMethod.OnCreat){
                Ds.arr_catego_item_Data = result
            }
            else if (methodType == CallMethod.Click){
                Ds.arr_catego_item_Data.addAll(result)
            }


            println("size is === ${Ds.arr_catego_item_Data.size}")

            val adaptor = RecentRecyAdp(DataStorage.instance.arr_catego_item_Data, context, activity)
            view.category_recyclerView_sec.adapter = adaptor

            var contextr: Context = view.category_recyclerView_sec.context
            var contoroler: LayoutAnimationController?
            contoroler = AnimationUtils.loadLayoutAnimation(contextr, R.anim.layout_fall_down)
            view.category_recyclerView_sec.setLayoutAnimation(contoroler)
            view.category_recyclerView_sec.getAdapter().notifyDataSetChanged()
            view.category_recyclerView_sec.scheduleLayoutAnimation()

        }


    }

}

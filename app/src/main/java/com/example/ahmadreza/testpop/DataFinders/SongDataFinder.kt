package com.example.ahmadreza.testpop.DataFinders

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.RecentRecyAdp
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
class SongDataFinder : AsyncTask<String, Unit, ArrayList<SongData>> {

    val view: View
    val context: Context?
    val activity: FragmentActivity?
    val type: CallType

    constructor(view: View, context: Context?, activity: FragmentActivity?, type: CallType) : super() {
        this.view = view
        this.context = context
        this.activity = activity
        this.type = type
    }

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
            var content = str[0]
            if (type == CallType.RECENT){
                content = Ds.recentWebContent
            }
            val n = ((content.split(Ds.bn_songs))[1].split(Ds.an_songs))[0]
            val m_link = Pattern.compile(Ds.pt_link_songs).matcher(n)
            val m_titleAndSinger_songs = Pattern.compile(Ds.pt_titleAndSinger_songs).matcher(n)
            val m_fa_titleAndsinger = Pattern.compile(Ds.pt_title_fa).matcher(n)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(n)
            val m_allcat = Pattern.compile(Ds.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(Ds.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(Ds.pt_views_songs).matcher(n)

            while (m_titleAndSinger_songs.find()){

                var cats = ""
                var title: String = ""
                var singer: String = ""
                var link: String
                var ImgUrl: String
                var date: String
                var views: String


                m_allcat.find()
                try{
                    var catstr = m_allcat.group(1)
                    var m_each_cat = Pattern.compile(Ds.pt_each_cat_songs).matcher(catstr)

                    while (m_each_cat.find()){
                        try {
                            val cat = m_each_cat.group(1)
                            println("catsss = ${cat}")
                            cats += cat + ","
                        }catch (e: Exception){
                            println("cat1 not found")
                        }
                    }

                /*    try {
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
                    }*/

                }
                catch (e: Exception){
                    e.printStackTrace()
                }

                try {
                    m_link.find()
                    link = m_link.group(1)
                }
                catch (e: Exception){
                    link = "http://pop-music.ir/wp-content/themes/PMWP/images/favicon.png"
                }

                try {
                    m_ImgUrl.find()
                    ImgUrl = m_ImgUrl.group(1)
                }
                catch (e: Exception){
                    ImgUrl = "Not Found"
                }

                try {
                    m_date.find()
                    date = m_date.group(1)
                }
                catch (e: Exception){
                    date = "Not Found!"
                }

                try {
                    m_views.find()
                    views = m_views.group(1)
                }
                catch (e: Exception){
                    views = "Not Found!"
                }

                try {
                    m_fa_titleAndsinger.find()
                    var TandS: String = m_titleAndSinger_songs.group(1) + "p>"
                    val m_titleAndSinger_songs2 = Pattern.compile(Ds.pt_titleAndSinger_songs2).matcher(TandS)
                    m_titleAndSinger_songs2.find()
                    TandS = m_titleAndSinger_songs2.group(1)

                    var m_title = Pattern.compile(Ds.pt_title_songs).matcher(TandS)
                    m_title.find()
                    title = m_title.group(1)

                    val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(TandS)
                    m_singer.find()
                    singer = m_singer.group(1)

                }
                catch (e: Exception){
                    try {
                        title = m_fa_titleAndsinger.group(1)
                    }
                    catch (e: Exception){
                        title = "Not Found!"
                        singer = "Not Found!"
                    }
                }


                val songdata = SongData(link, title, singer, ImgUrl, cats, date, views)
                arr.add(songdata)
            }
            return arr
        }
        catch (e : Exception){
            e.printStackTrace()
            return arr
        }
    }

    override fun onPostExecute(result: ArrayList<SongData>) {
        super.onPostExecute(result)
        println("Recent :Data set")

        if (type == CallType.RECENT)  {
            Ds.arr_recentData.addAll(result)
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

            Ds.arr_catego_item_Data = result
            println(Ds.arr_recentData.size)
            print("\nsizeeee")

            val adaptor = RecentRecyAdp(DataStorage.instance.arr_recentData, context, activity)
            view.recent_recyclerView.adapter = adaptor

            var contextr: Context = view.category_recyclerView_sec.context
            var contoroler: LayoutAnimationController?
            contoroler = AnimationUtils.loadLayoutAnimation(contextr, R.anim.layout_fall_down)
            view.category_recyclerView_sec.setLayoutAnimation(contoroler)
            view.category_recyclerView_sec.getAdapter().notifyDataSetChanged()
            view.category_recyclerView_sec.scheduleLayoutAnimation()
        }


    }

}


/*mabe needed for futuare
   /*         try {
                    m_cat.find()
                    var all_cat = m_cat.group(1)

                    if (all_cat.length < 15){
                        catG = all_cat
                        catT = all_cat
                    }
                    else{
                        catG = all_cat.split(Ds.sp_catg_songs)[0]
                        catT = all_cat.split(Ds.sp_catg_songs)[1].split(Ds.sp_catt_songs1)[1].split(Ds.sp_catt_songs2)[0]
                    }
                }
                catch (e: Exception){
                    catG = "Not Found!"
                    catT = "Not Found!"
                }*/
 */
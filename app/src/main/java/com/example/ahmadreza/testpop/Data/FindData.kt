package com.example.ahmadreza.testpop.Data

import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.RecentRecyAdp
import com.example.ahmadreza.testpop.Fragments.Recent
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.fragment_recent.view.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class FindData(val view: View, val context: Context?) : AsyncTask<Unit, Unit, ArrayList<SongData>>() {
    val Ds = DataStorage.instance
    override fun doInBackground(vararg uni: Unit): ArrayList<SongData>? {

        try {
            while (Ds.recentWebContent == ""){
                //wait
            }
            var songdata_arr :ArrayList<SongData> = ArrayList()

            val n = ((Ds.recentWebContent.split(Ds.bn_songs))[1].split(Ds.an_songs))[0]
            val m_link = Pattern.compile(Ds.pt_link_songs).matcher(n)
            val m_titleAndSinger_songs = Pattern.compile(Ds.pt_titleAndSinger_songs).matcher(n)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(n)
            val m_cat = Pattern.compile(Ds.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(Ds.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(Ds.pt_views_songs).matcher(n)

            while (m_titleAndSinger_songs.find()){

                var catG: String
                var catT: String
                var title: String
                var singer: String
                var link: String
                var ImgUrl: String
                var date: String
                var views: String

                try {
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
                    var tands: String = m_titleAndSinger_songs.group(1) + "p>"
                    val m_titleAndSinger_songs2 = Pattern.compile(Ds.pt_titleAndSinger_songs2).matcher(tands)
                    m_titleAndSinger_songs2.find()
                    tands = m_titleAndSinger_songs2.group(1)


                        var m_title = Pattern.compile(Ds.pt_title_songs).matcher(tands)
                        m_title.find()
                        title = m_title.group(1)

                        val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(tands)
                        m_singer.find()
                        singer = m_singer.group(1)

                }
                catch (e: Exception){
                    title = "Not Found!"
                    singer = "Not Found!"

                }


                val songdata = SongData(link, title, singer, ImgUrl, catG, catT, date, views)
                Ds.arr_recentData.add(songdata)
            }
            println("number of song =  ${songdata_arr.count()}")
            return songdata_arr
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: ArrayList<SongData>?) {
        super.onPostExecute(result)
        Ds.arr_recentData = result!!
        println("Data set")

        var adaptor = RecentRecyAdp(DataStorage.instance.arr_recentData, context)
        view.recent_recyclerView.adapter = adaptor

        var contextr: Context = view.recent_recyclerView.context
        var contoroler: LayoutAnimationController? = null
        contoroler = AnimationUtils.loadLayoutAnimation(contextr, R.anim.layout_fall_down)
        view.recent_recyclerView.setLayoutAnimation(contoroler)
        view.recent_recyclerView.getAdapter().notifyDataSetChanged()
        view.recent_recyclerView.scheduleLayoutAnimation()
    }

}
package com.example.ahmadreza.testpop.Data

import android.os.AsyncTask
import com.example.ahmadreza.testpop.Fragments.Recent
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class FindData : AsyncTask<Unit, Unit, ArrayList<SongData>>() {

    override fun doInBackground(vararg uni: Unit): ArrayList<SongData>? {

        try {
            var songdata_arr :ArrayList<SongData> = ArrayList()
            val Ds = DataStorage.instance
            val n = ((Ds.recentWebContent!!.split(Ds.bn_songs))[1].split(Ds.an_songs))[0]
            val m_link = Pattern.compile(Ds.pt_link_songs).matcher(n)
            val m_title = Pattern.compile("</strong> بنام <strong>(.*?)</strong> با بالاترین کیفیت</p>").matcher(n)
            val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(n)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(n)
            val m_cat = Pattern.compile(Ds.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(Ds.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(Ds.pt_views_songs).matcher(n)


            while (m_title.find() &&  m_singer.find() && m_ImgUrl.find()
                    && m_link.find() && m_cat.find() && m_date.find()
                    && m_views.find()){
                println("gotherebefadd")
                val title = m_title.group(1)
                println("${title} titleis")
                val singer = m_singer.group(1)
                println("${singer} singer")
                val link = m_link.group(1)
                val ImgUrl = m_ImgUrl.group(1)
                val all_cat = m_cat.group(1)
                println("${all_cat} cat")
                val catG: String
                val catT: String
                if (all_cat.length < 15){
                    catG = all_cat
                    catT = all_cat
                }
                else{
                    catG = all_cat.split(Ds.sp_catg_songs)[0]
                    catT = all_cat.split(Ds.sp_catg_songs)[1].split(Ds.sp_catt_songs1)[1].split(Ds.sp_catt_songs2)[0]
                }
                val date = m_date.group(1)
                val views = m_views.group(1)



                val songdata = SongData(link, title, singer, " ", catG, catT, date, views)
                songdata_arr.add(songdata)
                println("gotherefind ${songdata_arr.count()}")
            }
            Ds.arr_recentData = songdata_arr

            return songdata_arr
        }
        catch (e : Exception){
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: ArrayList<SongData>?) {
        super.onPostExecute(result)
    }

}
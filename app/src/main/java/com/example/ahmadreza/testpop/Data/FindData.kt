package com.example.ahmadreza.testpop.Data

import android.os.AsyncTask
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class FindData : AsyncTask<String, Unit, ArrayList<SongData>>() {

    override fun doInBackground(vararg str: String): ArrayList<SongData>? {

        try {
            val songdata_arr = ArrayList<SongData>()
            val Ds = DataStorage.instance

            val n = ((str[0].split(Ds.bn_songs))[1].split(Ds.an_songs))[0]

            val m_link = Pattern.compile(Ds.pt_link_songs).matcher(n)
            val m_title = Pattern.compile(Ds.pt_title_songs).matcher(n)
            val m_singer = Pattern.compile(Ds.pt_singer_songs).matcher(n)
            val m_ImgUrl = Pattern.compile(Ds.pt_Img_Url_songs).matcher(n)
            val m_cat = Pattern.compile(Ds.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(Ds.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(Ds.pt_views_songs).matcher(n)

            while (m_link.find()){

                val link = m_link.group(1)
                val title = m_title.group(1)
                val singer = m_singer.group(1)
                val ImgUrl = m_ImgUrl.group(1)
                val all_cat = m_cat.group(1)
                var catG = ""
                var catT = ""
                if (all_cat.equals("آلبوم") && all_cat.equals("فول آلبوم") && all_cat.equals("بزودی")){
                    catG = all_cat
                    catT = all_cat
                }
                else{
                    catG = all_cat.split(Ds.sp_catg_songs)[0]
                    catT = all_cat.split(Ds.sp_catg_songs)[1].split(Ds.sp_catt_songs)[0]
                }
                val date = m_date.group(1)
                val views = m_views.group(1)



                val songdata = SongData(link, title, singer, ImgUrl, catG, catT, date, views)
                songdata_arr.add(songdata)
            }
            Ds.arr_recentData = songdata_arr
            return songdata_arr
        }
        catch (e : Exception){

        }
        return null
    }

}
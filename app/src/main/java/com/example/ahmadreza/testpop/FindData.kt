package com.example.ahmadreza.testpop

import android.media.Image
import android.os.AsyncTask
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/5/18.
 */
class FindData : AsyncTask<String, Unit, ArrayList<SongData>>() {

    override fun doInBackground(vararg str: String): ArrayList<SongData>? {

        try {
            val songdata_arr = ArrayList<SongData>()
            val sr = Server.st

            val n = ((str[0].split(sr.bn_songs))[1].split(sr.an_songs))[0]

            val m_link = Pattern.compile(sr.pt_link_songs).matcher(n)
            val m_title = Pattern.compile(sr.pt_title_songs).matcher(n)
            val m_singer = Pattern.compile(sr.pt_singer_songs).matcher(n)
            val m_ImgUrl = Pattern.compile(sr.pt_Img_Url_songs).matcher(n)
            val m_cat = Pattern.compile(sr.pt_allcat_songs).matcher(n)
            val m_date = Pattern.compile(sr.pt_date_songs).matcher(n)
            val m_views = Pattern.compile(sr.pt_views_songs).matcher(n)

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
                    catG = all_cat.split(sr.sp_catg_songs)[0]
                    catT = all_cat.split(sr.sp_catg_songs)[1].split(sr.sp_catt_songs)[0]
                }
                val date = m_date.group(1)
                val views = m_views.group(1)



                val songdata = SongData(link, title, singer, ImgUrl, catG, catT, date, views)
                songdata_arr.add(songdata)
            }

            return songdata_arr
        }
        catch (e : Exception){

        }
        return null
    }

}
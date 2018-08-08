package com.example.ahmadreza.testpop.DataFinders

import android.os.AsyncTask
import com.example.ahmadreza.testpop.DataGeters.CatgoData
import com.example.ahmadreza.testpop.DataGeters.DataStorage
import java.util.regex.Pattern

/**
 * Created by ahmadreza on 8/8/18.
 */
class CategoryDF : AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg params: Unit?) {
        val Ds = DataStorage.instance
        try {
            while (Ds.recentWebContent == "") {
                //wait
            }

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
                println("name = $name  link = $link")
                var categodata = CatgoData(name, link)
                Ds.arr_categories.add(categodata)

            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onPostExecute(result: Unit?) {

    }
}
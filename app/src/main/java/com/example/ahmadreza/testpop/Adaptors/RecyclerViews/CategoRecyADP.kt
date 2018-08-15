package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.DataFinders.SongDataFinder
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.Datas.CallMethod
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import kotlinx.android.synthetic.main.catego_second_lay.view.*
import kotlinx.android.synthetic.main.fragment_categories.view.*

/**
 * Created by ahmadreza on 8/8/18.
 */
class CategoRecyADP(val view: View, val context: Context, val arrayList: ArrayList<CatgoData>, val activity: FragmentActivity?) : RecyclerView.Adapter<CategoRecyADP.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoData = arrayList.get(position)
        holder.updateUi(categoData)

        holder.cardview?.setOnClickListener {
            getdata(categoData.url)
            DataStorage.instance.cat_currnet_url = categoData.url
            view.second.animate().translationXBy(2000f).setDuration(600).withEndAction {
                view.second.x = 0f
                view.second.y = 0f
            }

        }

        view.more_catego.setOnClickListener {

            DataStorage.instance.cat_page_num ++
            getdata(DataStorage.instance.cat_currnet_url + DataStorage.instance.page_txt + DataStorage.instance.cat_page_num.toString())
        }
    }

    fun getdata(url: String){
        (activity as MainActivity).dialog?.show()
        println("url is = $url")
        DownloadWebContent(context, CallType.CATGORY).execute(url)
        SongDataFinder(view,context,activity,CallType.CATGORY, CallMethod.Click).execute()
    }
    override fun getItemCount(): Int {
        return arrayList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catego_recy_item, parent, false)


        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var categoname_txt = itemView?.findViewById<TextView>(R.id.catego_name_txt)
        var cardview = itemView?.findViewById<CardView>(R.id.catego_item_cardview)

        fun updateUi(data: CatgoData) {
            categoname_txt?.text = data.name
        }
    }
}
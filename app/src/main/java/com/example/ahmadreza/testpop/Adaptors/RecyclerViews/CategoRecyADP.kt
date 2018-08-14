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
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.CatgoData
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.fragment_categories.view.*

/**
 * Created by ahmadreza on 8/8/18.
 */
class CategoRecyADP(val view: View, val context: Context, val arrayList: ArrayList<CatgoData>, val activity: FragmentActivity?) : RecyclerView.Adapter<CategoRecyADP.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoData = arrayList.get(position)
        holder.updateUi(categoData)

        holder.cardview?.setOnClickListener {
            (activity as MainActivity).dialog?.show()
            view.second.animate().translationXBy(2000f).setDuration(600).withEndAction {
                view.second.x = 0f
                view.second.y = 0f
            }

            println("1")
            println(categoData.name)
            println(categoData.url)
            DownloadWebContent(context, CallType.CATGORY).execute(categoData.url)
            println("2")
            SongDataFinder(view,context,activity,CallType.CATGORY).execute()
        }
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
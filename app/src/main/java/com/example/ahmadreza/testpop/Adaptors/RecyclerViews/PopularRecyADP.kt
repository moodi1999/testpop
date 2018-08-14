package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.DataFinders.SongDataFinder
import com.example.ahmadreza.testpop.DataFinders.SongPageDF
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.DataGeters.GetPopularSongPage
import com.example.ahmadreza.testpop.DataGeters.GetSongPageCon
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Datas.PopularData
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R

/**
 * Created by ahmadreza on 8/14/18.
 */
class PopularRecyADP(val view: View, val context: Context, val arrayList: ArrayList<PopularData>, val activity: FragmentActivity) : RecyclerView.Adapter<PopularRecyADP.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = arrayList.get(position)

        holder.rate?.text = position.toString()
        holder.title?.text = data.title

        holder.click?.setOnClickListener{
            val ac = (activity as MainActivity)
            ac.dialog?.show()
            GetPopularSongPage(activity, SongData(data.url, data.title, "","","","",""),MusicType.Single, CallType.POPULAR).execute()
        }

    }

    override fun getItemCount(): Int {
     return arrayList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_recy_item, parent, false)

        return ViewHolder(view)
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var title = itemView?.findViewById<TextView>(R.id.popular_artist)
        var rate = itemView?.findViewById<TextView>(R.id.rate)
        var click = itemView?.findViewById<ConstraintLayout>(R.id.popularClick)
    }
}
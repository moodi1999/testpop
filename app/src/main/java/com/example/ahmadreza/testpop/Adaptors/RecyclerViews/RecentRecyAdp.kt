package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ahmadreza.testpop.Data.SongData
import com.example.ahmadreza.testpop.R

/**
 * Created by ahmadreza on 8/6/18.
 */
class RecentRecyAdp(val arrayList: ArrayList<SongData>): RecyclerView.Adapter<RecentRecyAdp.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song:SongData = arrayList.get(position)
        holder?.updateUi(song)

    }

    override fun getItemCount(): Int {

       return arrayList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val atristTxt = itemView?.findViewById<TextView>(R.id.artist_txtv)
        val artistImg = itemView?.findViewById<ImageView>(R.id.artist_imageView)
        val songTxt = itemView?.findViewById<TextView>(R.id.song_txtv)

        fun updateUi(songdata: SongData){
            songTxt?.setText(songdata.title)
            atristTxt?.setText(songdata.singer)
        }

    }
}
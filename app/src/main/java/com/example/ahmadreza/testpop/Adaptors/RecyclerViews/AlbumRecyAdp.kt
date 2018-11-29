package com.example.ahmadreza.testpop.Adaptors.RecyclerViews


import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.slide_toolbar.*

/**
 * Created by ahmadreza on 8/13/18.
 */
class AlbumRecyAdp(val activity: FragmentActivity, songData: SongData, val album: ArrayList<AlbumData>) : RecyclerView.Adapter<AlbumRecyAdp.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = album.get(position)
        holder.name?.setText(data.song_name)

        holder.play?.setOnClickListener {
            val ac = activity as MainActivity
            ac.setPlayPause(false)
            ac.play(data.mp3, true)
            ac.song_text_small.text = data.song_name
        }
    }

    override fun getItemCount(): Int {
        return album.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_songs_recy_item, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var name = itemView?.findViewById<TextView>(R.id.album_song_name_txt)
        var play = itemView?.findViewById<ConstraintLayout>(R.id.album_song_click)
    }
}
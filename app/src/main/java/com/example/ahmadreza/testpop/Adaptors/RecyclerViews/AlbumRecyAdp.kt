package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.media.Image
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R

/**
 * Created by ahmadreza on 8/13/18.
 */
class AlbumRecyAdp(/*val context: Context,*/ songData: SongData, val album: ArrayList<AlbumData>) : RecyclerView.Adapter<AlbumRecyAdp.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = album.get(position)
        holder.name?.setText(data.song_name)

       /* holder.playimg?.setOnClickListener {
            (context as MainActivity).play(data.mp3, true)
        }*/
    }

    override fun getItemCount(): Int {
        return album.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_recy_item, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var name = itemView?.findViewById<TextView>(R.id.album_song_name_txt)
        var playimg = itemView?.findViewById<ImageView>(R.id.album_play_img)
    }
}
package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ahmadreza.testpop.Data.SongData
import com.example.ahmadreza.testpop.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ahmadreza on 8/6/18.
 */
class RecentRecyAdp(val arrayList: ArrayList<SongData>,val context: Context?): RecyclerView.Adapter<RecentRecyAdp.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song:SongData = arrayList.get(position)

        holder.updateUi(song)

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
            //DownloadImg(artistImg, context).execute()
            //Glide.with(context!!).load(songdata.Img_URL).into(artistImg!!)
            Picasso.with(context!!).load(songdata.Img_URL).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.ic_loading_img).into(artistImg)
        }

    }

    inner class DownloadImg(val artistImg: ImageView?, val context: Context?): AsyncTask<String, Void, Unit>() {

        override fun doInBackground(vararg urls: String?): Unit {
/*
            if (urls[0] != null && !urls[0].equals("Not Found!")){
                Picasso.with(context).load(urls[0]).placeholder(R.drawable.ic_loading_img).into(artistImg)
            }
            else{*/
                //Picasso.with(context).load(R.drawable.ic_loading_img).into(artistImg)
            //}
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
        }
    }
}
package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

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
import com.example.ahmadreza.testpop.Data.SongData
import com.example.ahmadreza.testpop.R
import java.net.HttpURLConnection
import java.net.URL

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
            /*if(atristTxt?.text.toString().equals(songdata.singer)){
                println("yes")
            }else{
                DownloadImg(artistImg).execute(songdata.Img_URL)
                println("No")
            }*/
            songTxt?.setText(songdata.title)
            atristTxt?.setText(songdata.singer)

        }

    }

    inner class DownloadImg(val artistImg: ImageView?): AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String?): Bitmap {
            try {
                val url = URL(urls[0])
                var connect = url.openConnection() as HttpURLConnection
                connect.connect()
                var instream = connect.inputStream
                var bitmap = BitmapFactory.decodeStream(instream)

                return bitmap
            }
            catch (e: Exception){
                e.printStackTrace()
                return null!!
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            artistImg?.setImageBitmap(result)
        }
    }
}
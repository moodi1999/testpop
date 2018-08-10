package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ahmadreza.testpop.DataGeters.GetSongPageCon
import com.example.ahmadreza.testpop.DataGeters.SongData
import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

/**
 * Created by ahmadreza on 8/6/18.
 */
class RecentRecyAdp(val arrayList: ArrayList<SongData>,val context: Context?): RecyclerView.Adapter<RecentRecyAdp.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song:SongData = arrayList.get(position)

        for (i in DataStorage.instance.set_favo){
            if (song.title == i.title){
                holder.faveBtn?.setImageResource(R.drawable.ic_fave_checked)
            }
        }
        holder.updateUi(song)

        holder.faveBtn?.setOnClickListener {

            if (song.fave){
                DataStorage.instance.set_favo.remove(song)
                holder.faveBtn.setImageResource(R.drawable.ic_not_fave)
                song.fave = false
            }
            else{
                DataStorage.instance.set_favo.add(song)
                holder.faveBtn.setImageResource(R.drawable.ic_fave_checked)
                song.fave = true
            }

            var sonddata = DataStorage.instance.set_favo.elementAt(0)
            println(sonddata.title)
            println(DataStorage.instance.set_favo!!.size)
        }
        holder.detBtn?.setOnClickListener {
            println("Det")
        }

        holder.card?.setOnClickListener {
            GetSongPageCon().execute(position)
        }


    }

    override fun getItemCount(): Int {

       return arrayList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_recy_item, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        val atristTxt = itemView?.findViewById<TextView>(R.id.artist_txtv)
        val artistImg = itemView?.findViewById<ImageView>(R.id.artist_imageView)
        val faveBtn = itemView?.findViewById<ImageButton>(R.id.fav_img)
        val detBtn = itemView?.findViewById<ImageButton>(R.id.det_img)
        val songTxt = itemView?.findViewById<TextView>(R.id.song_txtv)
        val card = itemView?.findViewById<ConstraintLayout>(R.id.conscard)

        fun updateUi(songdata: SongData){
            songTxt?.setText(songdata.title)
            atristTxt?.setText(songdata.singer)

            Picasso.with(context!!).load(songdata.Img_URL).resize(190, 200).memoryPolicy(MemoryPolicy.NO_STORE).priority(Picasso.Priority.HIGH).placeholder(R.drawable.ic_loading_img).error(R.drawable.ic_carsh_img).into(artistImg)
        }

    }
}
package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.content.DialogInterface
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.ahmadreza.testpop.DataGeters.GetSongPageCon
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

/**
 * Created by ahmadreza on 8/11/18.
 */
class CategoSongItemADP (val arrayList: ArrayList<SongData>, val context: Context?, val activity: FragmentActivity?): RecyclerView.Adapter<CategoSongItemADP.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song: SongData = arrayList.get(position)

        for (i in DataStorage.instance.set_favo){
            if (song.title == i.title){
                holder.faveBtn?.setImageResource(R.drawable.ic_fave_checked)
            }
        }
        holder.updateUi(song)

        holder.faveBtn?.setOnClickListener {

            var isin = false
            for (i in DataStorage.instance.set_favo){
                if (song.title == i.title){
                    isin = true
                    break
                }
            }
            println("faveeeee")
            if (!isin) {
                println("in1")
                if (!song.fave) {
                    println("in0")
                    DataStorage.instance.set_favo.add(song)
                    holder.faveBtn.setImageResource(R.drawable.ic_fave_checked)
                    song.fave = true
                }
            }else{
                println("out1")
                if (song.fave) {
                    println("out0")
                    DataStorage.instance.set_favo.remove(song)
                    holder.faveBtn.setImageResource(R.drawable.ic_not_fave)
                    song.fave = false
                }
            }


        }
        holder.card?.setOnClickListener {
            if (song.category_tag.equals("بزودی", true)) {

                var builder = AlertDialog.Builder(context!!)
                var alertdialog: AlertDialog? = null
                builder.setTitle("\nComing Soon!!")
                alertdialog = builder.create()
                alertdialog.show()

            } else {
                GetSongPageCon(activity).execute(position)
            }
        }


        holder.detBtn?.setOnClickListener {
            var builder = AlertDialog.Builder(context!!)
            var alertdialog: AlertDialog? = null
            builder.setIcon(R.drawable.ic_det_dialog)
            builder.setTitle("\nDetails :")
            builder.setMessage("\nTitle:  ${song.title}\n\nArtist: ${song.singer}\n\nCategory:  ${song.category_tag}\n\nViews:  ${song.views}\n\nDate:  ${song.Date}")
            builder.setPositiveButton("Ok",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    alertdialog?.cancel()
                }
            })
            alertdialog = builder.create()
            alertdialog!!.show()
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
        val tap = itemView?.findViewById<TextView>(R.id.tap_image)
        val album = itemView?.findViewById<TextView>(R.id.album_item_txt)

        fun updateUi(songdata: SongData){
            songTxt?.setText(songdata.title)
            atristTxt?.setText(songdata.singer)
            if (songdata.category_tag.equals("آلبوم",true)){
                album?.visibility = View.VISIBLE
            }

            tap?.setOnClickListener {
                try {
                    Picasso.with(context!!).load(songdata.Img_URL).resize(190, 200).memoryPolicy(MemoryPolicy.NO_STORE).priority(Picasso.Priority.HIGH).placeholder(R.drawable.ic_loading_img).error(R.drawable.ic_carsh_img).into(artistImg)
                }catch (e: Exception){
                    e.printStackTrace()
                }
                tap.visibility = View.GONE
                card?.bringToFront()
            }
        }

    }
}
package com.example.ahmadreza.testpop.Adaptors.RecyclerViews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ahmadreza.testpop.DataGeters.CatgoData
import com.example.ahmadreza.testpop.R

/**
 * Created by ahmadreza on 8/8/18.
 */
class CategoRecyADP(val arrayList: ArrayList<CatgoData>, val context: Context) : RecyclerView.Adapter<CategoRecyADP.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoData = arrayList.get(position)
        holder.updateUi(categoData)
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

        fun updateUi(data: CatgoData) {
            categoname_txt?.text = data.name
        }
    }
}
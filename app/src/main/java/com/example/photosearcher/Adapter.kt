package com.example.photosearcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class Adapter(private var adapterList: ArrayList<String>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(adapterList[position]).into(holder.rowImage);
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val picture = null
        var rowImage: ImageView? = ItemView.findViewById(R.id.imageView)
    }

    fun updateData(newItems: ArrayList<String>) {
        adapterList = newItems
        notifyDataSetChanged()
    }


}
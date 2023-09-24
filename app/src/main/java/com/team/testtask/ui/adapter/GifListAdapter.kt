package com.team.testtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.testtask.R
import com.team.testtask.domain.model.GifImage

class GifListAdapter(
    private val gifList: List<GifImage>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<GifListAdapter.GifViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return GifViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = gifList[position]
        Glide.with(holder.itemView.context).asGif().load(item.fixWidthUrl).error(R.drawable.ic_no_image).into(holder.image)
    }

    inner class GifViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val image: ImageView = itemView.findViewById(R.id.img_holder)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(position)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(position: Int)
}
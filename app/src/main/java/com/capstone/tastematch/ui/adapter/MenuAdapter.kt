package com.capstone.tastematch.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tastematch.R
import com.capstone.tastematch.response.ResponseItem
import com.capstone.tastematch.presentation.detail.DetailActivity
import com.google.android.material.imageview.ShapeableImageView

class MenuAdapter(private val context: Context, private var dataList: List<ResponseItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RESPONSE_ITEM, currentItem)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodImageView: ShapeableImageView = itemView.findViewById(R.id.image_photo)
        private val foodNameTextView: TextView = itemView.findViewById(R.id.text_name)
        private val createdAtTextView: TextView = itemView.findViewById(R.id.createdAt)

        fun bind(responseItem: ResponseItem) {
            // Set item data to views
            foodNameTextView.text = responseItem.menu
            createdAtTextView.text = responseItem.kalori.toString()
            // Load image with Glide and placeholder
            Glide.with(itemView)
                .load(responseItem.imageURL)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(foodImageView)
        }
    }
}

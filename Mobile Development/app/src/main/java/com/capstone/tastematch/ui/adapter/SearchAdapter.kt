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

class SearchAdapter(private val context: Context, private var dataList: List<ResponseItem>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var itemClickListener: ((ResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseItem) -> Unit) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.foodNameTextView.text = currentItem.menu
        holder.caloriesTextView.text = "${currentItem.kalori} kkal / serving"

        Glide.with(context)
            .load(currentItem.imageURL)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.foodImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RESPONSE_ITEM, currentItem)
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDataList(newDataList: List<ResponseItem>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImageView: ShapeableImageView = itemView.findViewById(R.id.recImage)
        val foodNameTextView: TextView = itemView.findViewById(R.id.recTitle)
        val caloriesTextView: TextView = itemView.findViewById(R.id.recDesc)
    }
}

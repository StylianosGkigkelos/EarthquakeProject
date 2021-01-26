package com.icsd17029.earthquakeproject.features.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel
import kotlinx.android.synthetic.main.item_my_list.view.*

class MyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(firstGradeCheckModel: FirstGradeCheckModel, clickListener: OnItemClickListener) {
        itemView.item_id.text = firstGradeCheckModel.id
        itemView.setOnClickListener {
            clickListener.onItemClicked(firstGradeCheckModel)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: FirstGradeCheckModel)
    }
}
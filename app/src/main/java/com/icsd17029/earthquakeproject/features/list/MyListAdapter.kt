package com.icsd17029.earthquakeproject.features.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.icsd17029.earthquakeproject.R
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel

class MyListAdapter(private val clickListener: MyListViewHolder.OnItemClickListener): ListAdapter<FirstGradeCheckModel, MyListViewHolder>(MyListDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyListViewHolder(inflater.inflate(R.layout.item_my_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


}
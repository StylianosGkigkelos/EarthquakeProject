package com.icsd17029.earthquakeproject.features.list

import androidx.recyclerview.widget.DiffUtil
import com.icsd17029.earthquakeproject.internal.models.FirstGradeCheckModel

class MyListDiffUtil : DiffUtil.ItemCallback<FirstGradeCheckModel>() {
       override fun areItemsTheSame(oldItem: FirstGradeCheckModel, newItem: FirstGradeCheckModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FirstGradeCheckModel, newItem: FirstGradeCheckModel): Boolean {
        return oldItem == newItem
    }
}
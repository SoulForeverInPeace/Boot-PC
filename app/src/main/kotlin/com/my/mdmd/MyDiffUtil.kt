package com.my.mdmd

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtil(
    private val oldList: List<fileItem>,
    private val newList: List<fileItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fileName == newList[newItemPosition].fileName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].fileImage == newList[newItemPosition].fileImage-> true            
            else -> false
        }
    }
}
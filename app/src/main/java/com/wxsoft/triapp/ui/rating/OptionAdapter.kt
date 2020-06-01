package com.wxsoft.triapp.ui.rating


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wxsoft.triapp.data.entity.rating.Option
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.Subject
import com.wxsoft.triapp.databinding.ItemRatingSubjectItemBinding


class OptionAdapter constructor(private val owner: LifecycleOwner,
                                private val rating:Rating?,
                                var subject: Subject?=null):
    ListAdapter<Option,OptionAdapter.ItemViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return  ItemViewHolder(
            ItemRatingSubjectItemBinding.inflate(inflater, parent, false).apply {
                lifecycleOwner = owner
            }
        )

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.binding.apply {
                rat=rating
                subject=this@OptionAdapter.subject
                item=getItem(position)
                index=(position+1).toString()

                executePendingBindings()
            }
    }

    object DiffCallback : DiffUtil.ItemCallback<Option>() {
        override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean {
            return  oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean {
            return oldItem.checked == newItem.checked
        }
    }

    class ItemViewHolder(val binding: ItemRatingSubjectItemBinding) : RecyclerView.ViewHolder(binding.root)
}





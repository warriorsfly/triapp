package com.wxsoft.triapp.ui.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.Subject
import com.wxsoft.triapp.databinding.ItemRatingSubjectBinding


class SubjectAdapter constructor(private val owner: LifecycleOwner,
                                 private var rat: Rating?=null):
    ListAdapter<Subject,SubjectAdapter.ItemViewHolder>(DiffCallback) {

//    fun setRat(rating:Rating){
//        rat=rating
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            ItemRatingSubjectBinding.inflate(inflater, parent, false).apply {
//                optionList.setRecycledViewPool(pool)
                optionList.adapter=OptionAdapter(owner,rat)
                lifecycleOwner=owner
            }
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            val sub=getItem(position)
            item=sub
            (optionList.adapter as? OptionAdapter)?.apply {
                subject=sub
                submitList(sub.options)
            }
            executePendingBindings()
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.options == newItem.options
        }
    }

    class ItemViewHolder(val binding: ItemRatingSubjectBinding) : RecyclerView.ViewHolder(binding.root)
}





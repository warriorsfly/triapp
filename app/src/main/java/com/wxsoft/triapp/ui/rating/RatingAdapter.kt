package com.wxsoft.triapp.ui.rating


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wxsoft.triapp.data.entity.rating.RatingRecord
import com.wxsoft.triapp.databinding.ItemRatingResultBinding


class RatingAdapter constructor(private val owner: LifecycleOwner,
                                private val showDetail:(RatingRecord)->Unit):
    ListAdapter<RatingRecord,RatingAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ItemViewHolder.ScencelyViewHolder(ItemRatingResultBinding.inflate(inflater, parent, false).apply {

            root.setOnClickListener {
                item?.let { showDetail(it) }
            }
            lifecycleOwner = owner
        })
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        when(holder){
            is ItemViewHolder.ScencelyViewHolder->{
                holder.binding.apply {
                    item=getItem(position)
                    executePendingBindings()
                }
            }
        }

    }

    sealed class ItemViewHolder(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {


        class ScencelyViewHolder(override val binding: ItemRatingResultBinding) : ItemViewHolder(binding)
    }

    object DiffCallback : DiffUtil.ItemCallback<RatingRecord>() {
        override fun areItemsTheSame(oldItem: RatingRecord, newItem: RatingRecord): Boolean {
            return oldItem.Id==newItem.Id
        }

        override fun areContentsTheSame(oldItem: RatingRecord, newItem: RatingRecord): Boolean {
            return oldItem.score==newItem.score
        }
    }
}




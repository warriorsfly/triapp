package com.wxsoft.triapp.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wxsoft.triapp.data.entity.Entity


/**
 * 防leak memory
 */
@Suppress("LeakingThis")
abstract class AtomNoneAdapter<T,B>(diff:AtomNoneDiffUtil<T>, @LayoutRes val resource:Int):ListAdapter<T,AtomViewHolder<B>>(diff) where B:ViewDataBinding


abstract class AtomNoneDiffUtil<T>: DiffUtil.ItemCallback<T>()
/**
 * 防leak memory
 */
@Suppress("LeakingThis")
abstract class AtomAdapter<T,B>(diff:AtomDiffUtil<T>, @LayoutRes resource:Int):AtomNoneAdapter<T,B>(diff,resource) where T:Entity, B:ViewDataBinding


abstract class AtomDiffUtil<T>: AtomNoneDiffUtil<T>() where T: Entity {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {

        return oldItem.Id == newItem.Id
    }
}

class AtomViewHolder<T>(binding: T) : RecyclerView.ViewHolder(binding.root)where T:ViewDataBinding {
    var binding: T
        private set
    init {
        this.binding = binding
    }
}
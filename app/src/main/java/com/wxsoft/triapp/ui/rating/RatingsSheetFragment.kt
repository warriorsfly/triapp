package com.wxsoft.triapp.ui.rating

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.databinding.FragmentRatingsSheetBinding
import com.wxsoft.triapp.databinding.ItemRatingSheetBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RatingsSheetFragment constructor( private val itemClick: (Rating) -> Unit) : BottomSheetDialogFragment()  {

    companion object {
        const val TAG="RatingsSheetFragment"
    }
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    var patientId=0
    private lateinit var viewModel: RatingViewModel
    private lateinit var adapter: ItemAdapter

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activityViewModelProvider(factory)
        if(viewModel.patientId==0){
            viewModel.patientId=patientId
        }
        adapter=ItemAdapter(this@RatingsSheetFragment,itemClick)
        viewModel.ratings.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return FragmentRatingsSheetBinding.inflate(inflater,container,false).apply {
            list.adapter=this@RatingsSheetFragment.adapter
            canncel.setOnClickListener { this@RatingsSheetFragment.dismiss() }
            lifecycleOwner=this@RatingsSheetFragment
        }.root
    }



    private class ItemAdapter constructor(private val owner: LifecycleOwner,private  val itemClick:(Rating)->Unit) :
        ListAdapter<Rating,ItemAdapter.ItemViewHolder>(DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val inflater=LayoutInflater.from(parent.context)
            return ItemViewHolder(ItemRatingSheetBinding.inflate(inflater,parent,false) , itemClick)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
           holder.binding.apply {
               item = getItem(position)
               lifecycleOwner=owner
               executePendingBindings()
           }

        }

        object DiffCallback : DiffUtil.ItemCallback<Rating>() {
            override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
                return oldItem.Id==newItem.Id
            }

            override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
                return oldItem.Id==newItem.Id
            }
        }

        class ItemViewHolder(bind: ItemRatingSheetBinding,itemClick:(Rating)->Unit) : RecyclerView.ViewHolder(bind.root) {

            var binding: ItemRatingSheetBinding
                private set

            init {
                this.binding = bind.apply {
                    root.setOnClickListener {
                        item?.let{itemClick(it)}
                    }
                }


            }

        }
    }
}

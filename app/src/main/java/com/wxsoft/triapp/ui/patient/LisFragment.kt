package com.wxsoft.triapp.ui.patient


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.viewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.*
import com.wxsoft.triapp.data.entity.Report
import com.wxsoft.triapp.databinding.FragmentLisBinding
import com.wxsoft.triapp.databinding.ItemReportBinding
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentReportDetail
import com.wxsoft.triapp.ui.common.FragmentReportDetail2
import kotlinx.android.synthetic.main.fragment_lis.*
import javax.inject.Inject

class LisFragment(private val blh:String,private val flag:Int) : BaseFragment() {

    private lateinit var viewModel: LisModel

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var binding: FragmentLisBinding

    lateinit var adapter: AtomNoneAdapter<Report, ItemReportBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(factory)
        viewModel.flag=flag
        viewModel.blh=blh
        adapter = object : AtomNoneAdapter<Report, ItemReportBinding>(object : AtomNoneDiffUtil<Report>() {
            override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem.ApplyNo==newItem.ApplyNo
            }
            override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem.ReportTime == newItem.ReportTime
            }

        }, R.layout.item_report) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AtomViewHolder<ItemReportBinding> {

                val binding =
                    ItemReportBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AtomViewHolder(binding)
            }

            override fun onBindViewHolder(
                holder: AtomViewHolder<ItemReportBinding>,
                position: Int
            ) {

                holder.binding.apply {
                    item = getItem(position)
                    root.setOnClickListener {
                        showDetail=true
                        viewModel.loadDetails(getItem(position).ApplyNo)
                    }
                    executePendingBindings()
                }
            }

        }


        binding = FragmentLisBinding.inflate(inflater, container, false).apply {
            recyclerview.adapter=this@LisFragment.adapter
            swipe.setOnRefreshListener {
                viewModel?.refresh()
            }
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.reports.observe(viewLifecycleOwner, Observer { list ->
           adapter.submitList(list)
            binding.swipe.isRefreshing=false
        })


        viewModel.details.observe(viewLifecycleOwner, Observer {

            if(!showDetail)return@Observer
            if (flag==0){
                val fragment = FragmentReportDetail(it)
                fragment.show(childFragmentManager,"all")
                showDetail=false
            }else{

                val ls = listOf(it.first { item->item.ItemCode=="bw" },it.first { item->item.ItemCode=="jcjl"},it.first { item->item.ItemCode=="jcsj" })
                val fragment =  FragmentReportDetail2(ls)
                fragment.show(childFragmentManager,"all")
                showDetail=false
            }

        })


        return binding.root
    }

    var showDetail=false
    override fun onPause() {
        super.onPause()
        showDetail=false
    }
}



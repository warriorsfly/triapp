package com.wxsoft.triapp.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wxsoft.triapp.App
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.*
import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.data.entity.ReportDetail
import com.wxsoft.triapp.databinding.ItemReportItem2Binding
import com.wxsoft.triapp.databinding.ItemReportItemBinding
import kotlinx.android.synthetic.main.new_dictionary_picker.*

class FragmentReportDetail2(private val details:List<ReportDetail>) : BottomSheetDialogFragment() {


    lateinit var adapter: AtomNoneAdapter<ReportDetail, ItemReportItem2Binding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_dictionary_picker, container, false)

        adapter = object : AtomNoneAdapter<ReportDetail, ItemReportItem2Binding>(
            object : AtomNoneDiffUtil<ReportDetail>() {
                override fun areContentsTheSame(oldItem: ReportDetail, newItem: ReportDetail): Boolean {
                    return oldItem.ItemName == newItem.ItemName && oldItem.Result==newItem.Result
                }

                override fun areItemsTheSame(oldItem: ReportDetail, newItem: ReportDetail): Boolean {
                    return  oldItem.ItemCode==newItem.ItemCode
                }

            }, R.layout.item_report_item2
        ) {
            override fun onBindViewHolder(
                holder: AtomViewHolder<ItemReportItem2Binding>,
                position: Int
            ) {
                holder.binding.apply {
                    item = getItem(position)

                }.executePendingBindings()

            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AtomViewHolder<ItemReportItem2Binding> {
                val binding =
                    ItemReportItem2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AtomViewHolder(binding)

            }

        }


        view.findViewById<RecyclerView>(R.id.list)?.adapter=adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(details)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(context!!, R.style.BottomSheetDialogTheme)
    }

}
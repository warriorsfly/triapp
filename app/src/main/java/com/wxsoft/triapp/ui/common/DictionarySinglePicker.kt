package com.wxsoft.triapp.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wxsoft.triapp.App
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.AtomAdapter
import com.wxsoft.triapp.adapter.AtomDiffUtil
import com.wxsoft.triapp.adapter.AtomViewHolder
import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.databinding.ItemDictionaryBinding
import kotlinx.android.synthetic.main.new_dictionary_picker.*

class DictionarySinglePicker(private val dictionarySelected:(Dictionary)->Unit) : BottomSheetDialogFragment() {


    lateinit var adapter: AtomAdapter<Dictionary, ItemDictionaryBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_dictionary_picker, container, false)

        adapter = object : AtomAdapter<Dictionary, ItemDictionaryBinding>(
            object : AtomDiffUtil<Dictionary>() {
                override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
                    return oldItem.Name == newItem.Name
                }

                override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
                    return super.areItemsTheSame(oldItem, newItem) && oldItem.Key==newItem.Key
                }

            }, R.layout.item_dictionary
        ) {
            override fun onBindViewHolder(
                holder: AtomViewHolder<ItemDictionaryBinding>,
                position: Int
            ) {
                holder.binding.apply {
                    dic = getItem(position)
                    root.setOnClickListener {
                        if(getItem(position).Id==0){
                            dismiss()
                        }else {
                            dictionarySelected(getItem(position))
                        }
                    }
                }.executePendingBindings()

            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AtomViewHolder<ItemDictionaryBinding> {
                val binding =
                    ItemDictionaryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AtomViewHolder(binding)

            }

        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.adapter=adapter

        arguments?.getInt("parentId")?.let {parentId->
            val list= mutableListOf<Dictionary>().apply {
                addAll(App.dictionaries.filter { it.ParentId== parentId})
                add(Dictionary(Id = 0,ParentId = 0,Name = "取消"))
            }
            adapter.submitList(list)

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(context!!, R.style.BottomSheetDialogTheme)
    }

}
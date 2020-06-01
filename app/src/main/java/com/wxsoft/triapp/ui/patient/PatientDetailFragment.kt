package com.wxsoft.triapp.ui.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.AtomAdapter
import com.wxsoft.triapp.adapter.AtomDiffUtil
import com.wxsoft.triapp.adapter.AtomViewHolder
import com.wxsoft.triapp.data.entity.Rescue
import com.wxsoft.triapp.databinding.ActivityPatientEditBinding
import com.wxsoft.triapp.databinding.ItemTreatmentBinding
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentArgs
import com.wxsoft.triapp.utils.launchForResult
import javax.inject.Inject

class PatientDetailFragment(private val patientId:Int) : BaseFragment() {


    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var adapter: AtomAdapter<Rescue, ItemTreatmentBinding>
    lateinit var binding: ActivityPatientEditBinding

    private lateinit var viewModel: PatientRescueModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activityViewModelProvider(factory)
        viewModel.patientId=patientId
        adapter = object : AtomAdapter<Rescue, ItemTreatmentBinding>(object :
            AtomDiffUtil<Rescue>() {
            override fun areContentsTheSame(
                oldItem: Rescue,
                newItem: Rescue
            ): Boolean {
                return oldItem.decubitus == newItem.decubitus
            }

        }, R.layout.item_treatment) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AtomViewHolder<ItemTreatmentBinding> {
                val binding =
                    ItemTreatmentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AtomViewHolder(binding)
            }

            override fun onBindViewHolder(
                holder: AtomViewHolder<ItemTreatmentBinding>,
                position: Int
            ) {
                holder.binding.apply {
                    item = getItem(position)
                    edit.setOnClickListener {
                        launchForResult(
                            PatientRescueFragment::class.java,

                            args = FragmentArgs().apply {
                                add("recordId", viewModel.rescueRecord.value?.Id?:0)
                                add("rescueId", getItem(position).Id)
                                add("patientId", patientId)
                            },
                            requestCode = REQUEST_CODE_RESCUE_DETAIL
                        )
                    }
                    executePendingBindings()
                }
            }

        }
        binding = ActivityPatientEditBinding.inflate(inflater, container, false).apply {

            viewModel = this@PatientDetailFragment.viewModel
//            recyclerview.adapter = this@PatientDetailFragment.adapter
            lifecycleOwner = this@PatientDetailFragment

        }

        viewModel.rescueRecord.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.rescues)
        })

        viewModel.patient.observe(viewLifecycleOwner, Observer {
            val gens= if( it.gender==1) "男" else "女"

            activity?.title = "${it.name} $gens ${it.age}${it.ageUni}"
        })

        return binding.root


    }
}

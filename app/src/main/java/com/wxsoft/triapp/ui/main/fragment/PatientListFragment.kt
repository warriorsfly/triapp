package com.wxsoft.triapp.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.AtomAdapter
import com.wxsoft.triapp.adapter.AtomDiffUtil
import com.wxsoft.triapp.adapter.AtomViewHolder
import com.wxsoft.triapp.data.entity.PatientWithDetail
import com.wxsoft.triapp.databinding.FragmentPatientListBinding
import com.wxsoft.triapp.databinding.ItemPatientMainBinding
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentArgs
import com.wxsoft.triapp.ui.patient.PatientActivity
import com.wxsoft.triapp.ui.patient.PatientRescueListFragment
import com.wxsoft.triapp.utils.launch
import javax.inject.Inject

class PatientListFragment() : BaseFragment() {


    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var adapter: AtomAdapter<PatientWithDetail, ItemPatientMainBinding>
    lateinit var binding: FragmentPatientListBinding

    private lateinit var viewModel: PatientWithDetailModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activityViewModelProvider(factory)

        adapter = object : AtomAdapter<PatientWithDetail, ItemPatientMainBinding>(object :
            AtomDiffUtil<PatientWithDetail>() {
            override fun areContentsTheSame(
                oldItem: PatientWithDetail,
                newItem: PatientWithDetail
            ): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.level == newItem.level
                        && oldItem.clinicTime == newItem.clinicTime
                        && oldItem.onset == newItem.onset
                        && oldItem.areaname == newItem.areaname
                        && oldItem.bedName == newItem.bedName
            }

        }, R.layout.item_patient_main) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AtomViewHolder<ItemPatientMainBinding> {
                val binding =
                    ItemPatientMainBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AtomViewHolder(binding)
            }

            override fun onBindViewHolder(
                holder: AtomViewHolder<ItemPatientMainBinding>,
                position: Int
            ) {
                holder.binding.apply {
                    patient = getItem(position)
                    root.setOnClickListener {
                        Intent(activity,PatientActivity::class.java).apply {
                            putExtra("patientId", getItem(position).Id)
                            putExtra("blh", getItem(position).blh)
                        }.let {
                            startActivity(it)
                        }
//
                    }
                    executePendingBindings()
                }
            }

        }
        binding = FragmentPatientListBinding.inflate(inflater, container, false).apply {

            viewModel = this@PatientListFragment.viewModel

            swipe.setOnRefreshListener {
                viewModel?.refresh()
            }
            recyclerview.adapter = this@PatientListFragment.adapter
//            recyclerview.addItemDecoration(object :RecyclerView.ItemDecoration(){})
            searching.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel?.queryString = p0 ?: ""
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    if(p0.isNullOrEmpty()){
                        viewModel?.queryString = ""
                        return true
                    }
                    return false
                }
            })
            searching.time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel?.times?.value?.get(p2)?.let {
                        viewModel?.setTime(it)
                    }
                }

            }

            searching.passWay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel?.levels?.value?.get(p2)?.let {
                        viewModel?.setType(it)
                    }
                }

            }
            lifecycleOwner = this@PatientListFragment

        }

        viewModel.patients.observe(viewLifecycleOwner, Observer {
            binding.swipe.isRefreshing=false
            adapter.submitList(it)

        })

        return binding.root


    }

}

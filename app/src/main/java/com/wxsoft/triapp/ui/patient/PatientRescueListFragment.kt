package com.wxsoft.triapp.ui.patient

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.adapter.AtomAdapter
import com.wxsoft.triapp.adapter.AtomDiffUtil
import com.wxsoft.triapp.adapter.AtomViewHolder
import com.wxsoft.triapp.data.entity.Rescue
import com.wxsoft.triapp.databinding.FragmentListBinding
import com.wxsoft.triapp.databinding.ItemTreatmentBinding
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentArgs
import com.wxsoft.triapp.ui.widgets.FragmentContainerActivity
import com.wxsoft.triapp.utils.launch
import com.wxsoft.triapp.utils.launchForResult
import javax.inject.Inject

class PatientRescueListFragment(private val patientId:Int) : BaseFragment() {


    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var adapter: AtomAdapter<Rescue, ItemTreatmentBinding>
    lateinit var binding: FragmentListBinding

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
                    root.setOnClickListener {
                        launchForResult(PatientRescueFragment::class.java,args = FragmentArgs().apply {
                            add("recordId",viewModel.rescueRecord.value?.Id?:0)
                            add("rescueId",getItem(position).Id)
                            add("patientId",patientId)

                        },requestCode = REQUEST_CODE_RESCUE_DETAIL)
                    }
                    executePendingBindings()
                }
            }

        }
        binding = FragmentListBinding.inflate(inflater, container, false).apply {

            viewModel=this@PatientRescueListFragment.viewModel
            recyclerview.adapter = this@PatientRescueListFragment.adapter
            recyclerview.setBackgroundColor(Color.parseColor("#D9D9E0"))

            swipe.setOnRefreshListener {
                viewModel?.refresh()
            }
            lifecycleOwner=this@PatientRescueListFragment

        }

        viewModel.rescueRecord.observe(viewLifecycleOwner, Observer {
            binding.swipe.isRefreshing=false
            adapter.submitList(it.rescues)
        })

        setHasOptionsMenu(true)
        val activity = activity as PatientActivity?
        activity?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        return binding.root


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_RESCUE_DETAIL->viewModel.getPatientRescue()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_adding, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                launchForResult(PatientRescueFragment::class.java,args = FragmentArgs().apply {
                    add("recordId",viewModel.rescueRecord.value?.Id?:0)

                    add("patientId",patientId)
                },requestCode = REQUEST_CODE_RESCUE_DETAIL)
                true
            }
            else -> false
        }
    }




}

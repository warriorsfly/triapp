package com.wxsoft.triapp.ui.patient

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.lazyFast
import com.warriorsfly.core.utils.viewModelProvider
import com.wxsoft.fcare.ui.common.DingLikeTimePicker
import com.wxsoft.triapp.App
import com.wxsoft.triapp.R
import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.databinding.ActivityRescueEditBinding
import com.wxsoft.triapp.ui.common.*
import com.wxsoft.triapp.ui.widgets.FragmentContainerActivity
import com.wxsoft.triapp.utils.DateTimeUtils
import javax.inject.Inject

class PatientRescueFragment : BaseFragment(), ITimeSelected {


    private var selectedId = 0

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: ActivityRescueEditBinding

    private lateinit var viewModel: PatientRescueEditModel

    private val rescueId by lazyFast {
        arguments?.getInt("rescueId") ?: 0
    }

    private val recordId by lazyFast {
        arguments?.getInt("recordId") ?: 0
    }

    private val patientId by lazyFast {
        arguments?.getInt("patientId") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = viewModelProvider(factory)

        viewModel.patientId = patientId
        viewModel.recordId = recordId
        viewModel.rescueId = rescueId

        binding = ActivityRescueEditBinding.inflate(inflater, container, false).apply {
            viewModel = this@PatientRescueFragment.viewModel
            lifecycleOwner = this@PatientRescueFragment
        }


        viewModel.click.observe(viewLifecycleOwner, Observer {
            when (it) {
                "1" -> showDatePicker(binding.timing)
            }
        })

        viewModel.clickDictionary.observe(viewLifecycleOwner, Observer {
            when (it) {
                -1 -> showDictionaryPicker(binding.leftEye2, -1)
                -2 -> showDictionaryPicker(binding.rightEye2, -1)
                -3 -> showDictionaryPicker(binding.beding, -2)
                -4 -> showDictionaryPicker(binding.start, -3)
                -5 -> showDictionaryPicker(binding.rowe33, -4)
                -6 -> showDictionaryPicker(binding.rowe34, -4)
                -7 -> showDictionaryPicker(binding.rowe314, "Apostasis.Handle")
                -8 -> showMultipleDictionaryPicker(binding.rowe3131, 31)
            }
        })

        setupToolbar()

        viewModel.responseo.observe(viewLifecycleOwner, Observer {
            if(it.Type==200 || it.Type==203){
                Toast.makeText(activity,"保存成功",Toast.LENGTH_SHORT).show()
                activity?.apply{
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }else{

                Toast.makeText(activity,it.Data,Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root

    }

    private var timePicker: DingLikeTimePicker? = null
    private var dictionaryPicker: DictionarySinglePicker? = null
    private var dictionaryMultiplePicker: DictionaryMutiplePicker? = null

    private fun createDatePickerDialog(time: Long): DingLikeTimePicker {

        return DingLikeTimePicker(time, ::selectTime, ::clearTime)
    }

    private fun createDictionaryPickerDialog(): DictionarySinglePicker {

        return DictionarySinglePicker(::doWithDictionary)
    }


    private fun createMultipleDictionaryPickerDialog(): DictionaryMutiplePicker {

        return DictionaryMutiplePicker(::doWithDictionary)
    }


    private fun doWithDictionary(dic: Dictionary) {

        dictionaryPicker?.apply {
            dismiss()
            onDestroy()
        }
        dictionaryPicker = null
        when (selectedId) {
            R.id.start -> viewModel.rescueRecord.value?.conscious = dic.Id
            R.id.beding -> viewModel.rescueRecord.value?.decubitus = dic.Name
            R.id.rowe3_3 -> {
                viewModel.rescueRecord.value?.pupilDiameterLeft = dic.Name
                viewModel.rescueRecord.value?.pupilDiameterRight = dic.Name
            }
            R.id.rowe3_4 -> viewModel.rescueRecord.value?.pupilDiameterRight = dic.Name
            R.id.rowe3_14 -> viewModel.rescueRecord.value?.curemeasure2 = dic.Name
            R.id.rowe3_13_1 -> viewModel.rescueRecord.value?.curemeasure = dic.Name
            else -> (binding.root.findViewById<Button>(selectedId))?.text = dic.Name
        }

        selectedId = 0
    }

    private fun showDatePicker(v: View?) {
        (v as? Button)?.let {
            selectedId = it.id
            val currentTime = it.text.toString().let { txt ->
                if (txt.isEmpty()) 0L else DateTimeUtils.formatter.parse(txt.replace("T", " ")).time
            }

            timePicker = createDatePickerDialog(currentTime)
            timePicker?.show(childFragmentManager, "all")
        }
    }

    private fun showDictionaryPicker(v: View?, parentId: Int) {
        v?.let {
            selectedId = it.id
            dictionaryPicker = createDictionaryPickerDialog().apply {
                val args = FragmentArgs().add("parentId", parentId)
                val bundle = Bundle()
                FragmentArgs.toBundle(bundle, args)
                arguments = bundle
            }
            dictionaryPicker?.show(childFragmentManager, "all")
        }
    }

    private fun showMultipleDictionaryPicker(v: View?, parentId: Int) {
        v?.let {
            selectedId = it.id
            dictionaryMultiplePicker = createMultipleDictionaryPickerDialog().apply {
                val args = FragmentArgs().add("parentId", parentId)
                val bundle = Bundle()
                FragmentArgs.toBundle(bundle, args)
                arguments = bundle
            }
            dictionaryMultiplePicker?.show(childFragmentManager, "all")
        }
    }


    private fun showDictionaryPicker(v: View?, parentId: String) {
        v?.let {
            selectedId = it.id
            val p = App.dictionaries.find { it.Key == parentId }?.Id ?: 0
            dictionaryPicker = createDictionaryPickerDialog().apply {
                val args = FragmentArgs().add("parentId", p)
                val bundle = Bundle()
                FragmentArgs.toBundle(bundle, args)
                arguments = bundle
            }
            dictionaryPicker?.show(childFragmentManager, "all")
        }
    }

    override fun selectTime(mills: Long) {
        timePicker?.onDestroy()
        timePicker = null
        when (selectedId) {
            R.id.timing -> viewModel.rescueRecord.value?.updatedAt =
                DateTimeUtils.formatter.format(mills)
            else -> (binding.root.findViewById<Button>(selectedId))?.text =
                DateTimeUtils.formatter.format(mills)
        }

        selectedId = 0

    }

    override fun clearTime(mills: Long) {
        dictionaryPicker?.onDestroy()
        dictionaryPicker = null
        timePicker?.onDestroy()
        timePicker = null

        (binding.root.findViewById<Button>(selectedId))?.text = ""

        selectedId = 0
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        val activity = activity as FragmentContainerActivity?
        activity?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "抢救记录"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_saving, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit -> {
                viewModel.submit()
                true
            }
            else -> false
        }
    }


}

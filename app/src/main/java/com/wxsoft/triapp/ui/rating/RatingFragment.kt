package com.wxsoft.triapp.ui.rating

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.warriorsfly.core.utils.lazyFast
import com.wxsoft.triapp.R
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.RatingRecord
import com.wxsoft.triapp.databinding.ActivityRatingBinding
import com.wxsoft.triapp.ui.common.BaseActivity
import com.wxsoft.triapp.ui.common.BaseFragment
import javax.inject.Inject

class RatingFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var adapter: RatingAdapter
    private lateinit var viewModel: RatingViewModel
    private lateinit var binding: ActivityRatingBinding

    private val ratingFragment by lazy {
        RatingsSheetFragment(::newRating)
    }
    private val patientId: Int by lazyFast {
        arguments?.getInt(PATIENT_ID, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activityViewModelProvider(factory)
        viewModel.patientId = patientId
        adapter = RatingAdapter(this, ::showDetail)

        binding = ActivityRatingBinding.inflate(inflater, container, false).apply {

            list.adapter=this@RatingFragment.adapter
            list.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            viewModel=this@RatingFragment.viewModel

            lifecycleOwner = this@RatingFragment
        }

        viewModel.patientId=patientId

        viewModel.results.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        setupToolbar()
        return binding.root
    }

    private fun showDetail(result: RatingRecord) {

        val intent = Intent(activity, RatingSubjectActivity::class.java).apply {
            putExtra(RatingSubjectActivity.PATIENT_ID, patientId)
            putExtra(RatingSubjectActivity.RATING_NAME, result.ratingName)
            putExtra(RatingSubjectActivity.RECORD_ID, result.Id)
            putExtra(RatingSubjectActivity.RATING_ID, result.ratingId)
        }
        startActivityForResult(intent, ARG_UPDATE_ITEM_CODE)
    }

    private fun newRating(rating: Rating) {
        ratingFragment.dismiss()
        val intent = Intent(activity, RatingSubjectActivity::class.java).apply {
            putExtra(RatingSubjectActivity.PATIENT_ID, patientId)
            putExtra(RatingSubjectActivity.RATING_NAME, rating.name)
            putExtra(RatingSubjectActivity.RATING_ID, rating.Id)
        }
        startActivityForResult(intent, ARG_CREATE_ITEM_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==RESULT_OK) {
            when (requestCode) {
                ARG_CREATE_ITEM_CODE, ARG_UPDATE_ITEM_CODE -> {

                    viewModel.refresh()
                }
            }
        }
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        val activity = activity as BaseActivity?
        activity?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_adding, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                ratingFragment.show(childFragmentManager, RatingsSheetFragment.TAG)
                true
            }
            else -> false
        }
    }
}

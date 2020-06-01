package com.wxsoft.triapp.ui.rating

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.result.EventObserver
import com.warriorsfly.core.utils.lazyFast
import com.warriorsfly.core.utils.viewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.databinding.ActivityRatingSubjectBinding
import com.wxsoft.triapp.ui.common.BaseActivity
import javax.inject.Inject

class RatingSubjectActivity : BaseActivity() {

    companion object {
        const val PATIENT_ID = "PATIENT_ID"
        const val RATING_ID = "RATING_ID"
        const val RATING_NAME = "RATING_NAME"
        const val RECORD_ID = "RECORD_ID"
    }

//    @Inject
//    @field:Named("ratingOptionViewPool")
//    lateinit var optionViewPool: RecyclerView.RecycledViewPool


    @Inject
    lateinit var factory: ViewModelFactory
    private val toast: Toast by lazy {
        Toast.makeText(this, "", Toast.LENGTH_SHORT)
    }
    private lateinit var adapter: SubjectAdapter
    private lateinit var viewModel: RatingSubjectViewModel

    private val patientId: Int by lazyFast {
        intent?.getIntExtra(PATIENT_ID, 0) ?: 0
    }

    private val ratingId: Int by lazyFast {
        intent?.getIntExtra(RATING_ID, 0) ?: 0
    }

    private val ratingName: String by lazyFast {
        intent?.getStringExtra(RATING_NAME) ?: ""
    }

    private val recordId: Int by lazyFast {
        intent?.getIntExtra(RECORD_ID, 0) ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(factory)
        val binding = DataBindingUtil.setContentView<ActivityRatingSubjectBinding>(
            this,
            R.layout.activity_rating_subject
        ).apply {

            viewModel = this@RatingSubjectActivity.viewModel
            lifecycleOwner = this@RatingSubjectActivity
        }

        viewModel.record.observe(this, Observer { })
        viewModel.rating.observe(this, Observer {
            it ?: return@Observer

            adapter = SubjectAdapter(this, it)

            binding.list.adapter = adapter
            adapter.submitList(it.subjects)
            title = it.name
        })

        viewModel.mesAction.observe(this, EventObserver {
            toast.setText(it)
            toast.show()
        })

        viewModel.savingResult.observe(this, Observer {
            if (it) {
                setResult(Activity.RESULT_OK, intent)
                intent.putExtra("SCORE", viewModel.rating.value?.score ?: 0)
                intent.putExtra("RATINGID", ratingId)
                intent.putExtra("ID", viewModel.therecordId)
                finish()
            }
        })
        viewModel.patientId = patientId
        if (recordId == 0) {
            viewModel.ratingId = ratingId
        } else {
            viewModel.recordId = recordId
        }
        supportActionBar?.apply {
            setHomeButtonEnabled(true)//主键按钮能否可点击
            setDisplayHomeAsUpEnabled(true)//显示返回图标
        }


//        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_saving, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return  when(item?.itemId){
            R.id.submit->{
                viewModel.saveRecord()
                true
            }
            android.R.id.home->{
                onBackPressed()
                return true
            }
//            R.id.delete->{
//                if(recordId.isNotEmpty()) {
//                    val dialog = AlertDialog.Builder(this,R.style.Theme_FCare_Dialog)
//                    dialog.setTitle("是否删除当前评分?")
//                        .setPositiveButton("确定") { _, _ -> viewModel.deleteRecord() }
//                        .setNegativeButton("取消") { _, _ -> }
//                        .create().show()
//                }
//                true
//            }
            else->super.onOptionsItemSelected(item)
        }

    }
}

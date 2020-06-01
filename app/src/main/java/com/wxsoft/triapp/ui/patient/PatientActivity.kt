package com.wxsoft.triapp.ui.patient

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.warriorsfly.core.utils.lazyFast
import com.wxsoft.triapp.R
import com.wxsoft.triapp.ui.common.BaseActivity
import com.wxsoft.triapp.ui.common.BaseFragment.Companion.PATIENT_ID
import com.wxsoft.triapp.ui.rating.RatingFragment

class PatientActivity : BaseActivity() {


    private val patientId by lazyFast {
        intent?.getIntExtra("patientId",0)?:0
    }

    private val blh by lazyFast {
        intent?.getStringExtra("blh")
    }
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)//主键按钮能否可点击
            setDisplayHomeAsUpEnabled(true)//显示返回图标
        }

        setContentView(R.layout.activity_patient)
        val sectionsPagerAdapter =
            PageAdapter(
                patientId,
                blh?:"",
                supportFragmentManager
            )
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                onBackPressed()
                return true
            }
            else-> super.onOptionsItemSelected(item)
        }
        
    }

    class PageAdapter(private val patientId: Int,private val blh:String,fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when(position){
                0->PatientDetailFragment(patientId)
                1->PatientRescueListFragment(patientId)
                2->LisFragment(blh,1)
                3->LisFragment(blh,0)
                4->RatingFragment().apply {
                    arguments=Bundle().apply {
                        putInt(PATIENT_ID,patientId)
                    }
                }
                else->throw IllegalStateException("Unknown position $position")
            }

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0->"基本信息"
                1->"抢救记录单"
                2->"检查"
                3->"检验"
                4->"评分"
                else->throw IllegalStateException("Unknown position $position")
            }
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 5
        }
    }
}
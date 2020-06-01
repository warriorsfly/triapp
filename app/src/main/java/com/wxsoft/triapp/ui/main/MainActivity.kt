package com.wxsoft.triapp.ui.main

import android.content.Context
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wxsoft.triapp.R
import com.wxsoft.triapp.ui.common.BaseActivity
import com.wxsoft.triapp.ui.main.fragment.PatientListFragment
import com.wxsoft.triapp.ui.main.fragment.StatisticFragment
import com.wxsoft.triapp.ui.main.fragment.UserProfileFragment

class MainActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var navigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter =
            MainPageAdapter(
                this,
                supportFragmentManager
            )
        viewPager = findViewById(R.id.view_pager)
        navigation = findViewById(R.id.navigation)
        viewPager.adapter = sectionsPagerAdapter
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                viewPager.setCurrentItem(0, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_dashboard -> {
                viewPager.setCurrentItem(1, true)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_notifications -> {
////                message.setText(R.string.title_notifications)
//                viewPager.setCurrentItem(2, true)
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.nav_user -> {
                viewPager.setCurrentItem(2, true)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    class MainPageAdapter(private val context: Context, fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when(position) {
                0->StatisticFragment()
                1->PatientListFragment()
                else->UserProfileFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return ""
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 3
        }
    }
}
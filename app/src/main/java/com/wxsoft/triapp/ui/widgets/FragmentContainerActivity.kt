package com.wxsoft.triapp.ui.widgets

import android.os.Bundle
import android.view.MenuItem
import com.wxsoft.triapp.R
import com.wxsoft.triapp.ui.common.BaseActivity
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentArgs

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FragmentContainerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base_contain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val className = intent.getStringExtra("className")

        val args = intent.getSerializableExtra("args") as FragmentArgs

        try {
            val clazz = Class.forName(className)
            val fragment = clazz.newInstance() as BaseFragment
            // 设置参数给Fragment
            if (fragment != null) {
                try {
                    val method = clazz.getMethod(
                        "setArguments", Bundle::class.java
                    )
                    method.invoke(fragment, FragmentArgs.transToBundle(args))

                    supportFragmentManager.beginTransaction().add(R.id.container,fragment).commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                    finish()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            finish()
        }
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
}

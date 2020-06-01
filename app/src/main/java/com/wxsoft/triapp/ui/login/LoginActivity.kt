package com.wxsoft.triapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.result.EventObserver
import com.warriorsfly.core.utils.viewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.ui.common.BaseActivity
import com.wxsoft.triapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        viewModel = viewModelProvider(factory)

        viewModel.tokenData.observe(this, Observer {
//            networkModule.provideOkhttp(TokenInterceptor().apply {
//                token=it
//            })

            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        })

        viewModel.mesAction.observe(this,EventObserver{
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        login.setOnClickListener {
            var name = username.text.toString();
            var pass = password.text.toString()
            if (name.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.login(name,pass)
            }
        }
    }

}
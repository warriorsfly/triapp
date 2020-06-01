package com.wxsoft.triapp.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.databinding.FragmentUserProfileBinding
import com.wxsoft.triapp.ui.BaseViewModel
import com.wxsoft.triapp.ui.login.LoginActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class UserProfileFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activityViewModelProvider(factory)
        val binding = FragmentUserProfileBinding.inflate(inflater, container, false).apply {

            user=BaseViewModel.account
            logout.setOnClickListener {
                val intent = Intent(activity!!, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
                activity?.finish()
            }
            lifecycleOwner = viewLifecycleOwner
        }



        return binding.root
    }

}

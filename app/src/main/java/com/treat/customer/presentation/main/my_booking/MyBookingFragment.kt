package com.treat.customer.presentation.main.my_booking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.treat.customer.MainActivity
import com.treat.customer.R
import com.treat.customer.data.model.MyBookingData
import com.treat.customer.databinding.FragmentMyBookingBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.main.ui.home.HomeTabAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyBookingFragment : Fragment() {
    private var tabTitles: ArrayList<String> = arrayListOf()

    companion object {
        fun newInstance() = MyBookingFragment()
    }

    private val tabAdapter by lazy {
        MyBookingAdapter(this)
    }
    private val viewModel: MyBookingViewModel by viewModel<MyBookingViewModel>()
    private val authViewModel: LoginViewModel by viewModel<LoginViewModel>()

    private var _binding: FragmentMyBookingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    private fun initViews() {
        if (authViewModel.getUserData()?.data?.token == null) {
            binding.lnMyBooking.visibility = View.GONE
            binding.frMyBooking.visibility = View.VISIBLE
        } else {
            binding.lnMyBooking.visibility = View.VISIBLE
            binding.frMyBooking.visibility = View.GONE
        }
        binding.unAuthLayout.btnLogin.setOnClickListener {
            startActivity(
                Intent(requireContext(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.putExtra("Fragment", R.string.my_booking)
            )
        }
        setupTabs()
        setupAppBar()

    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.my_booking)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
    }


    private fun setupTabs() {
        tabTitles = arrayListOf<String>(
            getString(R.string.past),
            getString(R.string.pending),
            getString(R.string.upcoming)
        )
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            binding.viewPager.currentItem = tab.position
        }.attach()
    }
}


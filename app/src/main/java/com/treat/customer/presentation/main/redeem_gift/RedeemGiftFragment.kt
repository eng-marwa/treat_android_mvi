package com.treat.customer.presentation.main.redeem_gift

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treat.customer.R

class RedeemGiftFragment : Fragment() {

    companion object {
        fun newInstance() = RedeemGiftFragment()
    }

    private lateinit var viewModel: RedeemGiftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_redeem_gift, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RedeemGiftViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
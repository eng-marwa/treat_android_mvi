package com.treat.customer.presentation.main.ui.more.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.databinding.FragmentTermsAndConditionsBinding
import com.treat.customer.utils.extensions.showToast

class TermsAndConditionsFragment : Fragment() {

    companion object {
        fun newInstance() = TermsAndConditionsFragment()
    }

    private var terms: String = ""
    private var _binding: FragmentTermsAndConditionsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsAndConditionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         arguments?.let {
            terms = it.getString("terms","")
        }
        initViews()
    }

    private fun initViews() {
        setupAppBar()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_terms_condition_to_navigation_notification)
        }
        binding.lbTerms.text = terms
    }
    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.terms)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}
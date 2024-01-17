package com.treat.customer.presentation.main.ui.more.languages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.R
import com.treat.customer.databinding.FragmentChangeLanguageBinding
import com.treat.customer.domain.entities.Language
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeLanguageFragment : Fragment(), LanguageAdapter.OnItemClick {

    companion object {
        fun newInstance() = ChangeLanguageFragment()
    }

    private val adapter by lazy {
        LanguageAdapter(this)
    }
    private var _binding: FragmentChangeLanguageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangeLanguageViewModel by viewModel<ChangeLanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        setupAppBar()
        initList()
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.change_language)
        binding.titleToolBar.ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initList() {
        binding.rvLanguage.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLanguage.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemSelected(item: Language) {
        showSnack(
            binding.root,
            getString(item.name),
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }
}
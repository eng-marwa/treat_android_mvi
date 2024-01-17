package com.treat.customer.presentation.main.ui.more.contact_us

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.databinding.FragmentContactUsBinding
import com.treat.customer.databinding.FragmentMoreBinding
import com.treat.customer.databinding.FragmentMyWalletBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.main.ui.more.MoreViewModel
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactUsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactUsFragment()
    }
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    private var whats: String = ""
    private var email: String = ""
    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            whats = it.getString("whats", "")
            email = it.getString("email", "")
        }
        initViews()
    }

    private fun initViews() {
        setupAppBar()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_contact_us_to_navigation_notification)
        }
        binding.btnEmail.setOnClickListener {
            sendEmail()
        }
        binding.btnWhatsApp.setOnClickListener {
            sendWhatsApp()
        }
    }

    private fun sendWhatsApp() {
//        val phoneNumber = loginViewModel.getUserData()?.data?.phone
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.putExtra("jid", "$whats@s.whatsapp.net")
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello, this is my message.")
        sendIntent.setPackage("com.whatsapp")
        if (context?.let { sendIntent.resolveActivity(it.packageManager) } != null) {
            startActivity(sendIntent)
        } else {
            showSnack(
                binding.root,
                getString(R.string.something_wrong),
                null,
                isError = true,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )

        }
    }

    private fun sendEmail() {
        val userEmail = loginViewModel.getUserData()?.data?.email
        val mailtoUri = Uri.parse("mailto:$email")
        val emailIntent = Intent(Intent.ACTION_SENDTO, mailtoUri).apply {
           putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
           putExtra(Intent.EXTRA_CC, arrayOf(userEmail))
        }
        if (context?.let { emailIntent.resolveActivity(it.packageManager) } != null) {
            startActivity(emailIntent)
        } else {
        }
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.contact_us)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.treat.customer.presentation.main.ui.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.SettingsData
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.databinding.FragmentMoreBinding
import com.treat.customer.domain.entities.Menu
import com.treat.customer.presentation.ITemActivity
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.auth.profile.GenderAdapter
import com.treat.customer.presentation.main.HomeActivity
import com.treat.customer.utils.extensions.showDialog
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreFragment : Fragment(), MenuAdapter.OnItemClick {

    private var _binding: FragmentMoreBinding? = null
    private val viewModel: MoreViewModel by viewModel<MoreViewModel>()
    private var settingsData: SettingsData? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val menuAdapter by lazy {
        MenuAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.logoutStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onLogoutSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onLogoutFailed(it.throwable)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.disableAccountStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onDisableAccountSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onDisableAccountFailed(it.throwable)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.appSettingsStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onAppSettingsSuccess(it.data!!)
                    is BaseViewModel.ViewState.Failed -> onAppSettingsFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onAppSettingsFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onAppSettingsSuccess(data: SettingsResponse) {
        settingsData = data.data

    }

    private fun onDisableAccountFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onDisableAccountSuccess(data: TreatResponse?) {
        showSnack(
            binding.root,
            data?.message,
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        activity?.finish()
    }

    private fun onLogoutFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onLogoutSuccess(data: TreatResponse?) {
        showSnack(
            binding.root,
            data?.message,
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        activity?.finish()
    }

    private fun initViews() {
        setupAppBar()
        viewModel.getAppSettings()
        binding.toolBar.frNotification.setOnClickListener {
            startActivity(
                Intent(requireContext(), ITemActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.putExtra("ITEM", R.string.title_notifications)
            )
        }
        initList()

    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.my_account)
        binding.titleToolBar.ivBack.visibility = View.INVISIBLE
    }

    private fun initList() {
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = menuAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemSelected(item: Menu) {
        when (item.name) {
            R.string.logout -> {
                activity?.let {
                    showDialog(
                        it,
                        getString(R.string.logout_message),
                        getString(R.string.logout),
                        onOkClick = { viewModel.logout() },
                        onCancelClick = {}
                    )
                }

            }

            R.string.disable_account -> viewModel.disableAccount()
            R.string.share -> context?.let {
                openSharePopup(getString(R.string.share), it)
            }

            else -> {
                startActivity(
                    Intent(requireContext(), ITemActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }.putExtra("ITEM", item.name).putExtra("SettingsData", settingsData)
                )
            }
        }
    }

    private fun openSharePopup(message: String, context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.type = "text/plain"

        val chooserIntent = Intent.createChooser(sendIntent, "Share via")

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooserIntent)
        } else {
            // Handle the case where no suitable app is installed
            Toast.makeText(context, "No app found to handle the share action", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
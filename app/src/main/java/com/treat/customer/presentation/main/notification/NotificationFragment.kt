package com.treat.customer.presentation.main.notification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.MainActivity
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.Notifications
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.databinding.FragmentNotificationBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.utils.extensions.showSnack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment(), NotificationAdapter.OnItemClick {

    companion object {
        fun newInstance() = NotificationFragment()
    }
    private val authViewModel: LoginViewModel by viewModel<LoginViewModel>()

    private val notificationAdapter by lazy {
        NotificationAdapter()
    }
    private val viewModel: NotificationViewModel by viewModel<NotificationViewModel>()
    private var _binding: FragmentNotificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.title_notifications)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            activity?.finish()
        }

    }

    private fun observeViewModel() {
        binding.loader.show()
        lifecycleScope.launch {
            delay(100)
            viewModel.notifications.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onNotificationsSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onNotificationsFailed(it.throwable)
                    else -> {
                        binding.loader.show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            delay(100)
            viewModel.allNotificationsRead.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onReadAllNotificationsSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onReadAllNotificationsFailed(it.throwable)
                    else -> {
                        binding.loader.show()
                    }
                }
            }
        }
        lifecycleScope.launch {
            delay(100)
            viewModel.oneNotificationRead.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onReadOneNotificationSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onReadOneNotificationsFailed(it.throwable)
                    else -> {
                        binding.loader.show()
                    }
                }
            }
        }
    }

    private fun onReadAllNotificationsSuccess(data: TreatResponse?) {
        binding.loader.hide()
        showSnack(
            binding.root,
            data?.message,
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onReadAllNotificationsFailed(throwable: BaseException?) {
        binding.loader.hide()
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

    private fun onReadOneNotificationSuccess(data: TreatResponse?) {
        binding.loader.hide()
        showSnack(
            binding.root,
            data?.message,
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onReadOneNotificationsFailed(throwable: BaseException?) {
        binding.loader.hide()
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

    private fun onNotificationsFailed(throwable: BaseException?) {
        binding.loader.hide()
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


    private fun onNotificationsSuccess(data: NotificationResponse?) {
        binding.loader.hide()
        data?.let { data ->
            data.data?.let {
                notificationAdapter.setData(it.notifications)
                if (it.count == 0) {
                    binding.toolBar.lbNotificationCount.visibility = View.GONE

                } else {
                    binding.toolBar.lbNotificationCount.visibility = View.VISIBLE
                    binding.toolBar.lbNotificationCount.text = "${it.count}"
                }
            }
        }
    }
    private fun initViews() {
        if (authViewModel.getUserData()?.data?.token == null) {
            binding.container.visibility = View.GONE
            binding.frNotification.visibility = View.VISIBLE
        } else {
            binding.container.visibility = View.VISIBLE
            binding.frNotification.visibility = View.GONE
            viewModel.getNotifications()
        }
        binding.lbMarkAsRead.setOnClickListener {
            viewModel.markAllNotificationsRead()
        }
        binding.unAuthLayout.btnLogin.setOnClickListener {
            startActivity(
                Intent(requireContext(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.putExtra("Fragment", R.string.title_notifications)
            )
        }
        initList()
        setupAppBar()
    }

    private fun initList() {
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotifications.adapter = notificationAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemSelected(item: Notifications) {
        viewModel.makeOneNotificationRead(notificationId = item.id!!.toString())
    }
}
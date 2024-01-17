package com.treat.customer.presentation.auth.profile

import LocationHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.databinding.FragmentProfileBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.location.LocationViewModel
import com.treat.customer.utils.extensions.hideKeyboard
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(), GenderBottomSheet.GenderSelect,
    LocationHelper.LocationResultListener {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var genderId: String? = null
    private val genderList: MutableList<GenderData> = mutableListOf()

    private val genderBottomSheet by lazy {
        GenderBottomSheet(this)
    }
    private val viewModel: ProfileViewModel by viewModel<ProfileViewModel>()
    private val locationViewModel: LocationViewModel by viewModel<LocationViewModel>()
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.gender.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onGenderTypeSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onGenderTypeFailed(it.throwable)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            locationViewModel.location.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onUpdateLocationSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onUpdateLocationFailed(it.throwable)
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.profileStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onUpdateProfileSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onUpdateProfileFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onUpdateProfileFailed(throwable: BaseException?) {
        if (throwable?.getMessage() == "INVALID_NAME_ERROR") {
            showSnack(
                binding.root,
                getString(R.string.name_error),
                null,
                isError = true,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )
        } else if (throwable?.getMessage() == "INVALID_GENDER_ERROR") {
            showSnack(
                binding.root,
                getString(R.string.select_gender_error),
                null,
                isError = true,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )
        } else if (throwable?.getMessage() == "INVALID_EMAIL_ERROR") {
            showSnack(
                binding.root,
                getString(R.string.email_validation_error),
                null,
                isError = true,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )
        } else {
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
    }

    private fun onUpdateProfileSuccess(data: ProfileResponse?) {
        loginViewModel.saveUserData(data)
        findNavController().navigate(R.id.action_profileFragment_to_locationFragment)

    }

    private fun onUpdateLocationFailed(throwable: BaseException?) {
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

    private fun onUpdateLocationSuccess(data: ProfileResponse?) {
        loginViewModel.saveUserData(data)
    }

    private fun onGenderTypeFailed(throwable: BaseException?) {
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

    private fun onGenderTypeSuccess(data: GenderResponse?) {
        data?.let {
            genderList.addAll(it.data)
        }
    }

    private fun initViews() {
        viewModel.getGender()
        binding.btnMenu.setOnClickListener {
            if (genderList.isNotEmpty())
                if (genderBottomSheet.isAdded.not()) {
                    genderBottomSheet.setGenderList(genderList)
                    genderBottomSheet.show(childFragmentManager, "gender")
                }
        }
        binding.etGender.setOnClickListener {
            binding.btnMenu.callOnClick()
        }
        binding.btnConfirm.setOnClickListener {
            viewModel.updateUserProfileFromAuth(
                binding.etName.text.toString(), genderId ?: ""
            )
            context?.let {
                val locationHelper = LocationHelper(it);
                // Check if GPS is enabled
                if (locationHelper.isGPSEnabled) {
                    locationHelper.currentLocation()
                } else {
                    // If GPS is not enabled, prompt the user to open location settings
                    locationHelper.openLocationSettings();
                }
            }

        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        hideKeyboard()
    }

    override fun onGenderSelect(gender: GenderData) {
        viewModel.saveGender(gender)
        binding.etGender.text = gender.name
        genderId = gender.id
    }

    override fun onLocationResult(latitude: Double, longitude: Double) {
        Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
        locationViewModel.updateLocation(latitude, longitude)

    }
}
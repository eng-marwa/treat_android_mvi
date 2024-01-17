package com.treat.customer.presentation.main.ui.more.edit_profile

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.databinding.FragmentEditProfileBinding
import com.treat.customer.databinding.FragmentLoginBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.auth.profile.GenderBottomSheet
import com.treat.customer.presentation.auth.profile.ProfileViewModel
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class EditProfileFragment : Fragment(), GenderBottomSheet.GenderSelect {

    companion object {
        fun newInstance() = EditProfileFragment()
    }
    private var genderId: String?=null

    private val viewModel: ProfileViewModel by viewModel<ProfileViewModel>()
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val genderList: MutableList<GenderData> = mutableListOf()

    private val genderBottomSheet by lazy {
        GenderBottomSheet(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        viewModel.getGender()
        viewModel.getProfile()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_edit_profile_to_navigation_notification)
        }
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
            viewModel.updateUserProfileFromMore(
                binding.etName.text.toString(),
                genderId?:"",
                binding.etEmail.text.toString(),
                binding.etBirthDate.text.toString(),
                )
        }

        binding.ivBirthDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        context?.let {
            val datePickerDialog = DatePickerDialog(
                it,
                { view, year, monthOfYear, dayOfMonth ->
                    // Handle the selected date here
                    val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                    if (validateAge(year, monthOfYear, dayOfMonth)) {
                        // Do something with the selected date as it is above 18
                        binding.etBirthDate.text = selectedDate
                    } else {
                        // Show an error message as the age is below 18
                        showSnack(
                            binding.root,
                            getString(R.string.enter_validate_birth_date),
                            null,
                            isError = true,
                            showButton = false,
                            buttonTitle = null,
                            onClick = null
                        )
                    }
                },
                currentYear,
                currentMonth,
                currentDay
            )

            datePickerDialog.show()
        }
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
            viewModel.fetchProfileStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onFetchProfileSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onFetchProfileFailed(it.throwable)
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

    private fun onFetchProfileFailed(throwable: BaseException?) {
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

    private fun onFetchProfileSuccess(data: ProfileResponse?) {
        data?.let {
            binding.etName.setText(it.data.name)
            binding.etEmail.setText(it.data.email)
            binding.etPhone.setText(it.data.phone)
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
        }else if (throwable?.getMessage() == "INVALID_EMAIL_ERROR") {
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
            )        }
    }

    private fun onUpdateProfileSuccess(data: ProfileResponse?) {
        loginViewModel.saveUserData(data)
        showSnack(
            binding.root,
            getString(R.string.user_profile_update_successfully),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
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

    override fun onGenderSelect(gender: GenderData) {
        binding.etGender.text = gender.name
        genderId = gender.id
    }

    private fun validateAge(year: Int, month: Int, day: Int): Boolean {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year, month, day)
        val today = Calendar.getInstance()
        // Calculate age
        var age = today.get(Calendar.YEAR) - selectedCalendar.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < selectedCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        // Check if age is above 18
        return age >= 18
    }

}
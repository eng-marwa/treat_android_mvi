package com.treat.customer.presentation.splash

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.databinding.FragmentFavoritesBinding
import com.treat.customer.databinding.FragmentSplashBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.main.HomeActivity
import com.treat.customer.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModel<SplashViewModel>()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        animateLogo()
        //setLanguage()
    }

    private fun animateLogo() {
        binding.ivLogo.translationY = 0f
        binding.ivLogo.animate()
            .translationY((resources.displayMetrics.heightPixels /2.2f))
            .setInterpolator(LinearInterpolator())
            .setStartDelay(1000)
            .start()
    }

//    private fun setLanguage() {
//        if (UserPreferences.getString(this, PreferenceKeys.LANG)?.isEmpty()!!) {
//            val systemLang =
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    Resources.getSystem().configuration.locales[0].language
//                } else {
//                    Resources.getSystem().configuration.locale.language
//                }
//            val lang = when (systemLang) {
//                "ar", "en", "tr" -> systemLang
//                else -> "en"
//            }
//            UserPreferences.saveString(this, PreferenceKeys.LANG, lang)
//            UserRepository.lang = lang
//            setLanguage(lang)
//        }
//        else {
//            setLanguage(UserPreferences.getString(this, PreferenceKeys.LANG).toString())
//            UserRepository.lang = UserPreferences.getString(this, PreferenceKeys.LANG).toString()
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViews() {
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                viewModel.isLoggedIn() -> {
                    startActivity(
                        Intent(requireContext(), HomeActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                    )
                    activity?.finish()
                }

                else -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

        }, 3000)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SplashFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
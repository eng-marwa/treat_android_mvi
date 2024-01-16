package com.treat.customer.presentation.main.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.Branches
import com.treat.customer.data.model.BranchesData
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.databinding.FragmentFavoritesBinding
import com.treat.customer.presentation.ITemActivity
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), FavoritesAdapter.OnItemClick  {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val branchAdapter by lazy {
        FavoritesAdapter(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.loader.show()
            lifecycleScope.launch {
                delay(100)
                viewModel.favoriteBranch.collect {
                    when (it) {
                        is BaseViewModel.ViewState.Loaded -> onFavoriteBranchSuccess(it.data)
                        is BaseViewModel.ViewState.Failed -> onFavoriteBranchFailed(it.throwable)
                        else -> {
                            binding.loader.show()
                        }
                    }
                }
        }
    }

    private fun onFavoriteBranchFailed(throwable: BaseException?) {
        binding.loader.hide()
        showToast(throwable?.getMessage())
    }

    private fun onFavoriteBranchSuccess(data: BranchesResponse?) {
        binding.loader.hide()
       data?.let {
           if(it.data!=null)
           branchAdapter.setBranches(it.data!!.branches)
       }
    }

    private fun initViews() {
        viewModel.getFavoriteBranches()
        binding.toolBar.frNotification.setOnClickListener {
            startActivity(
                Intent(requireContext(), ITemActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.putExtra("ITEM", R.string.title_notifications)
            )        }
        setupAppBar()
        initList()
    }
    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.title_favorites)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initList() {
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = branchAdapter
    }
    override fun itemSelected(item: Branches) {
        startActivity(
            Intent(requireContext(), ITemActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }.putExtra("ITEM", R.string.branch_details).putExtra("BRANCH", item.id)
        )
    }

    override fun addItemToFavorite(item: Branches) {
        showSnack(
            binding.root,
            "${item.name}${getString(R.string.is_added)}",
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        favoritesViewModel.addToFavorites(item.id!!)
    }

    override fun removeItemFromFavorite(item: Branches) {
        showSnack(
            binding.root,
            "${item.name}${getString(R.string.is_removed)}",
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        favoritesViewModel.addToFavorites(item.id!!)


    }
}
package com.treat.customer.presentation.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.Branches
import com.treat.customer.data.model.BranchesData
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.databinding.FragmentHomeTabBinding
import com.treat.customer.presentation.ITemActivity
import com.treat.customer.presentation.main.ui.branches.BranchAdapter
import com.treat.customer.presentation.main.ui.favorites.FavoritesViewModel
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeTabFragment : Fragment(), BranchAdapter.OnItemClick , HomeTabAdapter.DataChangeListener{
    private var _binding: FragmentHomeTabBinding? = null
    private var categoryId: String? = null
    private var serviceTypeId: String? = null
    private var genderId: String? = null

    private val branchAdapter by lazy {
        BranchAdapter(this)
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            categoryId = it.getString("categoryId", "1")
            serviceTypeId = it.getString("serviceTypeId", "1")
            genderId = it.getString("genderId", "1")
        }

        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.branches.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onViewBranchesSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onViewBranchesFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onViewBranchesFailed(throwable: BaseException?) {
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

    private fun onViewBranchesSuccess(data: BranchesResponse?) {
        data?.let {
            if(it.data!=null) {
                branchAdapter.setBranches(it.data!!.branches)
            }
        }

    }

    private fun initViews() {
//        viewModel.getBranches(
//            categoryId, serviceTypeId,
//            genderId,
//            lat = null,
//            lng = null
//        )
        initList()

    }

    private fun initList() {
        binding.rvBranches.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvBranches.adapter = branchAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryId: String?, serviceTypeId: String?, genderId: String?) =
            HomeTabFragment().apply {
                arguments = Bundle().apply {
                    putString("categoryId", categoryId)
                    putString("serviceTypeId", serviceTypeId)
                    putString("genderId", genderId)
                }
            }
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

    override fun onDataChanged(categoryId: String?, serviceTypeId: String?, genderId: String?) {
        Log.d("TAG", "Data changed in fragment: $categoryId, $serviceTypeId, $genderId")
    }


}
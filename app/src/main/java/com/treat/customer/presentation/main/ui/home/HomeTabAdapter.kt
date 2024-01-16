package com.treat.customer.presentation.main.ui.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeTabAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private var categoryId: String? = null
    private var serviceTypeId: String? = null
    private var genderId: String? ="1"
    private var tabPages: Int = 0
    private var dataChangeListener: DataChangeListener? = null

    fun setDataChangeListener(listener: DataChangeListener) {
        this.dataChangeListener = listener
    }
    override fun getItemCount(): Int {
        return tabPages
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", "createFragment: ")
        return HomeTabFragment.newInstance(categoryId,serviceTypeId,genderId)
    }

    fun setPages(tabTitles: ArrayList<String>) {
        tabPages = tabTitles.size
    }

    fun setData(categoryId: String?, serviceTypeId: String?, genderId: String?) {
        this.categoryId = categoryId
        this.serviceTypeId = serviceTypeId
        this.genderId = genderId
        dataChangeListener?.onDataChanged(categoryId, serviceTypeId, genderId)


    }
    interface DataChangeListener {
        fun onDataChanged(categoryId: String?, serviceTypeId: String?, genderId: String?)
    }



}

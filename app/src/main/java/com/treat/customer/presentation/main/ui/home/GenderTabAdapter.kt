package com.treat.customer.presentation.main.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.treat.customer.data.model.ServiceCategoriesData

class GenderTabAdapter (
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {


    private var serviceCategoriesData: ServiceCategoriesData? = null
    private var tabPages: Int = 0

    override fun getItemCount(): Int {
        return tabPages
    }

    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }

    fun setPages(tabTitles: ArrayList<String>) {
        tabPages = tabTitles.size
    }

    fun setServices(serviceCategoriesData: ServiceCategoriesData) {
        this.serviceCategoriesData = serviceCategoriesData
        notifyDataSetChanged()
    }

}

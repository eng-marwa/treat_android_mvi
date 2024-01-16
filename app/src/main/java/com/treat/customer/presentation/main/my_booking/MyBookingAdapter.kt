package com.treat.customer.presentation.main.my_booking

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.treat.customer.data.model.MyBookingData

class MyBookingAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return MyBookingTabFragment.newInstance()
    }


}
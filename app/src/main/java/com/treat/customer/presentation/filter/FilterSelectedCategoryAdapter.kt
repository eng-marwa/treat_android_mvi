package com.treat.customer.presentation.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.databinding.SelectedItemBinding

class FilterSelectedCategoryAdapter ():
    RecyclerView.Adapter<FilterSelectedCategoryAdapter.FilterSelectedCategoryViewHolder>() {
    private var list: ArrayList<ServiceCategoriesData> = arrayListOf()

    inner class FilterSelectedCategoryViewHolder(private var binding: SelectedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: ServiceCategoriesData) {
            binding.lbSelectedITem.text = type.name


        }

        fun onEvent(type: ServiceCategoriesData) {
            binding.btnClose.setOnClickListener {
                list.remove(type)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterSelectedCategoryViewHolder {
        val binding = SelectedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterSelectedCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterSelectedCategoryViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size

    fun setSelectedList(selectedList: List<ServiceCategoriesData>) {
        list.clear()
        list.addAll(selectedList)
        notifyDataSetChanged()
    }

    fun resetCategory() {
        TODO("Not yet implemented")
    }


}
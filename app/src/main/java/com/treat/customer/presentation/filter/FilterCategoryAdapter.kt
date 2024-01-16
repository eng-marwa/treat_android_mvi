package com.treat.customer.presentation.filter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import com.treat.customer.R
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.databinding.OptionItemBinding
import com.treat.customer.domain.entities.MultipleChoiceData

class FilterCategoryAdapter(private var itemClicked: MultiOptionClick? = null) :
    RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryViewHolder>() {
    private var selectedItem: ServiceCategoriesData? = null
    private var list: ArrayList<MultipleChoiceData<ServiceCategoriesData>> = arrayListOf()


    inner class FilterCategoryViewHolder(private var binding: OptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(option: MultipleChoiceData<ServiceCategoriesData>) {
            binding.lbOptionItem.text = option.data!!.name
            binding.lbOptionItem.isAllCaps = false
            if (option.isSelected) {
                binding.lbOptionItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.green5E
                    )
                )
                binding.ivOptionSelected.visibility = View.VISIBLE
                binding.lbOptionItem.typeface = Typeface.DEFAULT_BOLD

            }  else {
                binding.lbOptionItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.gray4B
                    )
                )
                binding.ivOptionSelected.visibility = View.INVISIBLE
                binding.lbOptionItem.typeface = Typeface.DEFAULT
            }

        }

        fun onEvent(option: MultipleChoiceData<ServiceCategoriesData>) {
            binding.root.setOnClickListener {
                if (option.isSelected) {
                    unselectOption(option.data!!)
                } else {
                    selectOption(option.data)
                }
            }
        }
    }

    private fun selectOption(data: ServiceCategoriesData?) {
        val position = list.indexOfFirst { it.data!!.id == data!!.id }
        list[position].isSelected = true
        itemClicked?.onMultiOptionSelect(list[position])
        Log.d("TAG", "selectOption: ${list[position]}")
        notifyDataSetChanged()
    }

    private fun unselectOption(data: ServiceCategoriesData) {
        val position = list.indexOfFirst { it.data!!.id == data.id }
        list[position].isSelected = false
        itemClicked?.onMultiOptionUnselect(list[position])
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterCategoryAdapter.FilterCategoryViewHolder {
        val binding = OptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterCategoryAdapter.FilterCategoryViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size
    fun setList(categories: List<ServiceCategoriesData>) {
        val options = arrayListOf<MultipleChoiceData<ServiceCategoriesData>>()
        categories.forEach { options.add(MultipleChoiceData<ServiceCategoriesData>(it,false)) }
        list.clear()
        list.addAll(options)
        notifyDataSetChanged()
    }


    interface MultiOptionClick {
        fun onMultiOptionSelect(option: MultipleChoiceData<ServiceCategoriesData>)
        fun onMultiOptionUnselect(option: MultipleChoiceData<ServiceCategoriesData>)
    }

}
package com.treat.customer.presentation.main.ui.branches

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.databinding.ServiceTypeBinding

class CategoryAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var selectedItem: ServiceCategoriesData? = null
    private var list: ArrayList<ServiceCategoriesData> = arrayListOf()


    inner class CategoryViewHolder(private var binding: ServiceTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: ServiceCategoriesData, position: Int) {
            binding.lbServiceType.text = type.name
            binding.lbServiceType.isAllCaps = false
            if (type == selectedItem) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)
                binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.orangeB2) )

            } else if (position == 0 && selectedItem == null) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)
                binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.orangeB2) )

            } else {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.grayA4
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.outline_button_background)
                binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grayA4) )

            }

        }

        fun onEvent(type: ServiceCategoriesData) {
            binding.root.setOnClickListener {
                selectedItem = type
                itemClicked?.itemSelected(type)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val binding = ServiceTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryAdapter.CategoryViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size
    fun getSelectedOption(): ServiceCategoriesData? = selectedItem

    fun isItemSelected(): Boolean = selectedItem != null
    fun setList(categories: List<ServiceCategoriesData>) {
        list.clear()
        list.addAll(categories)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: ServiceCategoriesData)
    }
}
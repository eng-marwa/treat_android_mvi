package com.treat.customer.presentation.filter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.GenderData
import com.treat.customer.databinding.ServiceTypeBinding

class FilterGenderAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<FilterGenderAdapter.FilterGenderViewHolder>() {
    private var selectedGender: GenderData? = null
    private var list: ArrayList<GenderData> = arrayListOf()


    inner class FilterGenderViewHolder(private var binding: ServiceTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: GenderData, position: Int) {
            binding.lbServiceType.text = type.name
            if (type == selectedGender) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)
                context.let {
                    binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            it,
                            R.color.orangeB2
                        )
                    )
                }

            } else if (position == 0 && selectedGender == null) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)
                context.let {
                    binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            it,
                            R.color.orangeB2
                        )
                    )
                }

            } else {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.grayA4
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.outline_button_background)
                context.let {
                    binding.lbServiceType.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            it,
                            R.color.orangeB2
                        )
                    )
                }

            }
        }

        fun onEvent(type: GenderData) {
            binding.root.setOnClickListener {
                selectedGender = type
                itemClicked?.itemSelected(type)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterGenderViewHolder {
        val binding = ServiceTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterGenderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterGenderViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size
    fun getSelectedOption(): GenderData? = selectedGender

    fun isGenderSelected(): Boolean = selectedGender != null
    fun setGenderList(genderList: MutableList<GenderData>) {
        list.clear()
        list.addAll(genderList)
        notifyDataSetChanged()
    }

    fun resetGender() {
        TODO("Not yet implemented")
    }

    interface OnItemClick {
        fun itemSelected(item: GenderData)
    }
}
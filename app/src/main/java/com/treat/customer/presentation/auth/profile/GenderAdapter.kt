package com.treat.customer.presentation.auth.profile

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.GenderData
import com.treat.customer.databinding.OptionItemBinding

class GenderAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<GenderAdapter.GenderViewHolder>() {
    private var selectedGender: GenderData? = null
    private var list: ArrayList<GenderData> = arrayListOf()


    inner class GenderViewHolder(private var binding: OptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: GenderData, position: Int) {
            if (position == itemCount - 1) {
                binding.optionSeparator.visibility = View.GONE
            }
            binding.lbOptionItem.text = type.name
            if (type == selectedGender) {
                binding.ivOptionSelected.visibility = View.VISIBLE
                binding.lbOptionItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.green5E
                    )
                )
                binding.lbOptionItem.typeface = Typeface.DEFAULT_BOLD
            } else {
                binding.ivOptionSelected.visibility = View.GONE
                binding.lbOptionItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.gray4B
                    )
                )
                binding.lbOptionItem.typeface = Typeface.DEFAULT

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
    ): GenderAdapter.GenderViewHolder {
        val binding = OptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenderAdapter.GenderViewHolder, position: Int) {
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

    interface OnItemClick {
        fun itemSelected(item: GenderData)
    }
}
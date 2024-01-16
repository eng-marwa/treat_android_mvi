package com.treat.customer.presentation.main.ui.home

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.Services
import com.treat.customer.databinding.ServiceRowBinding


class ServiceAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {
    private var list: ArrayList<Services> = arrayListOf()
    private var selectedType: Services? = null

    inner class ServiceViewHolder(private var binding: ServiceRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: Services, position: Int) {
            binding.lbFavoriteSalon.text = type.name
            binding.lbTime.text = "${ type.estimatedTime} ${context.getString(R.string.min)}"
            binding.lbOldCost.text = "${type.regularPrice} ${context.getString(R.string.sar)}  "

            if(type.salePrice==null){
                binding.lbCost.visibility = View.GONE
            }else{
                binding.lbCost.text = "${type.salePrice} ${context.getString(R.string.sar)} "
                binding.lbOldCost.paintFlags = binding.lbOldCost.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }

            binding.lbFavoriteSalon.text = type.name
        }

        fun onEvent(type: Services) {
            binding.root.setOnClickListener {
                selectedType = type
                itemClicked?.itemSelected(type)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceAdapter.ServiceViewHolder {
        val binding = ServiceRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceAdapter.ServiceViewHolder, position: Int) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size

    fun setServices(serviceTypes: List<Services>) {
        list.clear()
        list.addAll(serviceTypes)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: Services)
    }
}
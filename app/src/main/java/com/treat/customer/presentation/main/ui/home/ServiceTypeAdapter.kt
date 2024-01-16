package com.treat.customer.presentation.main.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.ServiceTypeData
import com.treat.customer.databinding.ServiceTypeBinding
import com.treat.customer.domain.entities.Language

class ServiceTypeAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<ServiceTypeAdapter.ServiceTypeViewHolder>() {
    private var list: ArrayList<ServiceTypeData> = arrayListOf()
    private var selectedType: ServiceTypeData? = null

    inner class ServiceTypeViewHolder(private var binding: ServiceTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: ServiceTypeData, position: Int) {
            binding.lbServiceType.text = type.name
            if (type == selectedType) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)

            } else if (position == 0 && selectedType == null) {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.button_background_tab)

            } else {
                binding.lbServiceType.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.grayA4
                    )
                )
                binding.lbServiceType.setBackgroundResource(R.drawable.outline_button_background)

            }

        }

        fun onEvent(type: ServiceTypeData) {
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
    ): ServiceTypeAdapter.ServiceTypeViewHolder {
        val binding = ServiceTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceTypeAdapter.ServiceTypeViewHolder, position: Int) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size

    fun setServiceTypes(serviceTypes: List<ServiceTypeData>) {
        list.clear()
        list.addAll(serviceTypes)
        notifyDataSetChanged()
    }



    interface OnItemClick {
        fun itemSelected(item: ServiceTypeData)
    }
}
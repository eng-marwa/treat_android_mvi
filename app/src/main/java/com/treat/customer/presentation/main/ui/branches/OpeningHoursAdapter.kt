package com.treat.customer.presentation.main.ui.branches

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.data.model.OpeningHours
import com.treat.customer.databinding.OpeningHoursLayoutBinding

class OpeningHoursAdapter :
    RecyclerView.Adapter<OpeningHoursAdapter.OpeningHoursViewHolder>() {
    private var list: ArrayList<OpeningHours> = arrayListOf()

    inner class OpeningHoursViewHolder(private var binding: OpeningHoursLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: OpeningHours) {
            Log.d("TAG", "bindDataToView: $type")
            binding.lbDay.text = type.day
            binding.lbDate.text = "${type.start} - ${type.end}"
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpeningHoursViewHolder {
        val binding =
            OpeningHoursLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OpeningHoursViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OpeningHoursViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type)
    }

    override fun getItemCount(): Int = list.size

    fun setList(hours: List<OpeningHours>) {
        list.clear()
        list.addAll(hours)
        notifyDataSetChanged()
    }


}
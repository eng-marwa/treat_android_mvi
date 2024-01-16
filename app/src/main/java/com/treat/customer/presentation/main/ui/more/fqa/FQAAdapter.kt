package com.treat.customer.presentation.main.ui.more.fqa

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.FQAData
import com.treat.customer.data.model.MyPointsData
import com.treat.customer.databinding.PointRowBinding
import com.treat.customer.databinding.SupportTitleRowBinding
import com.treat.customer.presentation.main.ui.more.my_points.MyPointsAdapter

class FQAAdapter :
    RecyclerView.Adapter<FQAAdapter.FQAViewHolder>() {
    private var list: ArrayList<FQAData> = arrayListOf()

    inner class FQAViewHolder(private var binding: SupportTitleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(data: FQAData, position: Int) {
            binding.listTitle.text = data.name
            binding.listTitle.text = data.note
        }

        fun onEvent(data: FQAData, position: Int) {
            binding.lnTitle.setOnClickListener {
                if (!binding.listText.isVisible) {
                    binding.listText.visibility = View.VISIBLE
                    val boldTypeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                    binding.listTitle.typeface = boldTypeface
                    binding.listText.text = data.note
                    binding.ivDropDown.setImageResource(R.drawable.bx_chevron_up)

                } else {
                    binding.listText.visibility = View.GONE
                    val boldTypeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                    binding.listTitle.typeface = boldTypeface
                    binding.ivDropDown.setImageResource(R.drawable.bx_chevron_down)

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FQAAdapter.FQAViewHolder {
        val binding =
            SupportTitleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FQAViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FQAAdapter.FQAViewHolder, position: Int) {
        val data = list[position]
        holder.bindDataToView(data, position)
        holder.onEvent(data, position)
    }

    override fun getItemCount(): Int = list.size

    fun setFAQData(fqaData: ArrayList<FQAData>) {
        list.clear()
        list.addAll(fqaData)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: MyPointsData)
    }

}
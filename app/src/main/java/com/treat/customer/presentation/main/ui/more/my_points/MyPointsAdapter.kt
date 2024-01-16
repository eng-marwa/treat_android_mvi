package com.treat.customer.presentation.main.ui.more.my_points

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.data.model.MyPointsData
import com.treat.customer.data.model.MyPointsDetails
import com.treat.customer.databinding.PointRowBinding

class MyPointsAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<MyPointsAdapter.MyPointsViewHolder>() {
    private var list: ArrayList<MyPointsDetails> = arrayListOf()


    inner class MyPointsViewHolder(private var binding: PointRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(data: MyPointsDetails, position: Int) {
            binding.lbTime.text = data.createdAt
            binding.lbPaymentAmount.text = data.amount
            binding.lbPointValue.text = "${data.amountInSar}"
            binding.lbId.text = "#${data.id}"

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPointsAdapter.MyPointsViewHolder {
        val binding = PointRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPointsAdapter.MyPointsViewHolder, position: Int) {
        val type = list[position]
        holder.bindDataToView(type, position)
    }

    override fun getItemCount(): Int = list.size

    fun setMyPointsList(myPoints: ArrayList<MyPointsDetails>) {
        list.clear()
        list.addAll(myPoints)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: MyPointsData)
    }
}
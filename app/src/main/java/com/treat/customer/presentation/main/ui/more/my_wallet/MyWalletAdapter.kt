package com.treat.customer.presentation.main.ui.more.my_wallet

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.MyPointsData
import com.treat.customer.data.model.MyWalletData
import com.treat.customer.data.model.MyWalletDetails
import com.treat.customer.databinding.OptionItemBinding
import com.treat.customer.databinding.PointRowBinding
import com.treat.customer.presentation.auth.profile.GenderAdapter

class MyWalletAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<MyWalletAdapter.MyWalletViewHolder>() {
    private var list: ArrayList<MyWalletDetails> = arrayListOf()


    inner class MyWalletViewHolder(private var binding: PointRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(data: MyWalletDetails, position: Int) {
            binding.lbTime.text = data.createdAt
            binding.lbPaymentAmount.text = "${data.amount}"
        }

        fun onEvent(type: GenderData) {
            binding.root.setOnClickListener {
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyWalletAdapter.MyWalletViewHolder {
        val binding = PointRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyWalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyWalletViewHolder, position: Int) {
        val type = list[position]
        holder.bindDataToView(type, position)
    }

    override fun getItemCount(): Int = list.size


    fun setWalletDataList(details: ArrayList<MyWalletDetails>) {
        list.clear()
        list.addAll(details)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: GenderData)
    }
}
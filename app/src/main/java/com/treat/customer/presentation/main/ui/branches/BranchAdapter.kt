package com.treat.customer.presentation.main.ui.branches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.treat.customer.R
import com.treat.customer.data.model.Branches
import com.treat.customer.data.model.BranchesData
import com.treat.customer.data.model.Services
import com.treat.customer.databinding.BranchRowBinding
import com.treat.customer.databinding.ServiceRowBinding

class BranchAdapter(private var itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<BranchAdapter.BranchAdapterViewHolder>() {
    private var list: ArrayList<Branches> = arrayListOf()
    private var selectedType: Branches? = null
    private val isFav = true

    inner class BranchAdapterViewHolder(private var binding: BranchRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: Branches, position: Int) {
            binding.lbFavoriteSalon.text = type.name
            binding.btnFav.setImageResource(R.drawable.bx_heart)
            try {
                Glide.with(context).asBitmap().load(type.logo).into(binding.ivFavorite)
            }catch (e:Exception){}
            binding.lbCity.text = type.city
            binding.lbRate.text = type.rate ?: "0"
            if (type.distance != null) {
                binding.lbDistance.text = "${type.distance} km"
            } else {
                binding.lbDistance.visibility = View.GONE
            }
            if (type.ratesCount?.toInt()!! > 0) {
                binding.lbRateCount.visibility = View.GONE
                binding.lbRateCount.text = "(${type.ratesCount})"
            } else {
                binding.lbRateCount.visibility = View.GONE
            }
            if (type.isFavourite) {
                binding.btnFav.setImageResource(R.drawable.bxs_heart)
            } else {
                binding.btnFav.setImageResource(R.drawable.bx_heart)
            }
            if (type.isSpecial) {
                binding.lbBanner.text = context.getString(R.string.special)
                binding.lbBanner.visibility = View.VISIBLE
                binding.lbBanner.setBackgroundColor(context.getColor(R.color.green5E))
                binding.lbBanner.setTextColor(context.getColor(R.color.white))

            } else if (type.coupons != null) {
                binding.lbBanner.text = type.coupons?.title
                binding.lbBanner.visibility = View.VISIBLE
                binding.lbBanner.setBackgroundColor(context.getColor(R.color.black))
                binding.lbBanner.setTextColor(context.getColor(R.color.white))

            } else {
                binding.lbBanner.visibility = View.GONE
            }
        }

        fun onEvent(type: Branches) {
            binding.root.setOnClickListener {
                selectedType = type
                itemClicked?.itemSelected(type)
            }
            binding.btnFav.setOnClickListener {
                if (type.isFavourite) {
                    type.isFavourite = false
                    itemClicked?.removeItemFromFavorite(type)
                    binding.btnFav.setImageResource(R.drawable.bx_heart)

                } else {
                    type.isFavourite = true
                    itemClicked?.addItemToFavorite(type)
                    binding.btnFav.setImageResource(R.drawable.bxs_heart)
                }
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BranchAdapter.BranchAdapterViewHolder {
        val binding = BranchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BranchAdapter.BranchAdapterViewHolder, position: Int) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size

    fun setBranches(branches: List<Branches>) {
        list.clear()
        list.addAll(branches)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: Branches)
        fun addItemToFavorite(item: Branches)
        fun removeItemFromFavorite(item: Branches)
    }
}
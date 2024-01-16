package com.treat.customer.presentation.main.ui.branches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.treat.customer.data.model.Images
import com.treat.customer.databinding.SliderItemBinding

class BranchSliderAdapter (
    private val context: Context,
) : SliderViewAdapter<BranchSliderAdapter.BranchSliderVH>() {

    private var mSliderItems: MutableList<Images> = ArrayList()
    fun renewItems(sliderItems: MutableList<Images>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(image: Images) {
        mSliderItems.add(image)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): BranchSliderVH = BranchSliderVH(
        SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(viewHolder: BranchSliderVH, position: Int) {
        val imageRes = mSliderItems[position]
        try {
            Glide.with(context).asBitmap().load(imageRes.url).into(viewHolder.imageViewBackground)

        }catch (e:Exception){
        }
        viewHolder.itemView.setOnClickListener {

        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    fun setData(data: ArrayList<Images>) {
        mSliderItems.clear()
        mSliderItems.addAll(data)
        notifyDataSetChanged()

    }


    inner class BranchSliderVH(itemView: SliderItemBinding) : ViewHolder(itemView.root) {
        var imageViewBackground: ImageView = itemView.imageView

    }
}



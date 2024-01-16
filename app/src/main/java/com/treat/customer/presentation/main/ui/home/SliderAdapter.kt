package com.treat.customer.presentation.main.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.treat.customer.data.model.HomeSliderData
import com.treat.customer.databinding.SliderItemBinding

class SliderAdapter(
    private val context: Context,
) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    private var mSliderItems: MutableList<String> = ArrayList()
    fun renewItems(sliderItems: MutableList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(image: String) {
        mSliderItems.add(image)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH = SliderAdapterVH(
        SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val imageRes = mSliderItems[position]
        try {
            Glide.with(context).asBitmap().load(imageRes).into(viewHolder.imageViewBackground)

        }catch (e:Exception){
        }
        viewHolder.itemView.setOnClickListener {

        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    fun setData(data: ArrayList<HomeSliderData>) {
        val imagesList = mutableListOf<String>()
        data.forEach { it.image?.let { imagesList.add(it) } }
        mSliderItems = imagesList
        notifyDataSetChanged()

    }

    inner class SliderAdapterVH(itemView: SliderItemBinding) : ViewHolder(itemView.root) {
        var imageViewBackground: ImageView = itemView.imageView

    }
}



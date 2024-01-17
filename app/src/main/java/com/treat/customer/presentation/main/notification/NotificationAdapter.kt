package com.treat.customer.presentation.main.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.treat.customer.data.model.Notifications
import com.treat.customer.databinding.NotificationRowBinding

class NotificationAdapter(private val itemClicked: OnItemClick? = null) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private var list: ArrayList<Notifications> = arrayListOf()

    inner class NotificationViewHolder(private var binding: NotificationRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: Notifications, position: Int) {
            binding.lbNotificationBody.text = type.body
            binding.lbNotificationTitle.text = type.title
            binding.lbStatus.text = type.date1
            try {
                Glide.with(context).asBitmap().load(type.sender?.image).into(binding.ivNotification)
            } catch (e: Exception) {
            }


        }

        fun onEvent(type: Notifications) {
            binding.root.setOnClickListener {
                itemClicked?.itemSelected(type)

            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.NotificationViewHolder {
        val binding =
            NotificationRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationAdapter.NotificationViewHolder,
        position: Int
    ) {
        val type = list[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = list.size

    fun setData(notifications: ArrayList<Notifications>) {
        list.clear()
        list.addAll(notifications)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: Notifications)
    }
}
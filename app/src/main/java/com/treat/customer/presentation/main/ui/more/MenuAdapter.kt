package com.treat.customer.presentation.main.ui.more

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.treat.customer.R
import com.treat.customer.data.model.GenderData
import com.treat.customer.databinding.MenuItemBinding
import com.treat.customer.domain.entities.Menu
import com.treat.customer.domain.entities.menuItems
import com.treat.customer.presentation.auth.profile.GenderAdapter

class MenuAdapter(private var itemClicked: MenuAdapter.OnItemClick? = null) :
    RecyclerView.Adapter<MenuAdapter.MenuItemHolder>() {


    private var menuItems: List<Menu> = arrayListOf()
    private var selectedMenu: Menu? = null

    inner class MenuItemHolder(private val binding: MenuItemBinding) : ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(menu: Menu, position: Int) {
            if (position == itemCount - 1) {
                binding.optionSeparator.visibility = View.GONE
            }
            if (menu.name == R.string.logout || menu.name == R.string.disable_account)  {
                binding.lbItem.setTextColor(context.resources.getColor(R.color.redD4))
            }
            binding.ivItem.setImageResource(menu.icon)
            binding.lbItem.setText(menu.name)
        }

        fun onEvent(type: Menu) {
            binding.root.setOnClickListener {
                selectedMenu = type
                itemClicked?.itemSelected(type)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder =
        MenuItemHolder(
            MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        val type = this.menuItems[position]
        holder.bindDataToView(type, position)
        holder.onEvent(type)
    }

    override fun getItemCount(): Int = this.menuItems.size
    fun setData(menuItems: List<Menu>) {
        this.menuItems = menuItems
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun itemSelected(item: Menu)
    }
}
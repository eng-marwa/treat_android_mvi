package com.treat.customer.presentation.main.ui.more.languages

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.databinding.LanguageRowBinding
import com.treat.customer.domain.entities.Language
import com.treat.customer.domain.entities.languages

class LanguageAdapter(private var itemClicked: LanguageAdapter.OnItemClick? = null) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    private var selectedLanguage: Language? = null


    inner class LanguageViewHolder(private var binding: LanguageRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bindDataToView(type: Language, position: Int) {

            binding.lbItem.text = context.getString(type.name)
            binding.ivItem.setImageResource(type.icon)
            if (type == selectedLanguage) {
                binding.ivOptionSelected.visibility = View.VISIBLE
                binding.lbItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.green5E
                    )
                )
                binding.languageLayout.setBackgroundResource(R.drawable.outline_curved_white_rectangle)
                binding.lbItem.typeface = Typeface.DEFAULT_BOLD
            } else {
                binding.ivOptionSelected.visibility = View.GONE
                binding.lbItem.setTextColor(
                    ActivityCompat.getColor(
                        context,
                        R.color.gray4B
                    )
                )
                binding.lbItem.typeface = Typeface.DEFAULT
                binding.languageLayout.setBackgroundResource(R.drawable.curved_white_rectangle)

            }
        }

        fun onEvent(type: Language) {
            binding.root.setOnClickListener {
                selectedLanguage = type
                itemClicked?.itemSelected(type)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageAdapter.LanguageViewHolder {
        val binding = LanguageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageAdapter.LanguageViewHolder, position: Int) {
        val lang = languages[position]
        holder.bindDataToView(lang, position)
        holder.onEvent(lang)
    }

    override fun getItemCount(): Int = languages.size
    fun getSelectedOption(): Language? = selectedLanguage

    fun isLanguageSelected(): Boolean = selectedLanguage != null

    interface OnItemClick {
        fun itemSelected(item: Language)
    }
}
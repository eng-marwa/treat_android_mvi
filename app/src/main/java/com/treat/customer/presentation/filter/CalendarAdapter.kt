package com.treat.customer.presentation.filter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treat.customer.R
import com.treat.customer.databinding.ItemCalendarDayBinding
import com.treat.customer.domain.entities.Day
import com.treat.customer.utils.SingleLineCalender.isCurrentDay
import com.treat.customer.utils.SingleLineCalender.isDayBefore

class CalendarAdapter(private val onDateSelected: FilterBottomSheet) :
    RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder>() {

    private var selectedDate: Day? = null
    private var monthYear: String = ""
    private var data: ArrayList<Day> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        val binding =
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalenderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        holder.bind(data[position])
        holder.onEvent(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setCalenderData(forAllYears: List<Day>, monthYear: String) {
        data.clear()
        data.addAll(forAllYears)
        this.monthYear = monthYear
        notifyDataSetChanged()
    }

    inner class CalenderViewHolder(private val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        fun bind(day: Day) {
            binding.dateTextView.text = day.dayNo
            binding.dayTextView.text = day.dayName
            if (day == selectedDate) {
                binding.root.background.setTint(context.getColor(R.color.orangeB2));
                binding.dayTextView.setTextColor(context.getColor(R.color.white))
                binding.dateTextView.setTextColor(context.getColor(R.color.white))
                binding.root.isClickable = true
                binding.root.isEnabled = true

            } else if (isDayBefore(day.dayNo,monthYear)) {
                binding.root.background.setTint(context.getColor(R.color.grayEB));
                binding.dayTextView.setTextColor(context.getColor(R.color.grayA4))
                binding.dateTextView.setTextColor(context.getColor(R.color.grayA4))
                binding.root.isClickable = false
                binding.root.isEnabled = false

            } else {
                if (isCurrentDay(day.dayNo, monthYear)) {
                    binding.dayTextView.setTextColor(context.getColor(R.color.orangeB2))
                    binding.dateTextView.setTextColor(context.getColor(R.color.orangeB2))
                    binding.dateTextView.typeface = Typeface.DEFAULT_BOLD
                    binding.dayTextView.typeface = Typeface.DEFAULT_BOLD

                } else {
                    binding.root.background.setTint(context.getColor(R.color.grayEB));
                    binding.dayTextView.setTextColor(context.getColor(R.color.black))
                    binding.dateTextView.setTextColor(context.getColor(R.color.black))
                    binding.dateTextView.typeface = Typeface.DEFAULT
                    binding.dayTextView.typeface = Typeface.DEFAULT
                }
                binding.root.isClickable = true
                binding.root.isEnabled = true
            }
        }

        fun onEvent(day: Day) {
            binding.root.setOnClickListener {
                selectedDate = day
                onDateSelected.onDaySelected(selectedDate!!, monthYear)
                notifyDataSetChanged()
            }
        }
    }

    interface OnDateSelected {
        fun onDaySelected(day: Day, monthYear: String)
    }
}

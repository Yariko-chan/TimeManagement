package com.ganeeva.d.f.timemanagement.task_view.ui.time_gaps_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap
import java.text.SimpleDateFormat

class TimeGapAdapter(
    initialList: List<TimeGap>? = null,
    val dateFormat: SimpleDateFormat,
    val durationFormat: SimpleDateFormat
): RecyclerView.Adapter<TimeGapViewHolder>() {

    private val items = mutableListOf<TimeGap>()
        .apply { initialList?.let { addAll(initialList) } }

    fun updateList(list: List<TimeGap>) {
        items.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeGapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_time_gap, parent, false)
        return TimeGapViewHolder(view, dateFormat, durationFormat)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimeGapViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
package com.ganeeva.d.f.timemanagement.task_view.ui.time_gaps_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_time_gap.*
import java.text.SimpleDateFormat

class TimeGapViewHolder(
    override val containerView: View,
    private val dateFormat: SimpleDateFormat,
    private val durationFormat: SimpleDateFormat
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(timeGap: TimeGap) {
        start_time_text_view.text = dateFormat.format(timeGap.startTime)
        duration_text_view.text = timeGap.endTime?.let { durationFormat.format(it - timeGap.startTime) } ?: ""

    }
}
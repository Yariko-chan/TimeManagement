package com.ganeeva.d.f.timemanagement.task_view.ui.subtask_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.isRunning
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_subtask_view.*
import java.text.SimpleDateFormat

class ViewSubtaskViewHolder(
    override val containerView: View,
    private val durationFormat: SimpleDateFormat
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(subTask: SubTask, onChecked: (isChecked: Boolean, subTask: SubTask) -> Unit) {
        name_text_view.text = subTask.name
        showRunCheckBox(subTask, onChecked)
    }

    private fun showRunCheckBox(
        task: SubTask,
        onChecked: (isChecked: Boolean, subtask: SubTask) -> Unit
    ) {
        run_time_checkbox.setOnCheckedChangeListener(null)
        run_time_checkbox.apply {
            isChecked = task.isRunning()
            text = task.duration.value?.let { durationFormat.format(it) } ?: ""
        }
        run_time_checkbox.setOnCheckedChangeListener { _, isChecked ->
            onChecked(isChecked, task)
        }
    }

    fun updateDuration(duration: Long) {
        run_time_checkbox.text = durationFormat.format(duration)
    }

    fun updateCheckedButton(isChecked: Boolean) {
        run_time_checkbox.isChecked = isChecked
    }
}
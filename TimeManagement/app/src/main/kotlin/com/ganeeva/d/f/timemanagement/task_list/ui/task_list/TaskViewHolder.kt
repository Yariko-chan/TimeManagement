package com.ganeeva.d.f.timemanagement.task_list.ui.task_list

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.core.extensions.gone
import com.ganeeva.d.f.timemanagement.core.extensions.visible
import com.ganeeva.d.f.timemanagement.task_list.ui.subtask_list.MainSubtaskAdapter
import com.ganeeva.d.f.timemanagement.task.domain.model.task.StandaloneTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SteppedTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import com.ganeeva.d.f.timemanagement.task.domain.model.task.isRunning
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.*
import java.text.SimpleDateFormat
import java.util.*


class TaskViewHolder(
    override val containerView: View,
    private val dateFormat: SimpleDateFormat,
    private val durationFormat: SimpleDateFormat
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val adapter = MainSubtaskAdapter()

    init {
        subtasks_recyclerview.layoutManager = LinearLayoutManager(containerView.context, LinearLayoutManager.VERTICAL, false)
        subtasks_recyclerview.adapter = adapter
    }

    fun bind(task: Task, onChecked: (isChecked: Boolean, task: Task) -> Unit) {
        header_text_view.text = task.name
        showDescription(task.description)
        date_text_view.text = dateFormat.format(Date(task.creationDate))
        showRunCheckBox(task, onChecked)
        if (task is SteppedTask) {
            adapter.updateList(task.subtasks)
        }
    }

    private fun showDescription(description: String) {
        when {
            description.isEmpty() -> description_text_view.gone()
            else -> {
                description_text_view.visible()
                description_text_view.text = description
            }
        }
    }

    private fun showRunCheckBox(
        task: Task,
        onChecked: (isChecked: Boolean, task: Task) -> Unit
    ) {
        run_time_checkbox.setOnCheckedChangeListener(null)
        run_time_checkbox.apply {
            isChecked = task.isRunning()
            text = task.duration.value?.let { durationFormat.format(it) }
                ?: "" // todo use other model?
        }
        run_time_checkbox.setOnCheckedChangeListener { _, isChecked ->
            onChecked(isChecked, task)
        }
        run_time_checkbox.isEnabled = task is StandaloneTask
    }

    fun updateDuration(duration: Long) {
        run_time_checkbox.text = durationFormat.format(duration)
    }

    fun updateCheckedButton(isChecked: Boolean) {
        run_time_checkbox.isChecked = isChecked
    }
}
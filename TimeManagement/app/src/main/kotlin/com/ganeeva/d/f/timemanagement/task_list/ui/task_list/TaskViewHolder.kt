package com.ganeeva.d.f.timemanagement.task_list.ui.task_list

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.core.extensions.gone
import com.ganeeva.d.f.timemanagement.core.extensions.visible
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task_list.ui.subtask_list.MainSubtaskAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.*
import java.text.SimpleDateFormat
import java.util.*

val TASK_DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)

class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val adapter = MainSubtaskAdapter()

    init {
        subtasks_recyclerview.layoutManager = LinearLayoutManager(containerView.context, LinearLayoutManager.VERTICAL, false)
        subtasks_recyclerview.adapter = adapter
    }

    fun bind(task: Task) {
        header_text_view.text = task.name
        when {
            task.description.isEmpty() -> description_text_view.gone()
            else -> {
                description_text_view.visible()
                description_text_view.text = task.description
            }
        }
        date_text_view.text = TASK_DATE_FORMAT.format(Date(task.creationDate))
        adapter.updateList(task.subtasks)
    }
}
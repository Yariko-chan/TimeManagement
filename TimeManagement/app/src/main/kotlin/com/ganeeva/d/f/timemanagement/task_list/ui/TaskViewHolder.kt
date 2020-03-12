package com.ganeeva.d.f.timemanagement.task_list.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.task.domain.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.*

class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(task: Task) {
        header_text_view.text = task.name
        description_text_view.text = task.description
    }
}
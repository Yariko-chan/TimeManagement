package com.ganeeva.d.f.timemanagement.task_view.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.task.domain.SubTask
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_subtask_main.*

class ViewSubtaskViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(task: SubTask) {
        name_text_view.text = task.name
    }
}
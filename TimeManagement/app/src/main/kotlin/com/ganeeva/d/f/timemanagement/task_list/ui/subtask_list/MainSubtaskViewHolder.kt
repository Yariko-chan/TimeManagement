package com.ganeeva.d.f.timemanagement.task_list.ui.subtask_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_subtask_main.*

class MainSubtaskViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(task: SubTask) {
        name_text_view.text = task.name
    }
}
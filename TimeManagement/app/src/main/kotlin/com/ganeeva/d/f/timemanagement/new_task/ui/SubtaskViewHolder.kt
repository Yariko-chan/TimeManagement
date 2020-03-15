package com.ganeeva.d.f.timemanagement.new_task.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_subtask_new.*

class SubtaskViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(task: String) {
        name_text_view.text = task
    }
}
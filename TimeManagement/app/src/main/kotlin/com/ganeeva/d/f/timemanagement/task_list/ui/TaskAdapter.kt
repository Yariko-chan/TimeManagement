package com.ganeeva.d.f.timemanagement.task_list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.Task

typealias OnClick = (position: Int, item: Task) -> Unit

class TaskAdapter(
    initialList: List<Task>? = null,
    private val onClick: OnClick? = null
): RecyclerView.Adapter<TaskViewHolder>() {

    private val items = mutableListOf<Task>()
        .apply { initialList?.let { addAll(initialList) } }

    fun updateList(list: List<Task>) {
        items.apply {
            clear()
            addAll(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val holder = TaskViewHolder(view)
        onClick?.let {
            holder.containerView.setOnClickListener {
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    it(position, items[position])
                }
            }
        }
        return holder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
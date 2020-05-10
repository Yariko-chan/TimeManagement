package com.ganeeva.d.f.timemanagement.task_list.ui.task_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import java.text.SimpleDateFormat

typealias OnClick = (position: Int, item: Task) -> Unit

class TaskAdapter(
    initialList: List<Task>? = null,
    private val onClick: OnClick? = null,
    private val dateFormat: SimpleDateFormat,
    private val durationFormat: SimpleDateFormat,
    private val onCheckedListener: (isChecked: Boolean, task: Task) -> Unit
): RecyclerView.Adapter<TaskViewHolder>() {

    private val items = mutableListOf<Task>()
        .apply { initialList?.let { addAll(initialList) } }

    val displayedItems: List<Task>
        get() = items

    fun updateList(list: List<Task>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val holder = TaskViewHolder(view, dateFormat, durationFormat)
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
        holder.bind(items[position], onCheckedListener)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.forEach { payload ->
                when (payload) {
                    is Long -> holder.updateDuration(payload)
                    is Boolean -> holder.updateCheckedButton(payload)
                }
            }
        }
    }
}
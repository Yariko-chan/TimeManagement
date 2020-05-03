package com.ganeeva.d.f.timemanagement.task_view.ui.subtask_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask
import java.text.SimpleDateFormat

class ViewSubTaskAdapter(
    initialList: List<SubTask>? = null,
    private val durationFormat: SimpleDateFormat,
    private val onCheckedListener: (isChecked: Boolean, task: SubTask) -> Unit,
    private val onDeleteClicked: (SubTask) -> Unit
): RecyclerView.Adapter<ViewSubtaskViewHolder>() {

    private val items = mutableListOf<SubTask>()
        .apply { initialList?.let { addAll(initialList) } }

    val displayedItems: List<SubTask>
        get() = items

    fun updateList(list: List<SubTask>) {
        items.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask_view, parent, false)
        return ViewSubtaskViewHolder(view, durationFormat)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewSubtaskViewHolder, position: Int) {
        holder.bind(items[position], onCheckedListener, onDeleteClicked)
    }

    override fun onBindViewHolder(
        holder: ViewSubtaskViewHolder,
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
package com.ganeeva.d.f.timemanagement.task_view.ui.subtask_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SubTask

class ViewSubtaskAdapter(
    initialList: List<SubTask>? = null
): RecyclerView.Adapter<ViewSubtaskViewHolder>() {

    private val items = mutableListOf<SubTask>()
        .apply { initialList?.let { addAll(initialList) } }

    fun updateList(list: List<SubTask>) {
        items.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask_view, parent, false)
        return ViewSubtaskViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewSubtaskViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
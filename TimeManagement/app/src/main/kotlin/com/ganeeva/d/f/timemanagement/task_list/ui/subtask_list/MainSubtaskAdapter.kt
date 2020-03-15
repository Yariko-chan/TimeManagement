package com.ganeeva.d.f.timemanagement.task_list.ui.subtask_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.SubTask

class MainSubtaskAdapter(
    initialList: List<SubTask>? = null
): RecyclerView.Adapter<MainSubtaskViewHolder>() {

    private val items = mutableListOf<SubTask>()
        .apply { initialList?.let { addAll(initialList) } }

    fun updateList(list: List<SubTask>) {
        items.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainSubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask_main, parent, false)
        return MainSubtaskViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainSubtaskViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
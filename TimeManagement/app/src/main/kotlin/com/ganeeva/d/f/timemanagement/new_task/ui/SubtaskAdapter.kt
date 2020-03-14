package com.ganeeva.d.f.timemanagement.new_task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.task.domain.Task

class SubtaskAdapter(
    initialList: List<String>? = null
): RecyclerView.Adapter<SubtaskViewHolder>() {

    private val items = mutableListOf<String>()
        .apply { initialList?.let { addAll(initialList) } }

    fun updateList(list: List<String>) {
        items.apply {
            clear()
            addAll(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask, parent, false)
        return SubtaskViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SubtaskViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
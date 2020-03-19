package com.ganeeva.d.f.timemanagement.task_list.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.TASK_DATE_FORMAT
import com.ganeeva.d.f.timemanagement.core.extensions.gone
import com.ganeeva.d.f.timemanagement.core.extensions.navigateWithCheck
import com.ganeeva.d.f.timemanagement.core.extensions.visible
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task_list.ui.task_list.TaskAdapter
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.include_background_text.*
import kotlinx.android.synthetic.main.include_progress.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat

class TaskListFragment(): Fragment(R.layout.fragment_task_list) {

    companion object {
        fun newInstance(): TaskListFragment {
            val fragment = TaskListFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: TaskListViewModel by viewModel()
    private val dateFormat: SimpleDateFormat by inject(named(TASK_DATE_FORMAT))
    private val adapter: TaskAdapter by lazy {
        TaskAdapter(
            dateFormat = dateFormat,
            onClick = { position, task -> onTaskClicked(position, task) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(R.string.task_list_header)
        task_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        task_recycler_view.adapter = adapter

        add_new_button.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListToNewTask()
            view.findNavController().navigateWithCheck(action)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            progress_group.visibility = if(it) View.VISIBLE else View.GONE
        })
        viewModel.tasksListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
            background_text.gone()
            task_recycler_view.visible()
        })
        viewModel.emptyListLiveData.observe(viewLifecycleOwner, Observer {
            background_text.visible()
            task_recycler_view.gone()
        })
        viewModel.errorEvent.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.showTaskEvent.observe(viewLifecycleOwner, Observer {id ->
            val action = TaskListFragmentDirections.actionTaskListToViewTask(id)
            view.findNavController().navigateWithCheck(action)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.onViewVisible()
    }

    // todo extract reusable component
    private fun setupToolbar(
        @StringRes title: Int,
        @DrawableRes startIcon: Int? = null,
        @DrawableRes endIcon: Int? = null,
        onStartClicked: ((View) -> Unit)? = null,
        onEndClicked: ((View) -> Unit)? = null
    ) {
        title_textview.setText(title)
        if (startIcon == null) {
            start_image_view.visibility = View.INVISIBLE
        } else {
            start_image_view.setImageResource(startIcon)
        }
        if (endIcon == null) {
            end_image_view.visibility = View.GONE
        } else {
            end_image_view.setImageResource(endIcon)
        }
        onStartClicked?.let { start_image_view.setOnClickListener(it) }
        onEndClicked?.let{ end_image_view.setOnClickListener(it) }
    }

    private fun onTaskClicked(position: Int, task: Task) {
        viewModel.onTaskClicked(position, task)
    }
}
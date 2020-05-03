package com.ganeeva.d.f.timemanagement.task_list.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.DURATION
import com.ganeeva.d.f.timemanagement.core.TASK_DATE
import com.ganeeva.d.f.timemanagement.core.extensions.gone
import com.ganeeva.d.f.timemanagement.core.extensions.navigateWithCheck
import com.ganeeva.d.f.timemanagement.core.extensions.visible
import com.ganeeva.d.f.timemanagement.task_list.ui.task_list.TaskAdapter
import com.ganeeva.d.f.timemanagement.task_time_service.NotificationData
import com.ganeeva.d.f.timemanagement.task_time_service.TaskRunningService
import com.ganeeva.d.f.timemanagement.task.domain.model.task.StandaloneTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SteppedTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import com.ganeeva.d.f.timemanagement.task.domain.model.task.isRunning
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.include_background_text.*
import kotlinx.android.synthetic.main.include_progress.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat

class TaskListFragment: Fragment(R.layout.fragment_task_list) {

    private val viewModel: TaskListViewModel by viewModel()
    private val dateFormat: SimpleDateFormat by inject(named(TASK_DATE))
    private val durationFormat: SimpleDateFormat by inject(named(DURATION))
    private val adapter: TaskAdapter by lazy {
        TaskAdapter(
            dateFormat = dateFormat,
            durationFormat = durationFormat,
            onClick = { position, task -> onTaskClicked(position, task) })
        { isChecked, task -> viewModel.onTaskChecked(task, isChecked)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = R.string.task_list_header,
            endIcon = R.drawable.ic_filter,
            onEndClicked = { viewModel.onFilterClicked() })
        task_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        task_recycler_view.adapter = adapter

        add_new_button.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListToNewTask()
            findNavController().navigateWithCheck(action)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            progress_group.visibility = if(it) View.VISIBLE else View.GONE
        })
        viewModel.tasksListLiveData.observe(viewLifecycleOwner, Observer {
            subscribeItemsDuration(it)
            subscribeItemsRunning(it)
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
            findNavController().navigateWithCheck(action)
        })
        viewModel.runLiveData.observe(viewLifecycleOwner, Observer { data ->
            startTaskRunningService(data)
        })
        viewModel.showFilterEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_taskList_to_filter)
        })
        viewModel.stopLiveData.observe(viewLifecycleOwner, Observer { stopTaskRunningService() })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { showError(it) })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
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

    private fun subscribeItemsDuration(it: List<Task>) {
        adapter.displayedItems.forEach {
            it.duration.removeObservers(viewLifecycleOwner)
        }
        for ((index, value) in it.withIndex()) {
            value.duration.observe(viewLifecycleOwner, Observer { newDuration ->
                adapter.notifyItemChanged(index, newDuration) })
        }
    }

    private fun subscribeItemsRunning(taskList: List<Task>) {
        adapter.displayedItems.forEach { task ->
            when (task) {
                is StandaloneTask -> {
                    task.timeGaps.removeObservers(viewLifecycleOwner)
                }
                is SteppedTask -> {
                    task.subtasks.forEach { subtask ->
                        subtask.timeGaps.removeObservers(viewLifecycleOwner)
                    }
                }
            }
        }
        for ((index, task) in taskList.withIndex() ) {
            when (task) {
                is StandaloneTask -> {
                    task.timeGaps.observe(viewLifecycleOwner, Observer {
                        adapter.notifyItemChanged(index, task.isRunning()) })
                }
                is SteppedTask -> {
                    task.subtasks.forEach { subtask ->
                        subtask.timeGaps.observe(viewLifecycleOwner, Observer {
                            adapter.notifyItemChanged(index, task.isRunning()) })
                    }
                }
            }
        }
    }

    private fun startTaskRunningService(data: NotificationData) {
        context?.run {
            TaskRunningService.start(this, data.id, data.name)
        }
    }

    private fun stopTaskRunningService() {
        context?.run { TaskRunningService.stop(this) }
    }

    private fun onTaskClicked(position: Int, task: Task) {
        viewModel.onTaskClicked(position, task)
    }

    private fun showError(errorMsgId: Int) {
        Toast.makeText(context, errorMsgId, Toast.LENGTH_LONG).show()
    }
}
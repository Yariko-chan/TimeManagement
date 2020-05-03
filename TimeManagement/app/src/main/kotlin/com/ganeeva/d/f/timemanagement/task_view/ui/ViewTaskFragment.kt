package com.ganeeva.d.f.timemanagement.task_view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.DURATION
import com.ganeeva.d.f.timemanagement.core.TASK_START_TIME
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.isRunning
import com.ganeeva.d.f.timemanagement.task_time_service.NotificationData
import com.ganeeva.d.f.timemanagement.task_time_service.TaskRunningService
import com.ganeeva.d.f.timemanagement.task_view.ui.subtask_list.ViewSubTaskAdapter
import com.ganeeva.d.f.timemanagement.task_view.ui.time_gaps_list.TimeGapAdapter
import kotlinx.android.synthetic.main.fragment_view_task.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat


class ViewTaskFragment : Fragment(R.layout.fragment_view_task) {

    private val args: ViewTaskFragmentArgs by navArgs()
    private val viewModel: ViewTaskViewModel by viewModel()
    private val durationFormat: SimpleDateFormat by inject(named(DURATION))
    private val startTimeFormat by inject<SimpleDateFormat>(named(TASK_START_TIME))
    private val timeGapsAdapter: TimeGapAdapter by lazy {
        TimeGapAdapter(dateFormat = startTimeFormat, durationFormat = durationFormat) }
    private val subTaskAdapter: ViewSubTaskAdapter by lazy {
        ViewSubTaskAdapter(
            durationFormat = durationFormat,
            onCheckedListener = { isChecked, task -> viewModel.onSubTaskChecked(task, isChecked) },
            onDeleteClicked = {subTask -> viewModel.onDeleteSubTaskClicked(subTask)}
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onTaskId(args.taskId)
        setupView()
        subscribeViewModel()
    }

    private fun setupView() {
        setupToolbar()
        setupSubtasksList()
        setupTimeGapsList()
        setupRunCheckBox()
    }

    private fun setupRunCheckBox() {
        run_time_checkbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onRunChecked(isChecked)
        }
        viewModel.isRunningLiveData.observe(viewLifecycleOwner, Observer {
            run_time_checkbox.isChecked = it
        })
    }

    private fun setupToolbar() {
        start_image_view.setImageResource(R.drawable.ic_back)
        start_image_view.setOnClickListener { viewModel.onBackClicked() }
        end_image_view.setImageResource(R.drawable.ic_delete)
        end_image_view.setOnClickListener { viewModel.onRemoveClicked() }
    }

    private fun setupSubtasksList() {
        task_list_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        task_list_recycler_view.adapter = subTaskAdapter
    }

    private fun setupTimeGapsList() {
        time_gaps_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        time_gaps_recycler_view.adapter = timeGapsAdapter
    }

    private fun subscribeViewModel() {
        viewModel.dateLiveData.observe(viewLifecycleOwner, Observer { date_text_view.text = it })
        viewModel.nameLiveData.observe(viewLifecycleOwner, Observer { title_textview.text = it })
        viewModel.descriptionLiveData.observe(
            viewLifecycleOwner,
            Observer { description_text_view.text = it })
        viewModel.durationLiveData.observe(
            viewLifecycleOwner,
            Observer {
                run_time_checkbox.text = it }
        )
        viewModel.isRunEnabledLiveData.observe(viewLifecycleOwner, Observer {
            run_time_checkbox.isEnabled = it
        })
        viewModel.subtasksLiveData.observe(viewLifecycleOwner, Observer {
            subscribeItemsRunning(it)
            subscribeItemsDuration(it)
            subTaskAdapter.updateList(it) })
        viewModel.timeGapsLiveData.observe(viewLifecycleOwner, Observer { timeGapsAdapter.updateList(it) })
        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
        viewModel.runLiveData.observe(viewLifecycleOwner, Observer { data ->
            startTaskRunningService(data)
        })
        viewModel.stopLiveData.observe(viewLifecycleOwner, Observer { stopTaskRunningService() })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { showError(it) })
    }

    private fun startTaskRunningService(data: NotificationData) {
        context?.run {
            TaskRunningService.start(this, data.id, data.name)
        }
    }

    private fun stopTaskRunningService() {
        context?.run { TaskRunningService.stop(this) }
    }

    private fun finish() {
        findNavController().popBackStack()
    }

    private fun showError(errorMsgId: Int) {
        Toast.makeText(context, errorMsgId, Toast.LENGTH_LONG).show()
    }

    private fun subscribeItemsDuration(it: List<SubTask>) {
        subTaskAdapter.displayedItems.forEach {
            it.duration.removeObservers(viewLifecycleOwner)
        }
        for ((index, value) in it.withIndex()) {
            value.duration.observe(viewLifecycleOwner, Observer { newDuration ->
                subTaskAdapter.notifyItemChanged(index, newDuration) })
        }
    }

    private fun subscribeItemsRunning(taskList: List<SubTask>) {
        subTaskAdapter.displayedItems.forEach { task ->
            task.timeGaps.removeObservers(viewLifecycleOwner)
        }
        for ((index, task) in taskList.withIndex() ) {
            task.timeGaps.observe(viewLifecycleOwner, Observer {
                subTaskAdapter.notifyItemChanged(index, task.isRunning()) })
        }
    }
}
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
import com.ganeeva.d.f.timemanagement.task_time_service.NotificationData
import com.ganeeva.d.f.timemanagement.task_time_service.TaskRunningService
import com.ganeeva.d.f.timemanagement.task_view.ui.subtask_list.ViewSubtaskAdapter
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
    private val subtaskAdapter: ViewSubtaskAdapter by lazy { ViewSubtaskAdapter() }
    private val startTimeFormat by inject<SimpleDateFormat>(named(TASK_START_TIME))
    private val durationFormat by inject<SimpleDateFormat>(named(DURATION))
    private val timeGapsAdapter: TimeGapAdapter by lazy {
        TimeGapAdapter(dateFormat = startTimeFormat, durationFormat = durationFormat) }

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
        start_image_view.setOnClickListener { viewModel.onBackCliked() }
        end_image_view.setImageResource(R.drawable.ic_delete)
        end_image_view.setOnClickListener { viewModel.onRemoveClicked() }
    }

    private fun setupSubtasksList() {
        task_list_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        task_list_recycler_view.adapter = subtaskAdapter
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
        viewModel.subtasksLiveData.observe(viewLifecycleOwner, Observer { subtaskAdapter.updateList(it) })
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
}
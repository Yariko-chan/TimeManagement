package com.ganeeva.d.f.timemanagement.task_view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeeva.d.f.timemanagement.R
import kotlinx.android.synthetic.main.fragment_view_task.*
import kotlinx.android.synthetic.main.fragment_view_task.date_text_view
import kotlinx.android.synthetic.main.fragment_view_task.description_text_view
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class ViewTaskFragment : Fragment(R.layout.fragment_view_task) {

    private val args: ViewTaskFragmentArgs by navArgs()
    private val viewModel: ViewTaskViewModel by viewModel()
    private val adapter: ViewSubtaskAdapter by lazy { ViewSubtaskAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onTaskId(args.taskId)
        setupToolbar()
        setupSubtasksList()
        subscribeViewModel()
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
        task_list_recycler_view.adapter = adapter
    }

    private fun subscribeViewModel() {
        viewModel.dateLiveData.observe(viewLifecycleOwner, Observer { date_text_view.text = it })
        viewModel.nameLiveData.observe(viewLifecycleOwner, Observer { title_textview.text = it })
        viewModel.descriptionLiveData.observe(
            viewLifecycleOwner,
            Observer { description_text_view.text = it })
        viewModel.subtasksLiveData.observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
package com.ganeeva.d.f.timemanagement.task_list.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ganeeva.d.f.timemanagement.R
import kotlinx.android.synthetic.main.fragment_task_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class TaskListFragment(): Fragment(R.layout.fragment_task_list) {

    companion object {
        fun newInstance(): TaskListFragment {
            val fragment = TaskListFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val viewModel: TaskListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_new_button.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListToNewTask()
            view.findNavController().navigate(action)
        }

        viewModel.tasksList.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "test", Toast.LENGTH_LONG).show()
        })
    }
}
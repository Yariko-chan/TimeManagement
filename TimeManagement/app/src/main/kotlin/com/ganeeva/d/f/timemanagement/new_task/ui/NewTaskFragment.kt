package com.ganeeva.d.f.timemanagement.new_task.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganeeva.d.f.timemanagement.R
import kotlinx.android.synthetic.main.fragment_new_task.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewTaskFragment: Fragment(R.layout.fragment_new_task) {

    private val viewModel: NewTaskViewModel by viewModel()
    private val addSubtaskDialog: AddSubtaskDialog by lazy {
        AddSubtaskDialog(onOkClicked = { viewModel.onSubtaskDialogText(it) })
    }
    private val subtaskAdapter: SubtaskAdapter by lazy { SubtaskAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            R.string.new_task,
            R.drawable.ic_back,
            R.drawable.ic_ok,
            onStartClicked = { viewModel.onBackCLicked() },
            onEndClicked = { onToolbarOkClicked() })
        add_subtask_image_view.setOnClickListener { viewModel.onAddSubtaskClicked() }
        task_list_recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        task_list_recycler_view.adapter = subtaskAdapter

        viewModel.nameErrorLiveData.observe(viewLifecycleOwner, Observer { name_edittext.setError(it) })
        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { showError(it) })
        viewModel.showSubtaskDialogEvent.observe(viewLifecycleOwner, Observer {
            addSubtaskDialog.show(childFragmentManager, "") })
        viewModel.subtaskListLiveData.observe(viewLifecycleOwner, Observer {
            subtaskAdapter.updateList(it)
        })
    }

    private fun onToolbarOkClicked() {
        viewModel.onOkClicked(name_edittext.text.toString(), description_edittext.text.toString())
    }

    private fun setupToolbar(
        @StringRes title: Int,
        @DrawableRes startIcon: Int = R.drawable.ic_back,
        @DrawableRes endIcon: Int? = null,
        onStartClicked: ((View) -> Unit)? = null,
        onEndClicked: ((View) -> Unit)? = null
    ) {
        title_textview.setText(title)
        start_image_view.setImageResource(startIcon)
        if (endIcon == null) {
            end_image_view.visibility = View.GONE
        } else {
            end_image_view.setImageResource(endIcon)
        }
        onStartClicked?.let { start_image_view.setOnClickListener(it) }
        onEndClicked?.let{ end_image_view.setOnClickListener(it) }
    }

    private fun showError(@StringRes messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_LONG).show()
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
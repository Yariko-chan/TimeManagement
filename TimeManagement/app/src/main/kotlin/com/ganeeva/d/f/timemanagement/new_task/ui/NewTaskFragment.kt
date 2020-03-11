package com.ganeeva.d.f.timemanagement.new_task.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ganeeva.d.f.timemanagement.R
import kotlinx.android.synthetic.main.fragment_new_task.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewTaskFragment: Fragment(R.layout.fragment_new_task) {

    private val viewModel: NewTaskViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            R.string.new_task,
            onStartClicked = { viewModel.onBackCLicked() },
            onEndClicked = { viewModel.onOkClicked(name_edittext.text.toString(), description_edittext.text.toString()) })
    }

    private fun setupToolbar(
        @StringRes title: Int,
        @DrawableRes startIcon: Int = R.drawable.ic_back,
        @DrawableRes endIcon: Int = R.drawable.ic_ok,
        onStartClicked: ((View) -> Unit)? = null,
        onEndClicked: ((View) -> Unit)? = null
    ) {
        title_textview.setText(title)
        start_image_view.setImageResource(startIcon)
        end_image_view.setImageResource(endIcon)
        onStartClicked?.let { start_image_view.setOnClickListener(it) }
        onEndClicked?.let{ end_image_view.setOnClickListener(it) }

        viewModel.finishLiveData.observe(viewLifecycleOwner, Observer { finish() })
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
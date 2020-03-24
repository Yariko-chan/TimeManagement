package com.ganeeva.d.f.timemanagement.new_task.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ganeeva.d.f.timemanagement.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_add_subtask.*

class AddSubtaskDialog(
    private val onOkClicked: (String) -> Unit
    ): DialogFragment() {

    private lateinit var contentView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_add_subtask, null, false)
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.add_subtask)
            .setView(contentView)
            .setPositiveButton(android.R.string.ok) { _, _ -> onPositiveClick() }
            .create()
    }

    private fun onPositiveClick() {
        val text = edit_text.text.toString()
        onOkClicked(text)
        edit_text.setText("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView
    }
}
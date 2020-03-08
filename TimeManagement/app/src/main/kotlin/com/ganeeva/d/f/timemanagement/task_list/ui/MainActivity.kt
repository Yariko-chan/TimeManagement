package com.ganeeva.d.f.timemanagement.task_list.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ganeeva.d.f.timemanagement.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.tasksList.observe(this, Observer {
            Toast.makeText(this, "test", Toast.LENGTH_LONG).show()
        })
    }
}

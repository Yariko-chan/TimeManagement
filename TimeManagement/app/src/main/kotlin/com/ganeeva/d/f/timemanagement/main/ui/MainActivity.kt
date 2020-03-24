package com.ganeeva.d.f.timemanagement.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ganeeva.d.f.timemanagement.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val TASK_ID = "TASK_ID"
    }
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

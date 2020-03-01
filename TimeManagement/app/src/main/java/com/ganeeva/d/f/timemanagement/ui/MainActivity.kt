package com.ganeeva.d.f.timemanagement.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.db.DbTempRepository
import com.ganeeva.d.f.timemanagement.db.task.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val dbTempRepository = DbTempRepository()

    // to cancel all the jobs in ondestroy
    private var viewModelJob = Job()
    // The scope determines what thread the coroutine will run on,
    // and the scope also needs to know about the job.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var task = MutableLiveData<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.setOnClickListener {
            initialize()
        }
    }

    private fun initialize() {
        uiScope.launch {
            val task = getFromDatabase()
        }
    }

    private suspend fun getFromDatabase(): Task? {
        return withContext(Dispatchers.IO) {
            val newTask = Task(name = " test", description = "description")
            val result = dbTempRepository.saveTask(newTask)
            val task = dbTempRepository.getTask(result)
            task
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelJob.cancel()
    }
}

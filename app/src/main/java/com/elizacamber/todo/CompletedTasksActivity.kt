package com.elizacamber.todo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompletedTasksActivity : AppCompatActivity(R.layout.activity_completed_tasks) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvCompleted = findViewById<TextView>(R.id.tv_completed)
        tvCompleted.text = completedTasks.toString()
    }
}
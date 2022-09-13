package com.elizacamber.todo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActiveTasksActivity : AppCompatActivity(R.layout.activity_active_tasks) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvActive = findViewById<TextView>(R.id.tv_active)
        tvActive.text = activeTasks.toString()
    }
}
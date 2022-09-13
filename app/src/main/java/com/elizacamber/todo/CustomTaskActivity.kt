package com.elizacamber.todo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CustomTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_task)

        val tvCustom = findViewById<TextView>(R.id.tv_custom)
        tvCustom.text = intent.getStringExtra("title") ?: "oops"
    }
}
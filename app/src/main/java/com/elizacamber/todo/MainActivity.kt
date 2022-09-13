package com.elizacamber.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

enum class TaskType(val nameId: Int) {
    //    UNKNOWN(R.string.label_unknown),
    ACTIVE(R.string.label_active),
    COMPLETED(R.string.label_completed);

    companion object {

        /**
         * @return a TaskType that matches the given name
         */
        fun find(type: String?): TaskType? {
            return values().find { it.name.equals(other = type, ignoreCase = true) }
        }
    }
}

const val SHORTCUT_TASK_TYPE_PARAM_NAME = "task.type"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btActive = this.findViewById<Button>(R.id.bt_active)
        val btCompleted = this.findViewById<Button>(R.id.bt_completed)

        btActive.setOnClickListener { startActiveActivity() }
        btCompleted.setOnClickListener { startCompletedActivity() }

        intent.handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent.handleIntent()
    }

    private fun startActiveActivity() {
        Log.d("MainActivity.kt", "startActiveActivity: ")
        val intent = Intent(this, ActiveTasksActivity::class.java)
        startActivity(intent)
    }

    private fun startCompletedActivity() {
        Log.d("MainActivity.kt", "startCompletedActivity: ")
        val intent = Intent(this, CompletedTasksActivity::class.java)
        startActivity(intent)
    }

    private fun Intent?.handleIntent() {
        if (this == null) return
        when (action) {
            Intent.ACTION_VIEW -> {
                val taskType = intent?.extras?.getString(SHORTCUT_TASK_TYPE_PARAM_NAME)
                when (TaskType.find(taskType)) {
                    TaskType.ACTIVE -> startActiveActivity()
                    TaskType.COMPLETED -> startCompletedActivity()
                    null -> return
                }
            }
            else -> return
        }
    }

}
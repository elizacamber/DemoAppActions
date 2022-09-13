package com.elizacamber.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat

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

val activeTasks = mutableListOf("active 0", "active 1", "active 2", "active 3")
val completedTasks = mutableListOf("completed 0", "completed 1", "completed 2", "completed 3")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btActive = this.findViewById<Button>(R.id.bt_active)
        val btCompleted = this.findViewById<Button>(R.id.bt_completed)
        val btNew = this.findViewById<Button>(R.id.bt_new)

        btActive.setOnClickListener { startActiveActivity() }
        btCompleted.setOnClickListener { startCompletedActivity() }
        btNew.setOnClickListener { pushNewTask() }

        intent.handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent.handleIntent()
    }

    private fun startActiveActivity() {
        val intent = Intent(this, ActiveTasksActivity::class.java)
        startActivity(intent)
    }

    private fun startCompletedActivity() {
        val intent = Intent(this, CompletedTasksActivity::class.java)
        startActivity(intent)
    }

    private fun startCustomActivity(title: String?) {
        val intent = Intent(this, CustomTaskActivity::class.java).apply { putExtra("title", title) }
        startActivity(intent)
    }

    private fun pushNewTask() {
        val title = "dummy"
        activeTasks.add(title)
        pushShortcut(title)
    }

    private fun pushShortcut(title: String) {
        // Define the dynamic shortcut for the new task
        val intent = Intent(this, CustomTaskActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        val shortcutInfo = ShortcutInfoCompat.Builder(this, "aUniqueId")
            .setShortLabel(title)
            .setLongLabel("Your todo named $title")
            .addCapabilityBinding(
                "actions.intent.GET_THING", "thing.name", listOf(title)
            )
            .setIntent(intent) // Push the shortcut
            .build()

        // Push the shortcut
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcutInfo)
    }

    private fun Intent?.handleIntent() {
        if (this == null) return
        when (action) {
            Intent.ACTION_VIEW -> {
                val param = intent?.extras?.getString(SHORTCUT_TASK_TYPE_PARAM_NAME)
                Log.d("MainActivity.kt", "handleIntent: $param")
                val taskType = TaskType.find(param)
                when (taskType) {
                    TaskType.ACTIVE -> startActiveActivity()
                    TaskType.COMPLETED -> startCompletedActivity()
                    null -> {
                        if (activeTasks.contains(param) || completedTasks.contains(param)) {
                            startCustomActivity(param)
                        }
                    }
                }
            }
            else -> return
        }
    }
}
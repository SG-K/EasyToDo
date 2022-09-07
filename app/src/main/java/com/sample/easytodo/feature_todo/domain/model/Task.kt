package com.sample.easytodo.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title : String,
    val description : String,
    val timeStamp : Long? = null,
    val status : TASK_STATUS
)

enum class TASK_STATUS{
    PENDING, IN_PROGRESS, DONE, NONE
}

class InvalidTaskException(message: String): Exception(message)

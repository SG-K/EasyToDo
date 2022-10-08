package com.sample.easytodo.backup

import android.content.Context
import android.widget.Toast
import com.sample.easytodo.feature_todo.data.data_source.EasyToDoDatabase

fun Context.backupDb(easyToDoDatabase: EasyToDoDatabase){
    easyToDoDatabase.close()
    val dbFile = getDatabasePath(EasyToDoDatabase.DATABASE_NAME)
    Toast.makeText(this,"Size = ${dbFile.length()}", Toast.LENGTH_LONG).show()
}
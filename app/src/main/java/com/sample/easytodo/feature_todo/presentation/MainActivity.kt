package com.sample.easytodo.feature_todo.presentation

import android.R.attr.bitmap
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.easytodo.backup.CONST_SIGN_IN
import com.sample.easytodo.backup.GoogleSignInInApp
import com.sample.easytodo.backup.backupDb
import com.sample.easytodo.backup.handleSignData
import com.sample.easytodo.feature_todo.data.data_source.EasyToDoDatabase
import com.sample.easytodo.feature_todo.presentation.add_update_todo.components.AddUpdateTaskScreen
import com.sample.easytodo.feature_todo.presentation.list_todo.TasksScreen
import com.sample.easytodo.feature_todo.presentation.util.Screen
import com.sample.easytodo.feature_todo.presentation.util.print
import com.sample.easytodo.ui.theme.EasyToDoTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var roomDatabase: EasyToDoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyToDoTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Row {
                            Button(onClick = { triggerBridge() }) {
                                Text(text = "LOGIB", modifier = Modifier.padding(10.dp))
                            }
                            Button(onClick = { saveData() }) {
                                Text(text = "SAVE", modifier = Modifier.padding(10.dp))
                            }
                            Button(onClick = { retriveData() }) {
                                Text(text = "LOAD", modifier = Modifier.padding(10.dp))
                            }
                            Button(onClick = { backupDb(roomDatabase) }) {
                                Text(text = "CLEAR", modifier = Modifier.padding(10.dp))
                            }
                        }
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.TasksScreen.route
                        ) {
                            composable(route = Screen.TasksScreen.route) {
                                TasksScreen(navController = navController)
                            }
                            composable(
                                route = Screen.AddUpdateTaskScreen.route +
                                        "?taskId={taskId}",
                                arguments = listOf(
                                    navArgument(
                                        name = "taskId"
                                    ) {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    }
                                )
                            ) {
                                AddUpdateTaskScreen(
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun saveData(){
        roomDatabase.close()
        val dbFile = getDatabasePath(EasyToDoDatabase.DATABASE_NAME)
        val sampledata = "Size = ${dbFile.length()} " +
                "\n name = ${dbFile.name} " +
                "\n mimetype = ${mimeTypeExtractor(dbFile)} " +
                "\n  absoluteFile = ${dbFile.absoluteFile}"
        Toast.makeText(this,sampledata, Toast.LENGTH_LONG).show()
        sampledata.print()
        val cacheDir = getCacheDir()
        "Saving cachedir = $cacheDir".print()
        val file2 = File(cacheDir, EasyToDoDatabase.DATABASE_NAME)
        copy(src = dbFile, dst = file2)
        "file2 path = ${file2.path}".print()
        "file2 size = ${file2.length()}".print()
    }

    fun retriveData(){
        val cacheDir = cacheDir
        "cacheDir = $cacheDir/${EasyToDoDatabase.DATABASE_NAME}".print()
        val file : File = File("$cacheDir/${EasyToDoDatabase.DATABASE_NAME}")
        Toast.makeText(this,"Size = ${file.length()}", Toast.LENGTH_LONG).show()
        restoreDatabase(file)
    }

    fun restoreDatabase(file: File) {
        val db = roomDatabase
        db.close()
        val oldDB = getDatabasePath(EasyToDoDatabase.DATABASE_NAME)
        copy(src = file, dst = oldDB)
    }

    fun mimeTypeExtractor(file: File) : String{
        val uri: Uri = Uri.fromFile(file)
        val mime = contentResolver.getType(uri)
        return mime?:""
    }

    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    fun getDocumentCacheDir(context: Context): File {
        val dir = File(context.cacheDir, "documents")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        logDir(context.cacheDir)
        logDir(dir)
        return dir
    }

    private fun logDir(dir: File) {
        "Dir=$dir".print()
        val files = dir.listFiles()
        for (file: File in files) {
            "File=" + file.path.print()
        }
    }

    fun triggerBridge(){
        GoogleSignInInApp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == CONST_SIGN_IN) {

            handleSignData(data)

        }
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasyToDoTheme {
        Greeting("Android")
    }
}
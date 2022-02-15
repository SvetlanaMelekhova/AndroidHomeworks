package by.htp.first.homework8_1.database

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DatabaseRepository (context: Context){

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val database = ContactDatabase.init(context)
    private val threadIO = Dispatchers.IO

    fun mainScope() = mainScope

}
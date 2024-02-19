package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.dao.TaskDao
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.model.Task
import com.example.todoapp.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao: TaskDao
    val allTasks: LiveData<List<Task>>

    private val titleLiveData = MutableLiveData<String>()
    private val descriptionLiveData = MutableLiveData<String>()
    private val dateLiveData = MutableLiveData<String>()

    private val sessionManager = SessionManager(getApplication<Application?>().applicationContext)

    init {
        val database = AppDatabase.getInstance(application)
        taskDao = database.taskDao()
        //allTasks = taskDao.getAllTasks()
        val sharedPrefUserId = sessionManager.readIdUser()
        allTasks = taskDao.getAllTasksByIdUser(sharedPrefUserId)
    }

    fun getTitleLiveData(): MutableLiveData<String> {
        return titleLiveData
    }

    fun getDescriptionLiveData(): MutableLiveData<String> {
        return descriptionLiveData
    }

    fun getDateLiveData(): MutableLiveData<String> {
        return dateLiveData
    }


    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateTask(task)
        }
    }

    fun updateTaskById(id: Int, title: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateTaskById(id, title, description)
        }
    }

    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteTask(task)
        }
    }

}
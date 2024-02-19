package com.example.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Task


@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId")
    fun getAllTasksByIdUser(userId: Int): LiveData<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("UPDATE tasks SET title = :title, description = :description WHERE id = :id")
    fun updateTaskById(id: Int, title: String, description: String)

    @Delete
    fun deleteTask(task: Task)

}
package com.example.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.roomDb.ToDo

@Dao
interface ToDoDao {

    @Insert
    suspend fun insertToDo(toDo: ToDo) : Long

    @Query("SELECT * FROM todo_table")
    fun getToDo() : LiveData<List<ToDo>>

    @Update
    suspend fun updateToDo(toDo: ToDo) : Int

    @Delete
    suspend fun deletesingleToDo(toDo: ToDo) : Int

//    @Query("DELETE FROM todo_table")
//    suspend fun deleteAllToDo() : Int
}
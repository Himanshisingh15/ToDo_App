package com.example.todoapp.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.dao.ToDoDao


@Database(entities = [ToDo::class], version = 1)
  abstract class ToDoDb : RoomDatabase(){
    abstract val toDoDao : ToDoDao

    companion object{
        @Volatile
        private var INSTANCE: ToDoDb? = null
        fun getInstance(context : Context): ToDoDb{
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(context, ToDoDb::class.java,"todo_table").build()
            }
            return instance
        }
    }
}
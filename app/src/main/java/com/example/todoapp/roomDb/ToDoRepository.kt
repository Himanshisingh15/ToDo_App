package com.example.todoapp.roomDb

import androidx.lifecycle.LiveData
import com.example.todoapp.dao.ToDoDao

class ToDoRepository(private var toDoDao : ToDoDao) {

    var todoData : LiveData<List<ToDo>> = toDoDao.getToDo()

    suspend fun insertToDo(toDo : ToDo) : Long{
             return toDoDao.insertToDo(toDo)
    }

    suspend fun updateToDo(toDo: ToDo) : Int{
        return toDoDao.updateToDo(toDo)
    }

    suspend fun deleteToDo(toDo: ToDo) : Int{
        return toDoDao.deletesingleToDo(toDo)
    }
}
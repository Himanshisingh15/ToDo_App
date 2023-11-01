package com.example.todoapp.roomDb

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ToDoFactory(private var toDoRepository: ToDoRepository, var context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)){
            return ToDoViewModel(toDoRepository, context) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}
package com.example.todoapp

import com.example.todoapp.roomDb.ToDo

interface EditClickListener {
    fun editTodo(position : Int, List : ToDo)
}
package com.example.todoapp

import com.example.todoapp.roomDb.ToDo

interface DeleteClickListener {
    fun deleteTodo(position : Int, List : ToDo)
}
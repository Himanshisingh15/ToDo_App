package com.example.todoapp.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Row Id")
    var id : Int,

    @ColumnInfo(name = "ToDo Title")
    var todoTitle : String,

    @ColumnInfo(name = "ToDo Description")
    var todoDescription : String
)

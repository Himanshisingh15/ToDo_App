package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.todoapp.databinding.CustomTodoItemBinding
import com.example.todoapp.DeleteClickListener
import com.example.todoapp.EditClickListener
import com.example.todoapp.roomDb.ToDo

class RecyclerViewAdapter(var list: List<ToDo>, var deleteClickListener: DeleteClickListener, var editClickListener: EditClickListener) : Adapter<RecyclerViewAdapter.MyViewHolder>(){

    inner class MyViewHolder(val binding: CustomTodoItemBinding) : RecyclerView.ViewHolder(binding!!.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding: CustomTodoItemBinding = CustomTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var todoItem =list[position]
        holder.binding.todoTitle.text = todoItem.todoTitle
        holder.binding.todoDescription.text = todoItem.todoDescription
        holder.binding.icDelete.setOnClickListener {
            deleteClickListener.deleteTodo(position,todoItem)
        }
        holder.binding.icEdit.setOnClickListener {
            editClickListener.editTodo(position,todoItem)
        }
    }

}
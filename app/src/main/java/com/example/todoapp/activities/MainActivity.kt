package com.example.todoapp.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.DeleteClickListener
import com.example.todoapp.EditClickListener
import com.example.todoapp.R
import com.example.todoapp.adapter.RecyclerViewAdapter
import com.example.todoapp.roomDb.ToDoDb
import com.example.todoapp.roomDb.ToDoFactory
import com.example.todoapp.roomDb.ToDoRepository
import com.example.todoapp.roomDb.ToDoViewModel
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.TodoDialogBinding
import com.example.todoapp.roomDb.ToDo

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: ToDoViewModel
    lateinit var toDoAdapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var toDoDao = ToDoDb.getInstance(this).toDoDao
        var toDoRepository = ToDoRepository(toDoDao)
        viewModel = ViewModelProvider(this, ToDoFactory(toDoRepository,this))[ToDoViewModel::class.java]

        binding.lifecycleOwner = this
        binding.todoViewModel = viewModel

        viewModel.statusMessage.observe(this, Observer {
            it.getContentIfNotHandled()!!.apply {
                Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show()
            }
        })

        var dialogBinding: TodoDialogBinding = TodoDialogBinding.inflate(LayoutInflater.from(this))
        var dialog = Dialog(this@MainActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        var layoutManager = WindowManager.LayoutParams()
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutManager

        dialogBinding.todoViewModel = viewModel
        dialogBinding.lifecycleOwner = this
        dialog.setCancelable(false)


        viewModel.dialog.observe(this, Observer {
            if (it == true){
                dialog.show()
            }else {
                dialog.dismiss()
            }
        })

        binding.recyclerListItem.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        viewModel.todoData.observe(this, Observer {
            toDoAdapter = RecyclerViewAdapter(it, object : DeleteClickListener{
                override fun deleteTodo(position: Int, List: ToDo) {
                    alertMessage(List)
                }
                }, object : EditClickListener{
                override fun editTodo(position: Int, List: ToDo) {
                    viewModel.updateTodo(List)
                }

            })
            binding.recyclerListItem.adapter = toDoAdapter
            toDoAdapter.notifyDataSetChanged()
        })

    }

    fun alertMessage(toDo: ToDo){
        var appname = applicationInfo.loadLabel(packageManager).toString()
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("$appname")
        alertDialog.setMessage("Are you sure to delete your note!!")

        alertDialog.setPositiveButton("YES", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                viewModel.deleteTodo(toDo)
                dialog!!.dismiss()
            }
        })
        alertDialog.setNegativeButton("NO", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
            }
        })
        alertDialog.show()
    }

}
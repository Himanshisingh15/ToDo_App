package com.example.todoapp.roomDb

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.activities.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoViewModel(private var toDoRepository: ToDoRepository, private var context: Context) : ViewModel() {

    var inputTitle: MutableLiveData<String> = MutableLiveData()
    var inputDescription: MutableLiveData<String> = MutableLiveData()
    var okEdit: MutableLiveData<String> = MutableLiveData()
    var cancel: MutableLiveData<String> = MutableLiveData()
    var dialog: MutableLiveData<Boolean> = MutableLiveData()
    var editTodo: MutableLiveData<Boolean> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData()
    var rowId: MutableLiveData<Int> = MutableLiveData()

    var todoData: LiveData<List<ToDo>> = toDoRepository.todoData
    var statusMessage: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()

    init {
        dialog.value = false
        okEdit.value = "OK"
        cancel.value = "CANCEL"
        inputTitle.value = ""
        inputDescription.value = ""
        title.value = "CREATE"
        rowId.value = 0
        editTodo.value = false

    }

    fun insertOrUpdateToDo() {
        if (inputTitle.value!!.isEmpty() || inputDescription.value!!.isEmpty()) {
            statusMessage.value = Event("Fields are Mandatory")
        } else {
            if (editTodo.value!!) {
                viewModelScope.launch(Dispatchers.IO) {
                    var updatedRowId = toDoRepository.updateToDo(
                        ToDo(
                            rowId.value!!.toInt(),
                            inputTitle.value!!.toString(),
                            inputDescription.value!!.toString()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        if (updatedRowId > 0) {
                            statusMessage.value = Event("Successfully Updated")
                            inputTitle.value = ""
                            inputDescription.value = ""
                            editTodo.value = false
                            dialog.value = false
                        } else {
                            statusMessage.value = Event("Something went wrong...")
                            dialog.value = false
                        }
                    }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    var rowId = toDoRepository.insertToDo(
                        ToDo(
                            0,
                            inputTitle.value!!.toString(),
                            inputDescription.value!!.toString()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        if (rowId > 0) {
                            statusMessage.value = Event("Successfully Inserted $rowId row")
                            inputTitle.value = ""
                            inputDescription.value = ""
                            dialog.value = false
                        } else {
                            statusMessage.value = Event("Some error occured")
                            dialog.value = false
                        }
                    }
                }
            }
        }
    }

        fun showDialog() {
            dialog.value = true
            okEdit.value = "OK"
            cancel.value = "CANCEL"
            inputTitle.value = ""
            inputDescription.value = ""

        }

        fun cancelDialog() {
            dialog.value = false
        }

        fun updateTodo(toDo: ToDo) {
            dialog.value = true
            title.value = "UPDATE"
            okEdit.value = "EDIT"
            cancel.value = "CANCEL"
            rowId.value = toDo.id
            inputTitle.value = toDo.todoTitle
            inputDescription.value = toDo.todoDescription
            editTodo.value = true
        }

        fun deleteTodo(toDo: ToDo) {
            viewModelScope.launch(Dispatchers.IO) {
                var deleteRow = toDoRepository.deleteToDo(toDo)
                withContext(Dispatchers.Main) {
                    if (deleteRow > 0) {
                        statusMessage.value = Event("Deleted Successfully")
                    } else {
                        statusMessage.value = Event("Something went Wrong")
                    }
                }
            }
        }
}
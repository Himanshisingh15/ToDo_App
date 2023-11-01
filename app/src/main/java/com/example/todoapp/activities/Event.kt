package com.example.todoapp.activities

class Event<out T>(private val statusMessage : T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() : T? {
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            statusMessage
        }
    }
}
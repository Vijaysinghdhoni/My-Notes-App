package com.vijaydhoni.mynotesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myNotesDataBase.NotesRepo

class MyViewModelFactory(private val repository: NotesRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown Viewmodel Class")
    }

}
package com.vijaydhoni.mynotesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myNotesDataBase.Note
import myNotesDataBase.NotesRepo
import java.text.SimpleDateFormat
import java.util.*

class MyViewModel(private val myrepo: NotesRepo) : ViewModel() {

    val allNotes = myrepo.allNotes

    val inputNoteTitle = MutableLiveData<String>()
    val inputNotediscrip = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage


    var isUpdate = false


    fun saveOrUpdateNote(id: Int): Boolean {

        if (inputNoteTitle.value == null) {
            statusMessage.value = Event("Please Enter Note Title")
            return false
        } else if (inputNotediscrip.value == null) {
            statusMessage.value = Event("Please Enter Note Discription")
            return false
        } else {
            val title = inputNoteTitle.value!!
            val discription = inputNotediscrip.value!!
            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
            val currentDateAndTime: String = sdf.format(Date())
            if (isUpdate) {
                updateNotes(Note(id, title, discription, currentDateAndTime))
            } else {
                insertNotes(Note(0, title, discription, currentDateAndTime))
            }
            return true
        }

    }


    fun insertNotes(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            myrepo.insertNotes(note)
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Your Notes is saved Succesfully  ")
            }

        }
    }

    fun updateNotes(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            myrepo.updateNotes(note)
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Your Notes is Updated Succesfully  ")
            }

        }
    }

    fun delteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            myrepo.deleteNotes(note)
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("Your Notes is Deleted Succesfully  ")
            }

        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            myrepo.deleteAllNotes()
            withContext(Dispatchers.Main) {
                statusMessage.value = Event("All Notes Are Delted Succesfully  ")
            }
        }
    }

    fun init(title: String, discrip: String) {
        inputNoteTitle.value = title
        inputNotediscrip.value = discrip
        isUpdate = true

    }


}
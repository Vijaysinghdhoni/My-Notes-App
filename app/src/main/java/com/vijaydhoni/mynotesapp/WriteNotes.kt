package com.vijaydhoni.mynotesapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.mynotesapp.databinding.ActivityWriteNotesBinding
import myNotesDataBase.NotesDB
import myNotesDataBase.NotesRepo

class WriteNotes : AppCompatActivity() {
    private lateinit var binding: ActivityWriteNotesBinding
    private lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_notes)
        val dao = NotesDB.getInstance(applicationContext).dao
        val repo = NotesRepo(dao)
        val factory = MyViewModelFactory(repo)
        myViewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.myViewModel = myViewModel
        binding.lifecycleOwner = this

        myViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })


        val noteType = intent.getStringExtra("noteType")
        var noteID = -1
        if (noteType.equals("Edit")) {

            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            binding.saveBttn.text = getString(R.string.update_bttn)
            myViewModel.init(noteTitle.toString(), noteDescription.toString())
        } else {
            binding.saveBttn.text = getString(R.string.save_bttn)
        }

        binding.saveBttn.setOnClickListener {

            /* val noteTile = binding.notesTitle.text.toString()
             val noteDis = binding.notesDiscription.text.toString()

             if (noteType.equals("Edit")) {
                 myViewModel.updateNotes(Note(noteID, noteTile, noteDis, getCurrentDateTime()))
             } else {
                 myViewModel.saveOrUpdateNote()
             }*/
            val input = myViewModel.saveOrUpdateNote(noteID)

            if (input) {
                finish()
            }

        }


    }

    /*  private fun getCurrentDateTime(): String {
          val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
          return sdf.format(Date())
      }*/
}
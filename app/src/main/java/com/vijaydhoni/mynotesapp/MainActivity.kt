package com.vijaydhoni.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.mynotesapp.databinding.ActivityMainBinding
import myNotesDataBase.Note
import myNotesDataBase.NotesDB
import myNotesDataBase.NotesRepo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var adapter: MyRecylerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = NotesDB.getInstance(applicationContext).dao
        val repo = NotesRepo(dao)
        val factory = MyViewModelFactory(repo)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
        binding.addNoteBttn.setOnClickListener {
            val intent = Intent(this, WriteNotes::class.java)
            startActivity(intent)
        }
        setRecylerView()

        myViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_all) {
            myViewModel.deleteAll()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }

    }

    private fun setRecylerView() {
        adapter =
            MyRecylerViewAdapter({ selectedItem: Note -> listItemClicked2(selectedItem) }) { selectedDeltedItem: Note ->
                listItemClicked(
                    selectedDeltedItem
                )
            }
        binding.myRecyclerView.adapter = adapter
        setRecycle()
    }

    private fun setRecycle() {
        myViewModel.allNotes.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()

        })
    }


    private fun listItemClicked(note: Note) {
        myViewModel.delteNote(note)
    }

    private fun listItemClicked2(note: Note) {
        val intent = Intent(this, WriteNotes::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.discription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)

    }
}
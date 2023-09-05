package com.vijaydhoni.mynotesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vijaydhoni.mynotesapp.databinding.ListItemBinding
import myNotesDataBase.Note

class MyRecylerViewAdapter(
    private val clickListner2: (Note) -> Unit,
    private val clickListner: (Note) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val noteList = ArrayList<Note>()

    fun setList(List: List<Note>) {
        noteList.clear()
        noteList.addAll(List)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noteList[position], clickListner, clickListner2)
    }
}

class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, clickListner: (Note) -> Unit, clickListner2: (Note) -> Unit) {
        binding.notesTitle.text = note.title
        binding.dateTime.text = note.currentDateTime
        binding.deleteBttn.setOnClickListener {
            clickListner(note)
        }
        binding.linearItemView.setOnClickListener {
            clickListner2(note)
        }
    }

}
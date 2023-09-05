package myNotesDataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNotes(note: Note)

    @Update
    suspend fun updateNotes(note: Note)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Query("DELETE FROM My_Notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM My_Notes_table")
    fun getAllNotes() : LiveData<List<Note>>


}
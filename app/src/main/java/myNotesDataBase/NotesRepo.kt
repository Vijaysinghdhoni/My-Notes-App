package myNotesDataBase

class NotesRepo(private val dao: NotesDao) {

    val allNotes = dao.getAllNotes()


    suspend fun insertNotes(note: Note) {
        dao.insertNotes(note)
    }

    suspend fun updateNotes(note: Note) {
        dao.updateNotes(note)
    }

    suspend fun deleteNotes(note: Note) {
        dao.deleteNotes(note)
    }

    suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

}
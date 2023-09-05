package myNotesDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "My_Notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int,

    @ColumnInfo(name = "Notes_Title")
    var title: String,

    @ColumnInfo(name = "Notes_Discription")
    var discription: String,

    @ColumnInfo(name = "Notes_current_date_time")
    var currentDateTime: String

)

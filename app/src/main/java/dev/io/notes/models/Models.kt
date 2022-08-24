package dev.io.notes.models

import androidx.room.*
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var description: String,
    var updatedOn : Long
){
    @PrimaryKey
    var noteId: String = UUID.randomUUID().toString()
}


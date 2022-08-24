package dev.io.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.io.notes.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    suspend fun getNoteById(id: String): Note?

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    fun getNoteByIdByFlow(id: String): Flow<Note?>

    @Query("SELECT * FROM notes_table ORDER BY updatedOn DESC")
    fun getAllNotesSorted(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table")
    fun getAllNotesByFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): List<Note>

    @Query("DELETE FROM notes_table WHERE noteId = :id")
    suspend fun deleteNote(id: String)

    @Query("DELETE FROM notes_table")
    suspend fun deleteTable()
}
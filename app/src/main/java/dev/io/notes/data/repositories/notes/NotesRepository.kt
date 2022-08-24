
package dev.io.notes.data.repositories.notes

import dev.io.notes.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun create(note: Note)

    suspend fun getNote(noteId: String): Note?

    suspend fun delete(noteId: String)

    fun observeNotes(): Flow<List<Note>>

    fun getNoteByFlow(noteId: String): Flow<Note?>

    suspend fun sortNotes(): Flow<List<Note>>
}
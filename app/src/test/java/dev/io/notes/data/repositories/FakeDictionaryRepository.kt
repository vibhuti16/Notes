package dev.io.notes.data.repositories

import dev.io.notes.data.repositories.notes.NotesRepository
import dev.io.notes.models.Note
import kotlinx.coroutines.flow.Flow


class FakeNotesRepository: NotesRepository {
    override suspend fun create(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNote(noteId: String): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(noteId: String) {
        TODO("Not yet implemented")
    }

    override fun observeNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override fun getNoteByFlow(noteId: String): Flow<Note?> {
        TODO("Not yet implemented")
    }

    override suspend fun sortNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

}

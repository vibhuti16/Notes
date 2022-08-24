
package dev.io.notes.data.repositories.notes.impl

import dev.io.notes.data.local.NoteDao
import dev.io.notes.data.repositories.notes.NotesRepository
import dev.io.notes.models.Note
import dev.io.notes.ui.screens.home.DEFAULT

class NotesRepositoryImpl(
    private val notesDao: NoteDao
) : NotesRepository {

    override suspend fun create(note: Note) {
        notesDao.insert(note = note)
    }

    override suspend fun getNote(noteId: String) = notesDao.getNoteById(noteId)

    override suspend fun delete(noteId: String) {
        notesDao.deleteNote(noteId)
    }

    override fun observeNotes() = notesDao.getAllNotesByFlow()
//    override fun getNotesBySearch(query: String) = notesDao.getNotesBySearch(query)

    override fun getNoteByFlow(noteId: String) = notesDao.getNoteByIdByFlow(noteId)

    override suspend fun sortNotes() = notesDao.getAllNotesSorted()
}
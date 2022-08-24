package dev.io.notes.ui.screens.note.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.io.notes.data.repositories.local.LocalRepository
import dev.io.notes.data.repositories.notes.NotesRepository
import dev.io.notes.models.Note
import dev.io.notes.ui.screens.home.DEFAULT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*



data class NoteUiState(
    val note: Note = getEmptyNote(),
    val loading: Boolean = false,
)

fun getEmptyNote(id: String? = null) = Note(DEFAULT, "",Calendar.getInstance().timeInMillis).apply {
    if (id != null) this.noteId = id
}


class NoteViewModel(
    private val notesRepository: NotesRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(NoteUiState(loading = true))
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.currentNote.collect { id ->
                notesRepository.getNoteByFlow(id).collect { note ->
                    val newNote = note ?: getEmptyNote(localRepository.currentNote.value)
                    _uiState.update { it.copy(note = newNote) }
                }
            }
        }
    }

    fun setSelectedImage(pos: Int) {
        viewModelScope.launch {
            localRepository.saveSelectedPosition(pos)
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            notesRepository.create(note.apply {
                updatedOn = System.currentTimeMillis()
            })
        }

    }


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.delete(note.noteId)
            localRepository.saveCurrentNote()
        }
    }


}
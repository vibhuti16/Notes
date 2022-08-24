package dev.io.notes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.io.notes.data.repositories.local.LocalRepository
import dev.io.notes.data.repositories.notes.NotesRepository
import dev.io.notes.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val notes: List<Note> = emptyList(),
    val loading: Boolean = true
){ val isEmpty  = !loading && notes.isEmpty() }


const val DEFAULT = "default"

class HomeViewModel(
    private val localRepository: LocalRepository,
    private val notesRepository: NotesRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        // Observe for Notes changes in the repo layer
        viewModelScope.launch {
            notesRepository.observeNotes().collect { notes ->
                _uiState.update { it.copy(notes = notes,false) }
            }
        }
    }

    fun saveCurrentNote(currentNoteId: String) {
        viewModelScope.launch {
            localRepository.saveCurrentNote(currentNoteId)
        }
    }

    fun saveCurrentNote() {
        viewModelScope.launch {
            localRepository.saveCurrentNote()
        }
    }

    fun sortNotes(){
        viewModelScope.launch {
            notesRepository.sortNotes()
        }
    }


}

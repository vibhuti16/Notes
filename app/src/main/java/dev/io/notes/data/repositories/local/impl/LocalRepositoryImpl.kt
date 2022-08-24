
package dev.io.notes.data.repositories.local.impl

import dev.io.notes.data.repositories.local.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow



class LocalRepositoryImpl : LocalRepository {

    override val currentNote = MutableStateFlow("")
    override suspend fun saveCurrentNote(currentNote: String) {
        this.currentNote.emit(currentNote)
    }


    override val currentSelectedPosition = MutableStateFlow(0)

    override suspend fun saveSelectedPosition(position: Int) {
        currentSelectedPosition.value = position
    }


}
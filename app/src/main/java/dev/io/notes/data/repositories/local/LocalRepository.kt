
package dev.io.notes.data.repositories.local

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

interface LocalRepository {
    suspend fun saveCurrentNote(currentNote: String = UUID.randomUUID().toString())
    val currentNote: MutableStateFlow<String>

    suspend fun saveSelectedPosition(position: Int)
    val currentSelectedPosition: MutableStateFlow<Int>


}
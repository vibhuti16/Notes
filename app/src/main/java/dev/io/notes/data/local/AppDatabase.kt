package dev.io.notes.data.local

import androidx.room.*
import dev.io.notes.models.*
import kotlinx.coroutines.flow.Flow


@Database(
    version = 1,
    entities = [Note::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}



package dev.io.notes.di

import android.content.Context
import androidx.room.Room
import dev.io.notes.data.local.*
import dev.io.notes.data.repositories.local.LocalRepository
import dev.io.notes.data.repositories.local.impl.LocalRepositoryImpl
import dev.io.notes.data.repositories.notes.NotesRepository
import dev.io.notes.data.repositories.notes.impl.NotesRepositoryImpl
import kotlin.random.Random
import kotlin.random.nextInt

fun getDb(context: Context): AppDatabase {
    return synchronized(context) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-paper"
        ).fallbackToDestructiveMigration().build()
    }
}

fun getNoteTableDao(appDatabase: AppDatabase) = appDatabase.noteDao()

fun getNotesRepository(
    notesDao: NoteDao
): NotesRepository = NotesRepositoryImpl(notesDao)

fun getLocalRepository(): LocalRepository = LocalRepositoryImpl()


fun getRandomNumber() = Random(System.currentTimeMillis()).nextInt(0..17)

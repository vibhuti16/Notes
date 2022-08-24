package dev.io.notes.ui.screens.note.note

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.io.notes.data.local.AppDatabase
import dev.io.notes.data.local.NoteDao
import dev.io.notes.data.repositories.local.impl.LocalRepositoryImpl
import dev.io.notes.data.repositories.notes.impl.NotesRepositoryImpl
import dev.io.notes.models.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class NoteViewModelTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var notesRepositoryImpl: NotesRepositoryImpl
    private lateinit var localRepositoryImpl: LocalRepositoryImpl
    private lateinit var noteViewModel: NoteViewModel

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
        noteDao = appDatabase.noteDao()
        notesRepositoryImpl = NotesRepositoryImpl(notesDao = noteDao)
        localRepositoryImpl = LocalRepositoryImpl()
        noteViewModel = NoteViewModel(notesRepositoryImpl, localRepositoryImpl)
    }

    @After
    fun teardown() {
        appDatabase.close()
    }

    @Test
    fun `onInsertNote`() = runBlocking {
        val note = Note("1","hey", Calendar.getInstance().timeInMillis)
        noteViewModel.saveNote(note)
        with(noteDao.getAllNotes()) {
            assertThat(this.size).isEqualTo(1)
        }
    }

}
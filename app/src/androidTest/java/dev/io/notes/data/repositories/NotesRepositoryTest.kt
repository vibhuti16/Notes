package dev.io.notes.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import dev.io.notes.data.local.AppDatabase
import dev.io.notes.data.local.NoteDao
import dev.io.notes.data.repositories.notes.impl.NotesRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NotesRepositoryTest {

    private lateinit var dao: NoteDao
    private lateinit var notesRepository: NotesRepositoryImpl

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        dao = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
            "wordDb"
        )
            .allowMainThreadQueries()
            .build().noteDao()
        notesRepository = NotesRepositoryImpl(dao)
    }

    @Test
    fun NotesRepositoryImpl_Notes_stream_is_backed_by_Notes_dao() =
        runTest {
            Assert.assertEquals(
                dao.getAllNotesByFlow()
                    .first(),
                notesRepository.observeNotes()
                    .first()
            )
        }
}
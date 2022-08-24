package dev.io.notes.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.io.notes.models.Note
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class NotesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var appDatabase: AppDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        noteDao = appDatabase.noteDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertNote() = runBlocking {
        val note = Note("1","hey",Calendar.getInstance().timeInMillis)
        noteDao.insert(note)
        val result = noteDao.getAllNotes()
        assertThat(result[result.size-1]).isEqualTo(note)
    }

    @Test
    fun deleteNote() = runBlocking {
        val note = Note("1","hey",Calendar.getInstance().timeInMillis)
        noteDao.insert(note)
        noteDao.deleteNote(note.noteId)
        val result = noteDao.getAllNotes()
        assertThat(result.size).isEqualTo(0)
    }

}
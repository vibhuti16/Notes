package dev.io.notes.di

import dev.io.notes.ui.screens.home.HomeViewModel
import dev.io.notes.ui.screens.note.note.NoteViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * modules for dependency injection where [single] represents singleton class
 */
var databaseModule = module {
    single { getDb(androidApplication()) }
    single { getNoteTableDao(get()) }
}

var viewModel = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { NoteViewModel(get(), get()) }
}
var repositories = module {
    single { getLocalRepository() }
    single { getNotesRepository(get()) }
}
var utils = module {
    factory { getRandomNumber() }
}

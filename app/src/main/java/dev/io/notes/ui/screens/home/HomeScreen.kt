package dev.io.notes.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.io.notes.R
import dev.io.notes.models.Note
import dev.io.notes.ui.component.*
import dev.io.notes.ui.screens.Destinations
import io.ak1.rangvikalp.colorArray
import org.koin.androidx.compose.inject


val fabShape = RoundedCornerShape(30)

const val darkerToneIndex = 7
const val lighterToneIndex = 1

@Composable
fun HomeScreen(isDark: Boolean, scrollState: LazyListState, navigateTo: (String) -> Unit) {
    val homeViewModel by inject<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    LocalTextInputService.current?.hideSoftwareKeyboard()
    HomeScreen(isDark, uiState, scrollState, {
        homeViewModel.saveCurrentNote(it.noteId)
        navigateTo(Destinations.NOTE_ROUTE)
    }, {
        homeViewModel.saveCurrentNote()
        navigateTo(Destinations.NOTE_ROUTE)
    }, {
        homeViewModel.sortNotes()
    }, navigateTo)
}

@Composable
fun HomeScreen(
    isDark: Boolean,
    uiState: HomeUiState,
    scrollState: LazyListState,
    saveNote: (Note) -> Unit,
    openNewNote: () -> Unit,
    sortNotes: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val randomInt by inject<Int>()
    val headerColor = colorArray[randomInt][if (isDark) lighterToneIndex else darkerToneIndex]
    val tintColor = colorArray[randomInt][if (isDark) darkerToneIndex else lighterToneIndex]
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(10.dp),
                title = {
                    Text(text = stringResource(R.string.app_name), textAlign = TextAlign.Center)
                },
                actions = {
                    OverflowMenu {
                        DropdownMenuItem(onClick = { sortNotes.invoke()}) {
                            Text("Sort")
                        }
                    }
                },
                backgroundColor = Color.Transparent,
            )
        },
        content = { paddingValues ->
            NotesListComponent(
                uiState,
                scrollState,
                paddingValues,
                navigateTo,
                saveNote
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = openNewNote,
                shape = fabShape,
                backgroundColor = headerColor
            ) {
                Image(imageVector = Icons.Default.Edit, contentDescription = "New note",colorFilter = ColorFilter.tint(tintColor))
            }
        })
}


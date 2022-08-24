@file:OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)

package dev.io.notes.ui.component
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.io.notes.R
import dev.io.notes.models.Note
import dev.io.notes.ui.screens.Destinations
import dev.io.notes.ui.screens.home.HomeUiState
import dev.io.notes.ui.utils.gridTrim
import dev.io.notes.ui.utils.timeAgo
import dev.io.notes.ui.utils.toPercent


@Composable
fun NotesListComponent(
    homeUiState: HomeUiState,
    scrollState: LazyListState = rememberLazyListState(),
    padding: PaddingValues,
    navigateTo: (String) -> Unit,
    callback: (Note) -> Unit
) {
    val modifier = Modifier
        .padding(padding)
    Column {
        LazyColumn(modifier = modifier, state = scrollState) {
//            if (includeHeader) {
//                item {
//                    HomeHeader(headerColor = headerColor, scrollState = scrollState) {
////                        PaperIconButton(
////                            id = R.drawable.ic_more,
////                        ) { navigateTo(Destinations.SETTING_ROUTE) }
//                        OverflowMenu {
//                            DropdownMenuItem(onClick = { /*TODO*/ },) {
//                                Text("Settings")
//                            }
//                        }
//                    }
//                }
//            }
            itemsIndexed(homeUiState.notes) { index, element ->
                NoteView(element) {
                    callback(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteView(element: Note, callback: (Note) -> Unit) {
    val hasDescription = element.title.trim().isNotBlank()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(10),
        elevation = 0.dp,
        onClick = { callback(element) },
    ) {
        Column {
            if (hasDescription) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = element.title.trim().gridTrim(),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    VerticalSpacer(7.dp)
                    Text(
                        text = element.updatedOn.timeAgo(),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.caption
                    )

                }
            }
        }
    }
}


@Composable
fun PaperIconButton(
    @DrawableRes id: Int,
    enabled: Boolean = true,
    border: Boolean = false,
    tint: Color = if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            painterResource(id = id),
            contentDescription = stringResource(id = R.string.image_desc),
            tint = tint,
            modifier = if (border) Modifier.border(
                0.5.dp,
                MaterialTheme.colors.primary,
                CircleShape
            ) else Modifier
        )
    }
}


@Composable
fun OverflowMenu(content: @Composable () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = {
        showMenu = !showMenu
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = stringResource(R.string.more),
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        content()
    }
}

val headerBarCollapsedHeight = 50.dp
val headerBarExpandedHeight = 220.dp

@file:OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)

package io.ak1.paper.ui.component

import android.content.res.Configuration
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import io.ak1.paper.R
import io.ak1.paper.models.Note
import io.ak1.paper.models.NoteWithDoodleAndImage
import io.ak1.paper.models.getBitmapList
import io.ak1.paper.ui.screens.Destinations
import io.ak1.paper.ui.screens.home.DEFAULT
import io.ak1.paper.ui.theme.PaperTheme
import io.ak1.paper.ui.utils.gridTrim
import io.ak1.paper.ui.utils.limitWidthInWideScreen
import io.ak1.paper.ui.utils.timeAgo
import io.ak1.paper.ui.utils.toPercent

/**
 * Created by akshay on 27/11/21
 * https://ak1.io
 */


@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    scrollState: LazyListState? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val headerSize = if (scrollState == null) 20f else 40f
    val headerPadding = 11.dp
    var textSize by remember { mutableStateOf(headerSize) }
    var textPadding by remember { mutableStateOf(headerPadding) }
    val loc = LocalDensity.current
    val height = if (scrollState == null) headerBarCollapsedHeight else headerBarExpandedHeight
    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .onGloballyPositioned {
                val topBarHeight = with(loc) { headerBarCollapsedHeight.toPx() }
                val actualHeight = it.size.height - topBarHeight

                if ((scrollState?.firstVisibleItemScrollOffset ?: 0) < actualHeight.toInt()) {
                    val local =
                        scrollState?.firstVisibleItemScrollOffset?.toPercent(actualHeight) ?: 0f
                    textSize = (headerSize * local) + 20f
                    with(loc) {
                        textPadding = (((it.size.height / 2) - 30f) * local).toDp() + 11.dp
                    }
                }
            },
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = TextUnit(
                textSize,
                TextUnitType.Sp
            ),
            modifier = Modifier
                .padding(16.dp, 11.dp, 12.dp, textPadding)
                .align(Alignment.BottomStart),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            content = actions
        )
    }
}

@Composable
fun NotesListComponent(
    includeHeader: Boolean = true,
    resultList: List<NoteWithDoodleAndImage>,
    scrollState: LazyListState = rememberLazyListState(),
    padding: PaddingValues,
    navigateTo: (String) -> Unit,
    callback: (NoteWithDoodleAndImage) -> Unit
) {
    val modifier = Modifier
        .padding(padding)
    LazyColumn(modifier = modifier.limitWidthInWideScreen(), state = scrollState) {
        if (includeHeader) {
            item {
                HomeHeader(scrollState = scrollState) {
                    PaperIconButton(
                        id = R.drawable.ic_search,
                    ) { navigateTo(Destinations.SEARCH_ROUTE) }
                    PaperIconButton(
                        id = R.drawable.ic_more,
                    ) { navigateTo(Destinations.SETTING_ROUTE) }
                }
            }
        }
        itemsIndexed(resultList) { index, element ->
            NoteView(element) {
                callback(it)
            }
        }
    }
}

@Composable
fun NoteView(element: NoteWithDoodleAndImage, callback: (NoteWithDoodleAndImage) -> Unit) {
    val hasDoodle = element.doodleList.isNotEmpty()
    val hasImages = element.imageList.isNotEmpty()
    val hasDescription = element.note.description.trim().isNotBlank()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(10),
        elevation = 0.dp,
        onClick = { callback(element) },
    ) {
        Column {
            if (hasDoodle || hasImages) {
                ImageGridView(element)
            }
            if (hasDescription) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = element.note.description.trim().gridTrim(),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    VerticalSpacer(7.dp)
                    Text(
                        text = element.note.updatedOn.timeAgo(),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.caption
                    )

                }
            }
        }
    }
}

@Composable
fun ImageGridView(element: NoteWithDoodleAndImage) {
    val defaultHeight = 120.dp
    Box {
        val bitmapList = element.getBitmapList()
        val totalSize = bitmapList.size
        if (totalSize == 1) {
            bitmapList[0]?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(defaultHeight)
                        .fillMaxWidth()
                )
            }
        } else if (totalSize == 2 || totalSize == 3 || totalSize == 4) {
            Row {
                bitmapList.forEach {
                    it?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null, // decorative
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(defaultHeight)
                                .fillMaxWidth()
                                .weight(1f, true)
                        )
                    }
                }
            }
        }
        if (element.note.description.trim().isEmpty())
            Text(
                text = element.note.updatedOn.timeAgo(),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(14.dp)
            )

    }
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Preview("Home screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun Preview() {
    PaperTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                NoteView(
                    element = NoteWithDoodleAndImage(
                        Note(DEFAULT, "Hello this is sample text"), listOf(),
                        listOf()
                    )
                ) {}
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
            modifier = if (border) Modifier.border(0.5.dp, Color.White, CircleShape) else Modifier
        )
    }
}

val headerBarCollapsedHeight = 50.dp
val headerBarExpandedHeight = 200.dp
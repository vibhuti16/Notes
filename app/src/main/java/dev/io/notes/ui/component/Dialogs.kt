package dev.io.notes.ui.component

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.io.notes.R

@Composable
fun CustomAlertDialog(
    @StringRes titleId: Int = R.string.image_desc,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    callback: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) }, title = {
                Text(
                    text = stringResource(
                        id = titleId
                    ),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            setShowDialog(false)
                            callback()
                        },
                        modifier = Modifier.padding(8.dp, 0.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = android.R.string.ok
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Button(
                        onClick = { setShowDialog(false) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = android.R.string.cancel
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }

            }
        )
    }
}

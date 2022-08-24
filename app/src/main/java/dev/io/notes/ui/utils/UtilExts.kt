package dev.io.notes.ui.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.format.DateUtils
import android.util.Base64
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.io.notes.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun Long.timeAgo() = DateUtils.getRelativeTimeSpanString(
    this,
    Calendar.getInstance().timeInMillis,
    DateUtils.DAY_IN_MILLIS
).toString()

fun Long.timeAgoInSeconds() = DateUtils.getRelativeTimeSpanString(
    this,
    Calendar.getInstance().timeInMillis,
    DateUtils.SECOND_IN_MILLIS
).toString()


fun String.gridTrim(maxDigits: Int = 100) =
    if (this.length > maxDigits) "${this.substring(0, maxDigits)}..." else this


fun Int.toPercent(dependency: Float) =
    this.let { it -> 1 - ((it * 100 / dependency) / 100f) }

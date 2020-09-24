package com.devdd.framework.spotify.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.WEBP
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import timber.log.Timber
import java.io.*
/**
 * Created by @author Deepak Dawade on 9/20/2020 at 7:53 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/

fun Context.fileIfExistsOrNull(parent: String, fileName: String, ext: String): File? {
    val file = File(filesDir, "$parent${File.separator}${fileName}.$ext")
    return if (file.exists()) file else null
}

fun Context.fileIfExistsOrNull(path: String?): File? {
    if (path == null) return null
    val file = File(filesDir, path)
    return if (file.exists()) file else null
}

fun Context.createFile(parent: String, fileName: String, ext: String): File {
    return File("${createFileDirectory(parent)}${File.separator}${fileName}.$ext")
}

fun Context.createFile(path: String): File {
    createFileDirectory(File(path).parent)
    return File(path)
}

fun Context.createFileDirectory(parent: String): String {
    val directory = File(filesDir, parent)
    directory.mkdirs()
    return directory.absolutePath
}

fun File.copyFrom(sourceInputStream: InputStream) {
    sourceInputStream.use { inputStream ->
        val sourceOutputStream = FileOutputStream(this)
        sourceOutputStream.use { outputStream ->
            val buffer = ByteArray(4 * 1024) // buffer size
            while (true) {
                val byteCount = inputStream.read(buffer)
                if (byteCount < 0) break
                outputStream.write(buffer, 0, byteCount)
            }
            outputStream.flush()
        }
    }
}

fun Bitmap.saveToImageDirectory(imageFile: File, compressFormat: Bitmap.CompressFormat = WEBP): Boolean {
    try {
        val oStream = FileOutputStream(imageFile)
        compress(compressFormat, 100, oStream)
        oStream.flush()
        oStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
        Timber.e("There was an issue saving the image ${imageFile.name}.")
        return false
    }
    return true
}

fun View.toBitmap(): Bitmap {
    // Measure Specs (Unspecified)
    measure(
            View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0 /* any */, View.MeasureSpec.UNSPECIFIED)
    )
    // Create bitmap
    val bitmap: Bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
    // Draw Bitmap on Canvas
    val canvas = Canvas(bitmap)
    layout(0, 0, measuredWidth, measuredHeight)
    val backgroundDrawable: Drawable? = background
    if (backgroundDrawable != null) {
        //has background drawable, then draw it on the canvas
        backgroundDrawable.draw(canvas)
    } else {
        //does not have background drawable, then draw white background on the canvas
        canvas.drawColor(Color.WHITE)
    }
    draw(canvas)
    return bitmap
}

fun String.toFileUri(): Uri = Uri.Builder().authority("")
        .path(this)
        .scheme("file")
        .build()
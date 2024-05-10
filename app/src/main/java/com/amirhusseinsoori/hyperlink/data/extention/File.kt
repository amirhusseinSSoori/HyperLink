package com.amirhusseinsoori.hyperlink.data.extention

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import org.apache.commons.io.FileUtils
import java.io.File

 fun ContentResolver.createFileFromUri(context: Context, name: String, uri: Uri): File? {
    return try {
        val stream = this.openInputStream(uri)
        val file =
            File.createTempFile(
                "${name}_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )
        FileUtils.copyInputStreamToFile(
            stream,
            file
        )  // Use this one import org.apache.commons.io.FileUtils
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
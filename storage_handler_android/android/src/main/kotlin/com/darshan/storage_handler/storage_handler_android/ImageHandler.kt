package com.darshan.storage_handler.storage_handler_android

import android.content.ContentResolver
import android.content.ContentValues
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.webkit.MimeTypeMap
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ImageHandler constructor(private val resolver: ContentResolver) {
    fun saveImage(path: String) {
        val imgFile = File(path)
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

        val fileBytes: ByteArray = getBytesFromFile(imgFile)

        var imgUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val imageValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "Title")
            put(MediaStore.Images.Media.DISPLAY_NAME, imgFile.name)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        }

        try {
            imgUri = resolver.insert(imgUri, imageValues)

            if (imgUri != null) {
                // Writing the file
                val outputStream = resolver.openOutputStream(imgUri, "w")
                outputStream?.use {
                    it.write(fileBytes)
                }
                outputStream?.flush()
                outputStream?.close()

                // Saving the thumbnail
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ThumbnailUtils.createImageThumbnail(imgFile, Size(100, 100), null)
                }
            }
        } catch (ioe: IOException) {
            if (imgUri != null)
                resolver.delete(imgUri, null, null)
        }
    }

    private fun getBytesFromFile(file: File): ByteArray {
        val size = file.length().toInt()
        val bytes = ByteArray(size)
        val inputStream = BufferedInputStream(FileInputStream(file))
        inputStream.use {
            inputStream.read(bytes, 0, bytes.size)
        }
        return bytes
    }
}
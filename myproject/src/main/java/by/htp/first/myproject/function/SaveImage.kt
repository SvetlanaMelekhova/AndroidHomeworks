package by.htp.first.myproject.function

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream

fun saveImage(photo: Bitmap, iv: ImageView, personPhotoDirectory: File): String {
    val path = "photo_${System.currentTimeMillis()}.jpg"
    val pathToPicture = "${personPhotoDirectory.path}/${path}"
    val file = File(personPhotoDirectory, path)
    file.createNewFile()
    val stream = FileOutputStream(file)
    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    iv.setImageBitmap(photo)
    stream.flush()
    stream.close()
    return pathToPicture
}

fun createDirectory(context: Context): File? {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val personPhotoDirectory =
            File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/PersonPhoto")
        if (!personPhotoDirectory.exists()) {
            personPhotoDirectory.mkdir()
        }
        return personPhotoDirectory
    }
    return null
}
package by.htp.first.homework6_1.adapter

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.net.Uri
import by.htp.first.homework6_1.database.CarDatabase

class WorkContentProvider : ContentProvider() {

    private var database: CarDatabase? = null

    companion object {
        private const val AUTHORITY = "by.htp.first.homework6_1_1.adapter.WorkContentProvider"
        private const val URI_USER_CODE = 1
        private val uriMatcher: UriMatcher =
            UriMatcher(UriMatcher.NO_MATCH).apply {
                addURI(AUTHORITY, "database", URI_USER_CODE)
            }
    }

    override fun onCreate(): Boolean {
        context?.run {
            database = CarDatabase.getDataBase(this.applicationContext)
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ) = if (uriMatcher.match(uri) == URI_USER_CODE)
            database?.getWorkDatabaseDAO()?.getWorkList() else null


    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}
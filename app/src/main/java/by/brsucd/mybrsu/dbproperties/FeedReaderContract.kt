package by.brsucd.mybrsu.dbproperties

import android.provider.BaseColumns

object FeedReaderContract {

    object FeedEntry : BaseColumns{
        const val DB_NAME = "mybrsu.db"
        const val DB_VERSION = 1
        const val FACULTY_STRUCTURE = "faculty_structure"
        const val ORG_STRUCTURE = "org_structure"
        const val FACULTY_STRUCTURE_COLUMN_NAME = "faculty_name"
        const val FACULTY_STRUCTURE_COLUMN_IMAGE = "image"
        const val ORG_STRUCTURE_COLUMN_NAME = "org_name"
        const val ORG_STRUCTURE_COLUMN_IMAGE = "image"
    }
    private const val CREATE_DB_FACULTY_STRUCTURE = "CREATE TABLE ${FeedEntry.FACULTY_STRUCTURE} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${FeedEntry.FACULTY_STRUCTURE_COLUMN_NAME} TEXT, " +
            "${FeedEntry.FACULTY_STRUCTURE_COLUMN_IMAGE} BLOB)"
    private const val CREATE_DB_ORG_STRUCTURE = "CREATE TABLE ${FeedEntry.ORG_STRUCTURE} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${FeedEntry.ORG_STRUCTURE_COLUMN_NAME} TEXT, " +
            "${FeedEntry.ORG_STRUCTURE_COLUMN_IMAGE} BLOB)"
}
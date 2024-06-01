package by.brsucd.mybrsu.dbproperties

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val CREATE_DB_FACULTY_STRUCTURE = "CREATE TABLE ${FeedReaderContract.FeedEntry.FACULTY_STRUCTURE} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
        "${FeedReaderContract.FeedEntry.FACULTY_STRUCTURE_COLUMN_NAME} TEXT, " +
        "${FeedReaderContract.FeedEntry.FACULTY_STRUCTURE_COLUMN_IMAGE} BLOB)"

private const val DELETE_DB_FACULTY_STRUCTURE = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.FACULTY_STRUCTURE}"

private const val CREATE_DB_ORG_STRUCTURE = "CREATE TABLE ${FeedReaderContract.FeedEntry.ORG_STRUCTURE} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
        "${FeedReaderContract.FeedEntry.ORG_STRUCTURE_COLUMN_NAME} TEXT, " +
        "${FeedReaderContract.FeedEntry.ORG_STRUCTURE_COLUMN_IMAGE} BLOB)"

private const val DELETE_DB_ORG_STRUCTURE = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.ORG_STRUCTURE}"

class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_DB_FACULTY_STRUCTURE)
        db?.execSQL(CREATE_DB_ORG_STRUCTURE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DELETE_DB_FACULTY_STRUCTURE)
        db?.execSQL(DELETE_DB_ORG_STRUCTURE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object{
        const val DB_NAME = "mybrsu.db"
        const val DB_VERSION = 1
    }

}
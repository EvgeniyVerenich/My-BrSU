package by.brsucd.mybrsu.dbproperties

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, FeedReaderContract.FeedEntry.DB_NAME, null, FeedReaderContract.FeedEntry.DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}
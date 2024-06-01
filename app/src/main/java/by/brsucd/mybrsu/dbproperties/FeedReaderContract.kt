package by.brsucd.mybrsu.dbproperties

import android.provider.BaseColumns

object FeedReaderContract {

    object FeedEntry : BaseColumns{
        const val FACULTY_STRUCTURE = "faculty_structure"
        const val ORG_STRUCTURE = "org_structure"
        const val FACULTY_STRUCTURE_COLUMN_NAME = "faculty_name"
        const val FACULTY_STRUCTURE_COLUMN_IMAGE = "image"
        const val ORG_STRUCTURE_COLUMN_NAME = "org_name"
        const val ORG_STRUCTURE_COLUMN_IMAGE = "image"
    }
}
package com.luckyfriday.vocabulary.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luckyfriday.vocabulary.model.WordCategory
import com.luckyfriday.vocabulary.model.WordData

class SqlDbHandler(context: Context?) : SQLiteOpenHelper(context, DV_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + MEANING_COL + " TEXT, "
                + CATEGORY_COL + " TEXT)")
        db?.execSQL(query)
    }

    fun addVocab(name: String?, meaning: String?, category: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(MEANING_COL, meaning)
        values.put(CATEGORY_COL, category)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getVocab(): ArrayList<WordData> {
        val db = this.readableDatabase
        val cursorVocabs = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val vocabList: ArrayList<WordData> = arrayListOf()

        if (cursorVocabs.moveToFirst()) {
            do {
                WordCategory.entries.map {
                    if (it.title == cursorVocabs.getString(3)) {
                        vocabList.add(
                            WordData(cursorVocabs.getInt(0),
                            cursorVocabs.getString(1),
                            cursorVocabs.getString(2),
                            it,)
                        )
                    }
                }
            } while (cursorVocabs.moveToNext())
        }
        cursorVocabs.close()
        return vocabList
    }

    fun deleteVocab(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    companion object {
        private const val DV_NAME = "vocabdb"
        private const val TABLE_NAME = "vocab"
        private const val DB_VERSION = 1
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
        private const val MEANING_COL = "meaning"
        private const val CATEGORY_COL = "category"

    }
}
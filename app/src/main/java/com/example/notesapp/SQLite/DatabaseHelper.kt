package com.example.notesapp.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,"details.db", null, 2) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table notes (pk INTEGER PRIMARY KEY AUTOINCREMENT, Text text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // This removes the table if a new version is detected
        db!!.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    fun saveData(text: String){
        val contentValues = ContentValues()
        contentValues.put("Text", text)
        sqLiteDatabase.insert("notes", null, contentValues)
    }

    fun getData(): ArrayList<Note>{
        val notes = arrayListOf<Note>()

        // Read all data using cursor
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes", null)

        if(cursor.count < 1){  // Handle empty table
            println("No Data Found")
        }else{
            while(cursor.moveToNext()){  // Iterate through table and populate people Array List
                val pk = cursor.getInt(0)  // The integer value refers to the column
                val text = cursor.getString(1)
                Log.d("MAIN", text)
                notes.add(Note(pk, text))
            }
        }
        return notes
    }

    fun updateData(note: Note, text: String){
        val contentValues = ContentValues()
        contentValues.put("Text", text)
        sqLiteDatabase.update("notes", contentValues, "pk = ${note.pk}", null)
    }

    fun deleteData(note: Note){
        sqLiteDatabase.delete("notes", "pk = ${note.pk}", null)
    }
}
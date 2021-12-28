package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }
    lateinit var editText: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editTextTextPersonName)
        button = findViewById(R.id.button)

        button.setOnClickListener{
            databaseHelper.saveData(editText.text.toString())
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }
    }
}
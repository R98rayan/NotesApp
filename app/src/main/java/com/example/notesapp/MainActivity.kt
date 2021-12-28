package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var getData: Button

    lateinit var rvMain: RecyclerView
    lateinit var rvAdapter: RVAdapter

    var list = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(list)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        editText = findViewById(R.id.editTextTextPersonName)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            databaseHelper.saveData(editText.text.toString())
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
        }

        getData = findViewById(R.id.getData)

        getData.setOnClickListener {
            Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
            rvAdapter.update(databaseHelper.getData())
        }

    }
}
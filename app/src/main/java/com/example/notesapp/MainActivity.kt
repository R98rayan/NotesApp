package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

   val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }

    var selectedNote: Note? = null

    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var getData: Button

    lateinit var rvMain: RecyclerView
    lateinit var rvAdapter: RVAdapter

    var list = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {

        Shared.main = this

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(list)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        editText = findViewById(R.id.editTextTextPersonName)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            if(editText.text.isNotEmpty()){
                CoroutineScope(IO).launch {
                    noteDao.addNote(Note(0, editText.text.toString()))
                    Shared.notes = noteDao.getNotes()
                }
                Handler().postDelayed({
                    rvAdapter.update(Shared.notes)
                    editText.text.clear()
                }, 200)

                Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show()
            }
        }

        CoroutineScope(IO).launch {
            Shared.notes = noteDao.getNotes()
        }
        Handler().postDelayed({
            rvAdapter.update(Shared.notes)
            editText.text.clear()
        }, 200)

    }
}
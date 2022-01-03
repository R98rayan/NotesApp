package com.example.notesapp

import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    var notes : List<Note> = listOf()
}
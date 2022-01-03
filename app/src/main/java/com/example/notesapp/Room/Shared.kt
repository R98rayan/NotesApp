package com.example.notesapp.Room

class Shared {
    companion object{
        lateinit var main: MainActivity
        var notes : List<Note> = listOf()
    }
}
package com.example.notesapp

class Shared {
    companion object{
        lateinit var main: MainActivity
        var notes : List<Note> = listOf()
    }
}
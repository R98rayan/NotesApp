package com.example.notesapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener
import androidx.recyclerview.widget.GridLayoutManager
import dev.sasikanth.colorsheet.ColorSheet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class MainActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()

        Shared.main = this

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        Note.fetchNotesFromFirestore()

    }
}
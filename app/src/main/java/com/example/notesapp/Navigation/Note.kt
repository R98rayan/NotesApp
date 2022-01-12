package com.example.notesapp.Navigation

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener







data class Note(
    var id: String,
    var title: String,
    var date: String,
    var description: String,
    var color: String
){
    var db: FirebaseFirestore = Shared.main.db
    var TAG = "Note"

    fun saveToFirestore(){

        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val note: MutableMap<String, Any> = HashMap()
        note["title"] = title
        note["date"] = date
        note["description"] = description
        note["color"] = color

        // Add a new document with a generated ID

        // Add a new document with a generated ID
        db.collection("notes")
            .add(note)
            .addOnSuccessListener { documentReference ->
                id = documentReference.id
                Log.d( TAG, "DocumentSnapshot added with ID: " + documentReference.id )

                fetchNotesFromFirestore()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    fun updateDescriptionToFirestore(){

        val washingtonRef = db.collection("notes").document(id)

        washingtonRef
            .update("description", description)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Shared.main.rvAdapter.update(notes)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    fun updateTitleToFirestore(){

        val washingtonRef = db.collection("notes").document(id)

        washingtonRef
            .update("title", title)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                Shared.main.rvAdapter.update(notes)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }


    fun updateToFirestore(){

        val washingtonRef = db.collection("notes").document(id)

        var a1 = false
        var a2 = false
        var a3 = false
        washingtonRef
            .update("title", title)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                a1 = true
                if(a1 && a2 && a3) Shared.main.rvAdapter.update(notes)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

        washingtonRef
            .update("description", description)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                a2 = true
                if(a1 && a2 && a3) Shared.main.rvAdapter.update(notes)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

        washingtonRef
            .update("color", color)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                a3 = true
                if(a1 && a2 && a3) Shared.main.rvAdapter.update(notes)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }


    }


    fun deleteFromFirestore(){
        db.collection("notes").document(id)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    companion object{

        var notes: List<Note> = listOf()

        fun fetchNotesFromFirestore(){

            var tempNotes: ArrayList<Note> = arrayListOf()

            var db: FirebaseFirestore = Shared.main.db
            var TAG = "Note"

            Log.d(TAG, "working on it!")

            db.collection("notes")
                .get()
                .addOnSuccessListener { documents ->
                    Log.d(TAG, "${documents.toString()}")
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val note = Note(
                            id = document.id,
                            title = document.data["title"].toString(),
                            description = document.data["description"].toString(),
                            date = document.data["date"].toString(),
                            color = document.data["color"].toString()

                        )
                        tempNotes.add(note)
                    }

                    notes = tempNotes
                    Shared.main.rvAdapter.update(notes)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }


        }
    }
}


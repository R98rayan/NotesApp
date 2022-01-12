package com.example.notesapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import dev.sasikanth.colorsheet.ColorSheet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NoteFragment : Fragment() {

    var db: FirebaseFirestore = Shared.main.db

    val TAG = "Main"
    val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    var selectedNote: Note? = null

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var addButton: Button
    lateinit var getData: Button

    lateinit var rvMain: RecyclerView
    lateinit var rvAdapter: RVAdapter

    var list = arrayListOf<Note>()

    fun saveToFirestore(){

        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d( TAG, "DocumentSnapshot added with ID: " + documentReference.id )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Shared.noteFragment = this


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note, container, false)




        rvMain = view.findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(list)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(context)

        val layoutManager = StaggeredGridLayoutManager(2, 1)
        rvMain.setLayoutManager(layoutManager)

        addButton = view.findViewById(R.id.button)

        addButton.setOnClickListener {

            var textColor = "yellow"
            @ColorInt val yellow: Int = getResources().getColor(R.color.yellow);
            @ColorInt val blue: Int = getResources().getColor(R.color.blue);
            @ColorInt val red: Int = getResources().getColor(R.color.red);
            @ColorInt val green: Int = getResources().getColor(R.color.green);
            val colors: IntArray = intArrayOf(yellow, blue, red, green)

            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Add new Note!")

            val linearLayoutVERTICAL = LinearLayout(context)
            linearLayoutVERTICAL.orientation = LinearLayout.VERTICAL

            val titleBox = EditText(context)
            titleBox.hint = "Title"
            linearLayoutVERTICAL.addView(titleBox) // Notice this is an add method

            val descriptionBox = EditText(context)
            descriptionBox.hint = "Description"
            linearLayoutVERTICAL.addView(descriptionBox) // Another add method

            //===============================================================

            val button = Button(context)
            button.text = "Pick a Color"
            button.setBackgroundColor(yellow)
            button.setOnClickListener{
                ColorSheet().colorPicker(
                    colors = colors,
                    listener = { color ->
                        if(color.equals(yellow)) {textColor = "yellow"}
                        else if(color.equals(blue)) textColor = "blue"
                        else if(color.equals(red)) textColor = "red"
                        else if(color.equals(green)) textColor = "green"
                        button.setBackgroundColor(color)
                    })
                    .show(requireActivity().supportFragmentManager)
            }
            linearLayoutVERTICAL.addView(button)

            //===============================================================
            builder.setView(linearLayoutVERTICAL)


            // Set up the buttons
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // Here you get get input text from the Edittext
                if(titleBox.text.isNotEmpty() || descriptionBox.text.isNotEmpty()){
                    var title = titleBox.text.toString()
                    var description = descriptionBox.text.toString()

                    val sdf = SimpleDateFormat("MM/dd/yyyy")
                    val currentDate = sdf.format(Date())

                    var note: Note = Note(
                        id = "0",
                        title = title,
                        description = description,
                        date = currentDate,
                        color = textColor
                    )

                    note.saveToFirestore()

                }
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

















        return view
    }

}
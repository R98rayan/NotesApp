package com.example.notesapp.Navigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.item_row.view.*


class RVAdapter(private var list: List<Note>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.apply {
            textViewTitle.text = item.title
            textViewDescription.text = item.description
            textViewDate.text = item.date

            if(item.color == "yellow") {
                color.setBackgroundColor(getResources().getColor(R.color.yellow))
                textViewTop.setBackgroundColor(getResources().getColor(R.color.yellow2))
                imageButtonDelete.setBackgroundColor(getResources().getColor(R.color.yellow2))
            }
            else if(item.color == "blue") {
                color.setBackgroundColor(getResources().getColor(R.color.blue))
                textViewTop.setBackgroundColor(getResources().getColor(R.color.blue2))
                imageButtonDelete.setBackgroundColor(getResources().getColor(R.color.blue2))
            }
            else if(item.color == "red") {
                color.setBackgroundColor(getResources().getColor(R.color.red))
                textViewTop.setBackgroundColor(getResources().getColor(R.color.red2))
                imageButtonDelete.setBackgroundColor(getResources().getColor(R.color.red2))
            }
            else if(item.color == "green") {
                color.setBackgroundColor(getResources().getColor(R.color.green))
                textViewTop.setBackgroundColor(getResources().getColor(R.color.green2))
                imageButtonDelete.setBackgroundColor(getResources().getColor(R.color.green2))
            }
            imageButtonDelete.setOnClickListener{
                AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this note?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            // Continue with delete operation
                            item.deleteFromFirestore()
                            Note.fetchNotesFromFirestore()
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }

            imageButtonEdit.setOnClickListener{
                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(Shared.main)
                builder.setTitle("Update Note")

                val layout = LinearLayout(Shared.main)
                layout.orientation = LinearLayout.VERTICAL

                // Add a TextView here for the "Title" label, as noted in the comments

                // Add a TextView here for the "Title" label, as noted in the comments
                val titleBox = EditText(Shared.main)
                titleBox.hint = "Title"
                titleBox.setText(item.title)
                layout.addView(titleBox) // Notice this is an add method


                // Add another TextView here for the "Description" label

                // Add another TextView here for the "Description" label
                val descriptionBox = EditText(Shared.main)
                descriptionBox.hint = "Description"
                descriptionBox.setText(item.description)
                layout.addView(descriptionBox)

                //===============================================================

                var textColor = "yellow"
                @ColorInt val yellow: Int = getResources().getColor(R.color.yellow);
                @ColorInt val blue: Int = getResources().getColor(R.color.blue);
                @ColorInt val red: Int = getResources().getColor(R.color.red);
                @ColorInt val green: Int = getResources().getColor(R.color.green);
                val colors: IntArray = intArrayOf(yellow, blue, red, green)

                val button = Button(Shared.main)
                button.text = "Pick a Color"

                if(item.color == "yellow") {
                    button.setBackgroundColor(getResources().getColor(R.color.yellow))
                }
                else if(item.color == "blue") {
                    button.setBackgroundColor(getResources().getColor(R.color.blue))
                }
                else if(item.color == "red") {
                    button.setBackgroundColor(getResources().getColor(R.color.red))
                }
                else if(item.color == "green") {
                    button.setBackgroundColor(getResources().getColor(R.color.green))
                }


                button.setOnClickListener{
                    ColorSheet().colorPicker(
                        colors = colors,
                        listener = { color ->
                            if(color.equals(yellow)) {textColor = "yellow"}
                            else if(color.equals(blue)) textColor = "blue"
                            else if(color.equals(red)) textColor = "red"
                            else if(color.equals(green)) textColor = "green"
                            button.setBackgroundColor(color)
                            item.color = textColor
                        })
                        .show(Shared.main.supportFragmentManager)
                }
                layout.addView(button)

                //===============================================================

                // Another add method


                builder.setView(layout)


                // Set up the buttons
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    // Here you get get input text from the Edittext
                    item.title = titleBox.text.toString()
                    item.description = descriptionBox.text.toString()
                    item.updateToFirestore()
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
            }


        }


    }

    override fun getItemCount() = list.size

    fun update(list: List<Note>){
        this.list = list
        notifyDataSetChanged()
    }
}
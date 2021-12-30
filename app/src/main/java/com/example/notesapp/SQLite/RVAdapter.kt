package com.example.notesapp.SQLite

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import com.example.notesapp.R


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
            textView.text = item.text

            imageButtonDelete.setOnClickListener{
                AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this note?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            // Continue with delete operation
                            Shared.main.databaseHelper.deleteData(item)
                            update(Shared.main.databaseHelper.getData())
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }

            imageButtonEdit.setOnClickListener{
                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(Shared.main)
                builder.setTitle("Update Note")

                // Set up the input
                val input = EditText(Shared.main)
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setHint("Enter Text")
                input.inputType = InputType.TYPE_CLASS_TEXT
                input.setText(item.text)
                builder.setView(input)

                // Set up the buttons
                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    // Here you get get input text from the Edittext
                    var m_Text = input.text.toString()
                    Shared.main.databaseHelper.updateData(item, m_Text)
                    update(Shared.main.databaseHelper.getData())
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
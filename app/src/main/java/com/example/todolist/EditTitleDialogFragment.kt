package com.example.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.todolist.models.TodoItem
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 * Use the [EditTitleDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTitleDialogFragment(private val todoItem: TodoItem) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.fragment_edit_title_dialog, null)

            val inputEditText = view.findViewById<EditText>(R.id.editTitleDialog)
            inputEditText.setText(todoItem.title)


            builder.setView(view)
                    .setPositiveButton(R.string.button_edit,
                            DialogInterface.OnClickListener { _, _ ->
                                val textEdited = inputEditText.text.toString()
                                val todoItem = TodoItem(textEdited)

                            })
                    .setNegativeButton(R.string.button_cancel,
                            DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")    }

}
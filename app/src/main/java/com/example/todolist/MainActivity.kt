package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.models.TodoItem

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val myDataSet: ArrayList<TodoItem> = arrayListOf(TodoItem("Finalize this project"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this);

        viewAdapter = MyAdapter(myDataSet, { onRemoveTap(it) }, { openEditAlertDialog(it) })

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }

        val inputTextTodo = findViewById<EditText>(R.id.editTextTodo)
        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)

        buttonAddTodo.setOnClickListener {
            val lastPositionAddedData: Int = myDataSet.size
            val title: String = inputTextTodo.text.toString()
            myDataSet.add(lastPositionAddedData, TodoItem(title))
            viewAdapter.notifyItemInserted(lastPositionAddedData)
        }


    }

    private fun onRemoveTap(position: Int) {
        myDataSet.removeAt(position)
        viewAdapter.notifyItemRemoved(position)
    }

    private fun openEditAlertDialog(position: Int) {
        val todoItem = myDataSet[position];
        val alertDialog = EditTitleDialogFragment(todoItem)

        alertDialog.setTargetFragment(alertDialog, 0)
        alertDialog.show(supportFragmentManager, "EditTitleDialog")

        alertDialog.
    }

    class MyAdapter(
        private var myDataSet: ArrayList<TodoItem>,
        private var onRemoveTap: (position: Int) -> Unit,
        private var openEditAlertDialog: (position: Int) -> Unit
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        class MyViewHolder(val row: ConstraintLayout) : RecyclerView.ViewHolder(row)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val row = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,
                    parent, false) as ConstraintLayout
            return MyViewHolder(row)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.row.findViewById<Button>(R.id.btn_item_delete).setOnClickListener { onRemoveTap(holder.adapterPosition) }
            holder.row.findViewById<Button>(R.id.btn_item_edit).setOnClickListener { openEditAlertDialog(holder.adapterPosition) }
            holder.row.findViewById<TextView>(R.id.my_text_view).text = myDataSet[position].title
        }

        override fun getItemCount(): Int = myDataSet.size




    }

}
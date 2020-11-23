package com.example.todolist

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

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val myDataSet: ArrayList<String> = arrayListOf("Item 1", "Item 2", "Item 3");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this);

        viewAdapter = MyAdapter(myDataSet, deleteItemFromList = { deleteItemFromList(it) } )

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }

        val inputTextTodo = findViewById<EditText>(R.id.editTextTodo)
        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)

        buttonAddTodo.setOnClickListener {
            val lastPositionAddedData: Int = myDataSet.size
            val data: String = inputTextTodo.text.toString()
            myDataSet.add(lastPositionAddedData, data)
            viewAdapter.notifyItemInserted(lastPositionAddedData)
        }


    }

    private fun deleteItemFromList(position: Int) {
        myDataSet.removeAt(position)
        viewAdapter.notifyItemRemoved(position)
    }

    class MyAdapter(private var myDataSet: ArrayList<String>, private var deleteItemFromList: (position: Int) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        class MyViewHolder(val row: ConstraintLayout) : RecyclerView.ViewHolder(row)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val row = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view,
                    parent, false) as ConstraintLayout
            return MyViewHolder(row)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.row.findViewById<Button>(R.id.btn_item_delete).setOnClickListener { deleteItemFromList(holder.adapterPosition) }
            holder.row.findViewById<TextView>(R.id.my_text_view).text = myDataSet[position]
        }

        override fun getItemCount(): Int = myDataSet.size




    }

}
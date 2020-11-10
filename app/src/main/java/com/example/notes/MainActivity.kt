package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INoteRVAdapter {
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter = NoteRVAdapter(this,this)
        recyclerView.adapter=adapter

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application
        )).get(NoteViewModel::class.java)
        viewModel.getAllNotes().observe(this, Observer {list->
            list?.let {
                adapter.updateList(it)
            }

        })
       submitData.setOnClickListener{
           val noteText = input.text.toString()
           if(noteText.isNotEmpty()){
              viewModel.insertNote(Note(noteText))
               Toast.makeText(this,"One new Item Added",Toast.LENGTH_LONG).show()
           }
       }

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"One item Deleted",Toast.LENGTH_LONG).show()
    }
}
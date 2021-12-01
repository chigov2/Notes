package com.chigov.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();// чтобы не нужно было создавать новый объект активити - меняем его на static
    private NotesAdapter adapter;
    private NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //база данных получена
        database = NotesDatabase.getInstance(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        //в списке recyclerView будут храниться заметки - для этого понадобится класс Note
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        //получаем данные
        getData();

        adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this,3));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "Position number: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                    remove(position);
            }
        });

        //swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //remove(direction);
                remove(viewHolder.getAdapterPosition());
            }
        });
        //теперь его надо присоединить к recyclerView
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

    }
    public void remove(int position){
        //при удалении получаем экземпляр записки
        Note note = notes.get(position);
        //удаляем записку из БД
        database.notesDao().deleteNote(note);
        getData();
        //после этого получаем данные
        adapter.notifyDataSetChanged();

    }

    public void onClickAddNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }
    private void getData(){
        //будем получать значения в виде ArrayList<Note>
        //получмли все заметки
        List<Note> notesFromDB = database.notesDao().getAllNotes();
        notes.clear();
        notes.addAll(notesFromDB);
    }
}
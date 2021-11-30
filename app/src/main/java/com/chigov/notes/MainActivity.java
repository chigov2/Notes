package com.chigov.notes;

import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();// чтобы не нужно было создавать новый объект активити - меняем его на static
    private NotesAdapter adapter;
    //создание объекта помощника DB
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //в списке recyclerView будут храниться заметки - для этого понадобится класс Note
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);

        //теперь из хелпера можно получить базу данных
        database = dbHelper.getWritableDatabase();
        //database.delete(NotesContract.NotesEntry.TABLE_NAME,null,null);

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
        //при удалении надо получить ID
        int id = notes.get(position).getId();
        //для удаления передать ид и что удалять
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME,where,whereArgs);
        getData();
        //notes.remove(position);// после удаления... надо обновить
        adapter.notifyDataSetChanged();

    }

    //получать данные из БД и присваивать их масссиву
    private void getData(){
        notes.clear();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,
                null,null,null, NotesContract.NotesEntry.COLUMN_PRIORITY);
        //создаем ыикл, чтобы прочитать всю инфо
        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            @SuppressLint("Range") String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            @SuppressLint("Range") int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            //можем создавать объект типа Note
            Note note = new Note(id, title,description,dayOfWeek,priority);
            //и добавляем в созданный массив
            notes.add(note);
        }
    }


    public void onClickAddNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }
}
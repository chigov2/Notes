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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //в списке recyclerView будут храниться заметки - для этого понадобится класс Note
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);

        //теперь из хелпера можно получить базу данных
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //database.delete(NotesContract.NotesEntry.TABLE_NAME,null,null);

        // проверка, чтобы 2 раза не добавлялись элементы
//        if (notes.isEmpty()){
//        notes.add(new Note("Парикмахер", "Сделать прическу", "Понедельник", 2));
//        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
//        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
//        notes.add(new Note("Стоматолог", "Вылечить зубы", "Понедельник", 2));
//        notes.add(new Note("Парикмахер", "Сделать прическу к выпускному", "Среда", 1));
//        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
//        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
//        notes.add(new Note("Магазин", "Купить новые футболки", "Среда", 2));
//        }
//        // взять инфо из notes и в цикле записать в базу данных
//        for (Note note : notes){
//            ContentValues contentValues = new ContentValues();
//            //вставить заголовок заметки
//            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE,note.getTitle());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION,note.getDescription());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK,note.getDayOfWeek());
//            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY,note.getPriority());
//            //теперь объект contentValues необходимо вставить в базу данных
//            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
//        }
        //необходимо получить данные из базы и записать в arraylist
        //ArrayList<Note> notesFromDB = new ArrayList<>();
        //вытащить все данные из базы данных
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,null,null,
                null,null,null,null);

        //создаем ыикл, чтобы прочитать всю инфо
        while (cursor.moveToNext()){
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            @SuppressLint("Range") String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            @SuppressLint("Range") int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            //можем создавать объект типа Note
            Note note = new Note(title,description,dayOfWeek,priority);
            //и добавляем в созданный массив
            notes.add(note);
        }
        //обязательно закрыть курсор
        cursor.close();

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
        notes.remove(position);// после удаления... надо обновить
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

    }
}
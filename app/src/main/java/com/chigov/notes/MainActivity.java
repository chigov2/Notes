package com.chigov.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();// чтобы не нужно было создавать новый объект активити - меняем его на static
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //в списке recyclerView будут храниться заметки - для этого понадобится класс Note
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        // проверка, чтобы 2 раза не добавлялись элементы
        if (notes.isEmpty()){
        notes.add(new Note("Парикмахер", "Сделать прическу", "Понедельник", 2));
        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
        notes.add(new Note("Стоматолог", "Вылечить зубы", "Понедельник", 2));
        notes.add(new Note("Парикмахер", "Сделать прическу к выпускному", "Среда", 1));
        notes.add(new Note("Баскетбол", "Игра со школьной командой", "Вторник", 3));
        notes.add(new Note("Магазин", "Купить новые джинсы", "Понедельник", 3));
        notes.add(new Note("Магазин", "Купить новые футболки", "Среда", 2));
        }
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
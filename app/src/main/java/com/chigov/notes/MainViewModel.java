package com.chigov.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    //получить БД и присвоить ей значение
    private NotesDatabase database;
    //создаем обект, в которм будем хранить все заметки
    private LiveData<List<Note>> notes;
    //и присвоим значение в конструкторе

    //constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
       //5
    }
}

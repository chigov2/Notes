package com.chigov.notes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    //получить БД и присвоить ей значение
    private static NotesDatabase database;
    //создаем обект, в которм будем хранить все заметки
    private LiveData<List<Note>> notes;
    //и присвоим значение в конструкторе

    //constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
       notes = database.notesDao().getAllNotes();
    }
    //создаем геттер на све заметки
    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note){
        new InsertTask().execute(note);
    }

    //delete element
    public void deleteNote(Note note){
        new DeleteTask().execute(note);
    }

    public void deleteAllNote(){
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0){
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0){
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... notes) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }
}

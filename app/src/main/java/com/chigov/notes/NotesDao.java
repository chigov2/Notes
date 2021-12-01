package com.chigov.notes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

//объект для доступа к БД
//в нем созаются методы для доступа к БД
@Dao
public interface NotesDao {
    //все данные из БД test
    @Query("SELECT * FROM notes ORDER BY dayOfWeek")
    List<Note> getAllNotes();

    @Insert
    //вставка в БД
    void  insertNote(Note note);

    @Delete
    //удаление
    void deleteNote(Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}

package com.chigov.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//entities - сущности
@Database(entities = {Note.class}, version = 1, exportSchema = false)
//этот класс должен быть абстрактным
public abstract class NotesDatabase extends RoomDatabase {
    //создаем объект класса
    private static NotesDatabase database;
    private static final String DB_NAME = "notes2.db";
    //объект синхронизации
    private static final Object LOCK = new Object();

    //метод будет возвращать объект базы данных
    public static NotesDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
                        //.allowMainThreadQueries() //потом удалить
                        .build();
            }
        }
        return database;
    }
    //получить объект интерфейса NotesDao notesDao()
    public abstract NotesDao notesDao();

}

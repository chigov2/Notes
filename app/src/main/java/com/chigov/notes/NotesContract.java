package com.chigov.notes;

import android.provider.BaseColumns;

public class NotesContract {
    //в этом классе должно бвть столько классов  - сколько у нас будет таблиц - 1
    public static final class NotesEntry implements BaseColumns{
        //создаем констатнты
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PRIORITY = "priority";

        //два типа данных
        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        //создать команду, которая будет создавать таблицу
        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
                " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_DAY_OF_WEEK +
                " " + TYPE_INTEGER + ", " + COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " +TABLE_NAME;

    }
}

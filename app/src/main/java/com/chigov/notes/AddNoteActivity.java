package com.chigov.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;

    //пустая база уже
    //helper link
    private NotesDBHelper dbHelper;
    //create DB
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }

    public void onclickSaveNote(View view) {
        //по клику получить знавения всех элементов
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        //находим ид, на который нажали
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        //находим радиобаттон по єтому  ID
        RadioButton radioButton = findViewById(radioButtonId);////               !!!!!!!!!
        //текст надо привести к int
        int priority = Integer.parseInt(radioButton.getText().toString());

        //проверка все ли поля заполнены
        if (isFilled(title, description)) {
            //из полученных данных создаем заметку
            //Note note = new Note(title,description,dayOfWeek,priority);
            //добавить эту заметку к списку заметок
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek + 1);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            //заносим данные в БД
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            //запустить MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //новые элементы добавляются  - надо сделать проверку
            // перед тем как заносить данные в базу - проверить заполнены ли все поля
        } else {
            Toast.makeText(this, R.string.warning_field, Toast.LENGTH_SHORT).show();
        }


    }

    //этот метод проверяет не пустое ли занчение title description
    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}
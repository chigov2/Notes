package com.chigov.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }

    public void onclickSaveNote(View view) {
        //по клику получить знавения всех элементов
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dayOfWeek = spinnerDaysOfWeek.getSelectedItem().toString();
        //находим ид, на который нажали
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        //находим радиобаттон по єтому  ID
        RadioButton radioButton = findViewById(radioButtonId);////               !!!!!!!!!
        //текст надо привести к int
        int priority = Integer.parseInt(radioButton.getText().toString());
        //из полученных данных создаем заметку
        Note note = new Note(title,description,dayOfWeek,priority);
        //добавить эту заметку к списку заметок
        MainActivity.notes.add(note);
        //запустить MainActivity
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //новые элементы добавляются  - надо сделать проверку

    }
}
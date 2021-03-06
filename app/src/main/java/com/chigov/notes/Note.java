package com.chigov.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    //заметка будет содержать название...
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;
    //чтобы удалить объект из БД - он должен содержать ID

    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    //add constructor
    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    //add getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    //преобразование дня недели
    public static String getDayAsString(int position) {
        switch (position) {
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
            default:
                return "Воскресенье";
        }
    }
}
//далее создается макет, который будет содержать одну заметку  note_item
//  у корневого элемента ConstraintLayout высоту по контексту
// in activity_main.xml добавить recyclerview - прикрепить к экрану
//ширина и высота 0
// добавить переменную в MainActivity
// необходимо создать заметки в MainActivity - arraylist -12
//для тестирования добавить заметки - 20
//чтобы соединить ресайклаью и элементы массива 20-26 нажл создать адаптер -27
//создается новый класс, который будет адаптером - NotesAdapter
//внутри класса NotesAdapter создается еще лдин класс NotesViewHolder
//у конструктора родительского класса нет конструктора без параметров - поэтому
// class NotesViewHolder extends RecyclerView.ViewHolder -> ctrl + O или по подсказке
//добавить в класс NotesViewHolder ссылки на объекты textView
// присваиваем значения переменным в конструкторе NotesViewHolder
// вызываем findview... в качестве параметра конструктора itemView
//public class NotesAdapter extends RecyclerView.Adapter<...>
//переопределить методы
//получили вью, теперь на его основе создаем и возвоащаем new NotesViewHolder - 20
//заполняем вью элементами массива  Note note = notes.get(position);
//holder.textViewPriority.setText("" + note.getPriority());/// check test!!!!
//getItemCount() возвращает количество элементов в массиве
// создаем NotesAdapter adapter = new NotesAdapter(notes);
// как расположить адаптер hor ver grid - > recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
// затем установить у RecyclerView только что созданный адаптер
// recyclerViewNotes.setAdapter(adapter);
//
//изменяем цвет титл в зависимости от приорити - удаляем текствью приорити
// создаем в onBindViewHolder переменную, которая будет хранить приоритет - int colorId
////после того, как получили colorId - устанавливаем background
// добавляем кнопку "добавить заметку" floating action button
//при клике на кнопку откроется новая активность
//по кнопке заметки будут добавляться в аррайлист notes - меняем на public
// чтобы не нужно было создавать новый объект main активити - меняем его на static
//выбор дня недели - контейнер спиннер in activity_add_note.xml
//кнопки выбора приоритета добавляются в радиогруппу
// recyclerview adapter не имеет onitemclicklistener - надо создать его самостоятельно -
// делаем интерфейс - он будет слушать щелчки по элементам - onNoteClickListener
// в адаптере создаем объект слушателя - private onNoteClickListener onNoteClickListener;
// и добавляем сеттер на него
//когда кликаем на какуюто заметку - этой заметкой является itemView аргумент конструктора
// (  public NotesViewHolder(@NonNull View itemView) {  ), а у него можно вызвать setOnclickListener
//onClick вызывается при нажатии на itemView
//создаем слушатель в mainactivity
//10.6 // и присвоим значение в методе onCreate, плзтому поздаем базу данных


//10_5_15-51     //создание объекта помощника DB
// private NotesDBHelper dbHelper;
// теперь из хелпера можно получить базу данных
// взять инфо из notes и в цикле записать в базу данных
//для того, чтобы вставить запись в таблицу понядобится объект класса ContentValues
//вставить заголовок заметки
//теперь объект contentValues необходимо вставить в базу данных


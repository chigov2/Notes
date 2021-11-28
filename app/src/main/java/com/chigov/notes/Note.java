package com.chigov.notes;

public class Note {
    //заметка будет содержать название...
    private String title;
    private String description;
    private String dayOfWeek;
    private int priority;
    //add constructor
    public Note(String title, String description, String dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }
    //add getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
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
    //
}

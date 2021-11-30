package com.chigov.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private ArrayList<Note> notes;//массив заметок    // присваиваем значения переменным в конструкторе

    private onNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(NotesAdapter.onNoteClickListener onNoteClickListener) {//setter on onNoteClickListener
        this.onNoteClickListener = onNoteClickListener;
    }

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    interface onNoteClickListener {
        void onNoteClick(int position);//возвращать будет номер позиции нажатия
        // а определяться будет в анонимном класса в mainactivity
        void onLongClick(int position);//добавить  в NotesViewHolder
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;// вэтом методе надо взять макет, созданный для каждой заметки и
                    // передать его в качестве аргумента в конструктор NotesViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        //получили вью, теперь на его основе создаем NotesViewHolder
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        //в адаптере нет массивов - надо создать -private ArrayList<Note> notes;
        // и дать значения массивов в конструкторе Alt +Ins
        //заполнение массива
        Note note = notes.get(position);//позиция в массиве
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(Note.getDayAsString(position));
        //holder.textViewDayOfWeek.setText(note.getDayOfWeek());
        //holder.textViewPriority.setText("" + note.getPriority());/// check test!!!!
        int colorId;
        int priority = note.getPriority();
        switch(priority){
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                //установка цвета, если находишся не в главной активити
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_blue_light);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        //после того, как получили colorId - устанавливаем background
        holder.textViewTitle.setBackgroundColor(colorId);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }//возвращает количество элементов в массиве

    class NotesViewHolder extends RecyclerView.ViewHolder{//объект этого класса будет содержать все видимые заметки
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;
        //private TextView textViewPriority;

        //constructor
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            //объект этого класса будет содержать все видимые заметки
            // присваиваем значения переменным в конструкторе
            // вызываем findView... в качестве параметра конструктора itemView
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            //textViewPriority = itemView.findViewById(R.id.textViewPriority);
            itemView.setOnClickListener(new View.OnClickListener(){// и передаем анонимный класс
                @Override
                public void onClick(View v) {//onClick вызывается при нажатии на itemView
                    if (onNoteClickListener != null){//проверка существует ли объект интерфейса onNoteClickListener
                        onNoteClickListener.onNoteClick(getAdapterPosition());//номер позиции адаптера getAdapterPosition()
                    }
                }
            });// и передаем анонимный класс
            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if (onNoteClickListener != null){//проверка существует ли объект интерфейса onNoteClickListener
                        onNoteClickListener.onLongClick(getAdapterPosition());//номер позиции адаптера getAdapterPosition()
                    }
                    return true;
                }
            });

        }
    }
}

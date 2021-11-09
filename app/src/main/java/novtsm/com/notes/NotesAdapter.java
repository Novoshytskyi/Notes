package novtsm.com.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    // Хранение заметок
    private ArrayList<Note> notes;

    // Конструктор для присвоения значения для notes
    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    // Сеттер для onNoteClickListener
    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    // Создание объекта слушателя 2)
    private OnNoteClickListener onNoteClickListener;


    // Создание интерфейса 1)
    interface OnNoteClickListener{
        void onNoteClick(int position); // Нажатие
        void onLongClick(int position); // Долгое нажатие
    }

    // 2) ------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Макет заметки нужно передать в качестве аргумента в конструктор NotesViewHolder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        Note note = notes.get(i); // Заметка с индексом
        // Присвоение значений полям notesViewHolder
        notesViewHolder.textViewTitle.setText(note.getTitle());
        notesViewHolder.textViewDescription.setText(note.getDescription());
        notesViewHolder.textViewDayOfWeek.setText(note.getDayOfWeek());
        // Переменная для хранения Id цвета
        int colorId = notesViewHolder.itemView.getResources().getColor(R.color.white);
        // Переменная для приоритета
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            case 3:
                colorId = notesViewHolder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        // Установка цвета
        notesViewHolder.textViewTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size(); // Возвращает количество элементов в массиве
    }
    //---------------------------------------------------------------------------------------------

    // 1) -----------------------------------------------------------------------------------------
    class NotesViewHolder extends RecyclerView.ViewHolder {

        // Ссылки на все видимые части заметки
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;

        // Конструктор родительского класса. Содержит все видимые части заметки
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Присвоение значений всм видимым частям заметки
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            // Слушатель нажатия на элемент. В качестве аргумента анонимный класс
            itemView.setOnClickListener(new View.OnClickListener() {
                // Метод, который вызывается по клику на заметку, т.е. itemView
                @Override
                public void onClick(View v) {
                    if(onNoteClickListener != null){ // Существует ли слушатель
                        // Аргумент - номер позиции элемента
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            // Слушатель долгого нажатия на элемент
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onNoteClickListener != null){
                        // Аргумент - номер позиции адаптера
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    }
                    return true; // Если false - то сработает метод onLongClick и onClick
                }
            });
        }
    }
    // --------------------------------------------------------------------------------------------
}

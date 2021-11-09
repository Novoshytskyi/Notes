package novtsm.com.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    // Добавляем созданный адаптер
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        if(notes.isEmpty()){
            notes.add(new Note("Проходить курс Android", "Пройти все разделы", "Понедельник", 3));
            notes.add(new Note("Проходить курс Kotlin 1", "Пройти все разделы", "Вторник", 2));
            notes.add(new Note("Проходить курс Kotlin 2", "Пройти все разделы", "Среда", 2));
            notes.add(new Note("YouTube", "Слушать ролики", "Четверг", 3));
            notes.add(new Note("rabota.ua", "Обновить резюме", "Пятница", 2));
            notes.add(new Note("rabota.ua", "Откликнуться на вакансию", "Понедельник", 1));
            notes.add(new Note(".....", "Пройти собеседование", "Вторник", 1));
            notes.add(new Note(".....", "Приступить к работе", "Среда", 1));
        }

        // Добавляем созданный адаптер
        adapter = new NotesAdapter(notes);

        // Указание RecyclerView как располагать элементы
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this)); // Обычное расположение

        // Установка у RecyclerView адаптера
        recyclerViewNotes.setAdapter(adapter);

        // Установка слушателя у адаптера
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {

            @Override
            public void onNoteClick(int position) {
                // Вывод номера нажатой позиции
                // Toast.makeText(MainActivity.this, "Номер позиции: " + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Clicked. Position: " + position, Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });
        // Для использования свайпа влево и вправо
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override // Выполняется при свайпе
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { // int direction - направление смахивания
                remove(viewHolder.getAdapterPosition());
            }
        });
        // Применение созданного объекта к RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    // Метод для удаления элемента по позиции
    private void remove(int position){
        // Удаление из списка элемента, на который нажали
        notes.remove(position);
        // Сообщение адаптеру, что элемент удален
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Deleted. Position: " + position, Toast.LENGTH_SHORT).show();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
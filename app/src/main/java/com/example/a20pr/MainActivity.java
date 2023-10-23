package com.example.a20pr;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private Button saveButton, deleteButton;
    private TextView listTextView;
    private List<String> savedItems;
    private DatabaseReference myRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем экземпляр базы данных
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("users2");

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        listTextView = findViewById(R.id.listTextView);

        savedItems = new ArrayList<>();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String text3 = editText3.getText().toString();
                String text4 = editText4.getText().toString();
                String text5 = editText5.getText().toString();
                String text6 = editText6.getText().toString();

                // Создаем новый объект пользователя
                User user = new User(text1, text2, text3, text4, text5, text6);

                // Генерируем уникальный ключ для нового объекта пользователя
                String key = myRef1.push().getKey();

                // Сохраняем объект пользователя в базе данных по сгенерированному ключу
                myRef1.child(key).setValue(user);


                // Сохраняем введенные данные
                savedItems.add("Имя: " + text1);
                savedItems.add("Фамилия : " + text2);
                savedItems.add("Номер телефона : " + text3);
                savedItems.add("Почта : " + text4);
                savedItems.add("Логин : " + text5);
                savedItems.add("Пароль : " + text6);

                // Обновляем текст в TextView с сохраненными данными
                updateListTextView();

                // Очищаем поля ввода
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
                editText6.setText("");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!savedItems.isEmpty()) {
                    // Получаем последний сохраненный элемент (уникальный ключ)
                    String lastKey = savedItems.get(savedItems.size() - 1);

                    // Удаляем элемент из базы данных
                    myRef1.child(lastKey).removeValue();

                    // Удаляем ключ из списка сохраненных элементов
                    savedItems.remove(savedItems.size() - 1);

                    // Обновляем текст в TextView после удаления
                    updateListTextView();
                }
            }
        });
    }

    private void updateListTextView() {
        StringBuilder sb = new StringBuilder();
        for (String item : savedItems) {
            sb.append(item).append("\n");
        }
        listTextView.setText(sb.toString());
    }
}

    class User {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String username;
    public String password;

    public User() {
        // Пустой конструктор требуется для использования с Firebase
    }

    public User(String firstName, String lastName, String phoneNumber, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

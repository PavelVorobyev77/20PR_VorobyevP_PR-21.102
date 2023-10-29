package com.example.a20pr;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private Button saveButton, deleteButton;
    private ListView listView;
    private List<String> savedItems;
    private DatabaseReference myReff;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем экземпляр базы данных
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myReff = database.getReference("user5");

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        savedItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedItems);
        listView.setAdapter(adapter);

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
                User user = new User(text1,text2,text3,text4,text5,text6);

                // Генерируем уникальный ключ для нового объекта пользователя
                String key = myReff.push().getKey();

                // так тоже работает добавление
                //myReff.child(key).setValue(user);

                // Добавляем нового пользователя
                myReff.push().setValue(user);

                // Сохраняем введенные данные в одной строке
                String item = "Имя: " + text1 + "\nФамилия: " + text2 + "\nТелефон: " + text3
                        + "\nПочта: " + text4 + "\nЛогин: " + text5 + "\nПароль: " + text6;

                // Добавляем данные в список
                savedItems.add(item);

                // Обновляем адаптер списка
                adapter.notifyDataSetChanged();

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
                    // Получаем последний сохраненный элемент
                    String lastItem = savedItems.get(savedItems.size() - 1);

                    // Удаляем элемент из базы данных
                    myReff.child(lastItem).removeValue();

                    // Удаляем элемент из списка
                    savedItems.remove(savedItems.size() - 1);

                    // Обновляем адаптер списка
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public class User {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String username;
        private String password;

        public User(String firstName, String lastName, String phoneNumber, String email, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.username = username;
            this.password = password;
        }
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}


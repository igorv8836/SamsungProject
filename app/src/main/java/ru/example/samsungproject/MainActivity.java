package ru.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        //проверка на авторизацию, есть ли авторизация, либо null
        FirebaseUser currentUser = mAuth.getCurrentUser(); //текущий пользователь
        if (currentUser != null) {
            Toast.makeText(this, "Вы авторизованы", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, RegisterEmailActivity.class);
            //startActivity(intent);
        } else {
            Toast.makeText(this, "Вы не авторизованы!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivityForResult(intent, 1);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
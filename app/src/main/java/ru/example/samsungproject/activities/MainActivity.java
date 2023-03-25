package ru.example.samsungproject.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.activities.authentication.AuthActivity;
import ru.example.samsungproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;


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
        } else {
            Toast.makeText(this, "Вы не авторизованы!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }

        actionBar = getSupportActionBar();
        if (actionBar.isShowing())
            actionBar.hide();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
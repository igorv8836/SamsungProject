package ru.example.samsungproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.R;
import ru.example.samsungproject.activities.authentication.AuthActivity;
import ru.example.samsungproject.databinding.ActivityMainBinding;
import ru.example.samsungproject.fragments.basic.SettingsFragment;

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

        binding.navMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_news:
                        Navigation.findNavController(binding.getRoot())
                                .navigate(R.id.action_to_NewsFragment);
                        return true;
                    case R.id.menu_search:
                        Navigation.findNavController(binding.getRoot())
                                .navigate(R.id.action_to_SearchEventsFragment);
                        return true;
                    case R.id.menu_events:
                        Navigation.findNavController(binding.getRoot())
                                .navigate(R.id.action_to_MyEventsFragment);
                        return true;
                    case R.id.menu_calendar:
                        Navigation.findNavController(binding.getRoot())
                                .navigate(R.id.action_to_CalendarFragment);
                        return true;
                    case R.id.menu_settings:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SettingsFragment settingsFragment = new SettingsFragment();
                        fragmentTransaction.add(R.id.fragment_container, settingsFragment);
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
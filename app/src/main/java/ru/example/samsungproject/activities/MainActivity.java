package ru.example.samsungproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import ru.example.samsungproject.fragments.basic.CalendarFragment;
import ru.example.samsungproject.fragments.basic.MyEventsFragment;
import ru.example.samsungproject.fragments.basic.NewsFragment;
import ru.example.samsungproject.fragments.basic.SearchEventsFragment;
import ru.example.samsungproject.fragments.basic.SettingsFragment;
import ru.example.samsungproject.supportingClasses.PermissionUtils;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_STORAGE = 101;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;

    private NewsFragment newsFragment;
    private SearchEventsFragment searchEventsFragment;
    private MyEventsFragment myEventsFragment;
    private CalendarFragment calendarFragment;
    private SettingsFragment settingsFragment;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        newsFragment = new NewsFragment();
        searchEventsFragment = new SearchEventsFragment();
        myEventsFragment = new MyEventsFragment();
        calendarFragment = new CalendarFragment();
        settingsFragment = new SettingsFragment();

        if (!PermissionUtils.hasPermissions(MainActivity.this))
            PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);

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

//        actionBar = getSupportActionBar();
//        if (actionBar.isShowing())
//            actionBar.hide();

        fragmentManager = getSupportFragmentManager();
        startFirstFragment();

        binding.navMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_news:
                        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.fragment_container, newsFragment);
                        fragmentTransaction1.commit();
                        return true;

                    case R.id.menu_search:
                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                        fragmentTransaction2.replace(R.id.fragment_container, searchEventsFragment);
                        fragmentTransaction2.commit();
                        return true;


                    case R.id.menu_events:
                        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                        fragmentTransaction3.replace(R.id.fragment_container, myEventsFragment);
                        fragmentTransaction3.commit();
                        return true;



                    case R.id.menu_calendar:
                        FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                        fragmentTransaction4.replace(R.id.fragment_container, calendarFragment);
                        fragmentTransaction4.commit();
                        return true;



                    case R.id.menu_settings:
                        FragmentTransaction fragmentTransaction5 = fragmentManager.beginTransaction();
                        fragmentTransaction5.replace(R.id.fragment_container, settingsFragment);
                        fragmentTransaction5.commit();
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

    protected void startFirstFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, newsFragment);
        fragmentTransaction.commit();
    }

}
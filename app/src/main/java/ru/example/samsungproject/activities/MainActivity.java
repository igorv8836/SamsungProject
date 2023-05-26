package ru.example.samsungproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
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
import ru.example.samsungproject.fragments.basic.CalendarFragment;
import ru.example.samsungproject.fragments.basic.MyEventsFragment;
import ru.example.samsungproject.fragments.basic.NewsFragment;
import ru.example.samsungproject.fragments.basic.SearchEventsFragment;
import ru.example.samsungproject.fragments.basic.SettingsFragment;
import ru.example.samsungproject.repositories.FirestoreRepository;
import ru.example.samsungproject.supportingClasses.PermissionUtils;
import ru.example.samsungproject.viewModels.SettingsFragmentViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private static final int PERMISSION_STORAGE = 101;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        if (actionBar != null && actionBar.isShowing()) actionBar.hide();

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

        //if (!PermissionUtils.hasPermissions(MainActivity.this))
            //PermissionUtils.requestPermissions(MainActivity.this, PERMISSION_STORAGE);

        binding.navMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_news:
                        Navigation.findNavController(binding.navHostFragment)
                                .navigate(R.id.action_menu_1);
                        return true;

                    case R.id.menu_search:
                        Navigation.findNavController(binding.navHostFragment)
                                .navigate(R.id.action_menu_2);
                        return true;

                    case R.id.menu_events:
                        Navigation.findNavController(binding.navHostFragment)
                                .navigate(R.id.action_menu_3);
                        return true;

//                    case R.id.menu_calendar:
//                        Navigation.findNavController(binding.navHostFragment)
//                                .navigate(R.id.action_menu_4);
//                        return true;

                    case R.id.menu_settings:
                        Navigation.findNavController(binding.navHostFragment)
                                .navigate(R.id.action_menu_5);
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
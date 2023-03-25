package ru.example.samsungproject.activities.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ru.example.samsungproject.activities.MainActivity;
import ru.example.samsungproject.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        if (actionBar.isShowing()){
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();
        intent = getIntent();
        if (!intent.getStringExtra("Email").isEmpty())
            binding.editTextEmailInputText.setText(intent.getStringExtra("Email"));

    }

    public void registerAccount(View view) {
        String email = binding.editTextEmailInputText.getText().toString().trim();
        String password = binding.editTextPasswordInputText.getText().toString().trim();
        Log.i("login", email);
        Log.i("password", password);
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Вы ничего не ввели!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    binding.textView.setText("Ошибка: " + task.getException().toString());
                }
            }
        });
    }
}
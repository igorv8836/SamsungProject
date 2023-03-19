package ru.example.samsungproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import ru.example.samsungproject.databinding.ActivityRecoverPasswordBinding;

public class RecoverPasswordActivity extends AppCompatActivity {

    private Intent intent;
    private String email;
    private FirebaseAuth mAuth;

    private ActivityRecoverPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecoverPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar.isShowing())
            actionBar.hide();

        intent = getIntent();
        email = intent.getStringExtra("Email");
        if (!email.isEmpty()) {
            binding.editTextEmailInputTextRecover.setText(email);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    public void recover(View view) {
        email = binding.editTextEmailInputTextRecover.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(RecoverPasswordActivity.this, "Вы ничего не ввели", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecoverPasswordActivity.this, "Письмо со ссылкой отправлено на почту", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }
}
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.example.samsungproject.activities.MainActivity;
import ru.example.samsungproject.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        intent = getIntent();
        if (!intent.getStringExtra("Email").isEmpty())
            binding.editTextEmailInputText.setText(intent.getStringExtra("Email"));

    }

    public void registerAccount(View view) {
        String email = binding.editTextEmailInputText.getText().toString().trim();
        String password = binding.editTextPasswordInputText.getText().toString().trim();
        String name = binding.editTextNicknameInputText.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()){
            Toast.makeText(this, "Вы ничего не ввели!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    CollectionReference collection = firebaseFirestore.collection("users");
                    DocumentReference newDocRef = collection.document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                    Map<String, String> data = new HashMap<>();
                    data.put("Email", email);
                    data.put("Name", name);

                    newDocRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            binding.textView.setText("Ошибка: " + task.getException().toString());
                        }
                    });
                } else{
                    RegisterError(task.getException());
                }
            }
        });
    }

    public void RegisterError(Exception e){
        Toast.makeText(this, "Ошибка " + e.toString(), Toast.LENGTH_SHORT).show();
    }
}
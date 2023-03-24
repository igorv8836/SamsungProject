package ru.example.samsungproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ru.example.samsungproject.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    private FirebaseAuth mAuth;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        if (actionBar.isShowing()){
            actionBar.hide();
        }
        mAuth = FirebaseAuth.getInstance();

    }

    public void enterInAccount(View view) {
        String email = binding.editTextEmailInputText.getText().toString().trim();
        String password = binding.editTextPasswordInputText.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(AuthActivity.this, "Вы ничего не ввели!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AuthActivity.this, "Ошибка: " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registerAccount(View view) {
        Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
        intent.putExtra("Email", binding.editTextEmailInputText.getText().toString().trim());
        startActivity(intent);
    }

    public void recoverPassword(View view) {
        Intent intent = new Intent(AuthActivity.this, RecoverPasswordActivity.class);
        intent.putExtra("Email", binding.editTextEmailInputText.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() != null){
            finish();
        }
    }
}
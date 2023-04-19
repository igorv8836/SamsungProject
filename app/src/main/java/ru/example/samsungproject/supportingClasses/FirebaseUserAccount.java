package ru.example.samsungproject.supportingClasses;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.example.samsungproject.interfaces.OnPasswordChangedListener;
import ru.example.samsungproject.interfaces.OnReauthenticatedListener;

public class FirebaseUserAccount {

    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;

    public FirebaseUserAccount() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void ChangePassword(OnPasswordChangedListener listener, String password){
        if (password.isEmpty() || password.length() < 6)
            listener.onPasswordNotChanged(new NullPointerException());
        else {
            currentUser.updatePassword(password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            listener.OnPasswordChanged();
                        else
                            listener.onPasswordNotChanged(task.getException());
                    });
        }
    }

    public void Reauthenticate(String password, OnReauthenticatedListener listener){
        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), password);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        listener.OnReauthenticated();
                    else
                        listener.OnNotReauthenticated(task.getException());
                });
    }
}

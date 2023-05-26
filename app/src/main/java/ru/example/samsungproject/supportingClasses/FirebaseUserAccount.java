package ru.example.samsungproject.supportingClasses;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.example.samsungproject.interfaces.UserListener.OnPasswordChangedListener;
import ru.example.samsungproject.interfaces.UserListener.OnReauthenticatedListener;

public class FirebaseUserAccount {

    private final FirebaseAuth mAuth;
    private final FirebaseUser currentUser;
    private static FirebaseUserAccount instance;
    private FirebaseUserAccount() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public static synchronized FirebaseUserAccount getInstance(){
        if (instance == null)
            instance = new FirebaseUserAccount();
        return instance;
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
        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(currentUser.getEmail()), password);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        listener.OnReauthenticated();
                    else
                        listener.OnNotReauthenticated(task.getException());
                });
    }

    public void SignOut(){
        mAuth.signOut();
    }
}

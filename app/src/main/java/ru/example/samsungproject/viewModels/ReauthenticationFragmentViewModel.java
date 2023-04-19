package ru.example.samsungproject.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.example.samsungproject.interfaces.OnReauthenticatedListener;
import ru.example.samsungproject.supportingClasses.FirebaseUserAccount;

public class ReauthenticationFragmentViewModel extends ViewModel {
    public MutableLiveData<Boolean> reauthCheck = new MutableLiveData<>();

    FirebaseUserAccount firebaseUserAccount = new FirebaseUserAccount();
    public void ReLogin(String password){
        firebaseUserAccount.Reauthenticate(password, new OnReauthenticatedListener() {
            @Override
            public void OnReauthenticated() {
                reauthCheck.setValue(true);
            }

            @Override
            public void OnNotReauthenticated(Exception e) {
                reauthCheck.setValue(false);
            }
        });
    }
}

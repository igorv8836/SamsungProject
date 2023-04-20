package ru.example.samsungproject.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.Arrays;

import ru.example.samsungproject.interfaces.OnProfileLoadedListener;
import ru.example.samsungproject.repositories.FirestoreRepository;
import ru.example.samsungproject.supportingClasses.FirebaseUserAccount;

public class SettingsFragmentViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    public MutableLiveData<String[]> data;

    public SettingsFragmentViewModel() {
        this.firestoreRepository = new FirestoreRepository();
        data = new MutableLiveData<>();
    }

    public void LoadUserData(){
        firestoreRepository.LoadSimpleProfileFromFirebase(new OnProfileLoadedListener() {
            @Override
            public void OnProfileLoaded(String name, String email) {
                data.setValue(new String[]{email, name});
            }

            @Override
            public void OnProfileNotLoaded(Exception e) {
                Log.i("TAG", "имя и фамилия не загружены в настройках");
            }
        });
    }

    public void SignOut(){
        FirebaseUserAccount.getInstance().SignOut();
    }
}

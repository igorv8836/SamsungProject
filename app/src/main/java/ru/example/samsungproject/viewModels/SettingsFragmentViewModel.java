package ru.example.samsungproject.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import ru.example.samsungproject.interfaces.OnProfileLoadedListener;
import ru.example.samsungproject.repositories.FirestoreRepository;

public class SettingsFragmentViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    public MutableLiveData<String> Email;
    public MutableLiveData<String> Name;

    public SettingsFragmentViewModel() {
        this.firestoreRepository = new FirestoreRepository();
        Email = new MutableLiveData<>(null);
        Name = new MutableLiveData<>(null);
    }

    public void LoadUserData(){
        firestoreRepository.LoadSimpleProfileFromFirebase(new OnProfileLoadedListener() {
            @Override
            public void OnProfileLoaded(String name, String email) {
                Email.setValue(email);
                Name.setValue(name);
            }

            @Override
            public void OnProfileNotLoaded(Exception e) {
                Log.i("TAG", "имя и фамилия не загружены в настройках");
            }
        });
    }
}

package ru.example.samsungproject.viewModels;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.example.samsungproject.fragments.fragmentsOfSettings.ProfileFragment;
import ru.example.samsungproject.interfaces.OnNameSendedListener;
import ru.example.samsungproject.repositories.FirestoreRepository;

public class ProfileFragmentViewModel extends ViewModel {

    public MutableLiveData<String[]> data = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadedCheck = new MutableLiveData<>();
    public MutableLiveData<Boolean> sendedName = new MutableLiveData<>();
    public MutableLiveData<Boolean> notSendedName = new MutableLiveData<>();
    public FirestoreRepository repository = new FirestoreRepository();



    public void startFragment(Bundle bundle){
        if (bundle.get("Email") != null && bundle.get("Name") != null) {
            this.data.setValue(new String[]{
                    bundle.get("Email").toString(),
                    bundle.get("Name").toString()});
        } else{
            loadedCheck.setValue(false);
        }
    }

    public void setNewName(String name){
        repository.SendName(new OnNameSendedListener() {
            @Override
            public void onNameSended() {
                sendedName.setValue(true);
            }

            @Override
            public void onNameNotSended() {
                notSendedName.setValue(true);
            }
        }, name);
    }

    public void setNewPassword(String password){

    }




}

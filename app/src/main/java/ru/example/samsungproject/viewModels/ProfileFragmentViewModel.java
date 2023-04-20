package ru.example.samsungproject.viewModels;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;

import ru.example.samsungproject.fragments.fragmentsOfSettings.ProfileFragment;
import ru.example.samsungproject.interfaces.OnNameSendedListener;
import ru.example.samsungproject.interfaces.OnPasswordChangedListener;
import ru.example.samsungproject.repositories.FirestoreRepository;
import ru.example.samsungproject.supportingClasses.FirebaseUserAccount;

public class ProfileFragmentViewModel extends ViewModel {

    public MutableLiveData<String[]> profileData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadedCheck = new MutableLiveData<>(); //проверка, загружены ли данные с прошлого фрагмента
    public MutableLiveData<Boolean> sendedName = new MutableLiveData<>(); //проверка, поменялось ли имя на сервере
    public MutableLiveData<Boolean> sendedPassword = new MutableLiveData<>(); //проверка, поменялся ли пароль на сервере
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public FirestoreRepository repository = new FirestoreRepository();
    public FirebaseUserAccount firebaseUserAccount = FirebaseUserAccount.getInstance();



    public void startFragment(Bundle bundle){
        if (bundle.get("Email") != null && bundle.get("Name") != null) {
            this.profileData.setValue(new String[]{
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
                sendedName.setValue(false);
            }
        }, name);
    }

    public void setNewPassword(String password){
        firebaseUserAccount.ChangePassword(new OnPasswordChangedListener() {
            @Override
            public void OnPasswordChanged() {
                sendedPassword.setValue(true);
            }

            @Override
            public void onPasswordNotChanged(Exception e) {
                sendedPassword.setValue(false);
                Log.i("TAG", e.toString());
                if (e instanceof FirebaseAuthRecentLoginRequiredException) {
                    passwordError.setValue("Нужна повторная аутентификация!");
                    Log.i("TAG", "ошибка недавней аутентификации");
                }

            }
        }, password);
    }




}

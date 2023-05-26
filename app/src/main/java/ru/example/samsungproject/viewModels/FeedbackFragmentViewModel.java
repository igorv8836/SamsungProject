package ru.example.samsungproject.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.example.samsungproject.interfaces.UserListener.OnFeedbackSendedListener;
import ru.example.samsungproject.repositories.FirestoreRepository;

public class FeedbackFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> sendedCheck;
    FirestoreRepository firestoreRepository;

    public FeedbackFragmentViewModel() {
        this.sendedCheck = new MutableLiveData<>();
        this.firestoreRepository = new FirestoreRepository();
    }

    public void sendFeedback(String topic, String text){
        firestoreRepository.SendFeedback(new OnFeedbackSendedListener() {
            @Override
            public void onFeedbackSended() {
                sendedCheck.setValue(true);
            }

            @Override
            public void onFeedbackNotSended(Exception e) {
                sendedCheck.setValue(false);
            }
        }, topic, text);
    }
}

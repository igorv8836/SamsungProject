package ru.example.samsungproject.viewModels;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class TasksFragmentViewModel extends ViewModel {
    private String eventId;





    public void receptionEventInfo(Bundle bundle){
        eventId = bundle.getString("id");
    }
}

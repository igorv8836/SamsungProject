package ru.example.samsungproject.viewModels;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnAddedTasksListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnChangedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnDeletedTaskListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnLoadedEventListener;
import ru.example.samsungproject.interfaces.EventsListeners.OnTaskButtonListener;
import ru.example.samsungproject.repositories.FirestoreEventsRepository;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.Task;

public class TasksFragmentViewModel extends ViewModel implements OnTaskButtonListener {
    private String eventId;
    private Event event;
    public MutableLiveData<List<Task>> data = new MutableLiveData<>();
    private final FirestoreEventsRepository repository = new FirestoreEventsRepository();





    public void receptionEventInfo(Bundle bundle){
        eventId = bundle.getString("id");
        repository.loadEvent(new OnLoadedEventListener() {
            @Override
            public void onLoadedEvent(Event event1) {
                event = event1;
                loadTasks();
            }

            @Override
            public void onNotLoadedEvent(String message) {

            }
        }, eventId);
    }

    public void addNewTask(){
        List<Task> temp = data.getValue();
        Task task = new Task("","", "", 0, "", true);
        temp.add(task);
        repository.addTask(new OnAddedTaskListener() {
            @Override
            public void OnAddedTask(String id, String author) {
                task.setId(id);
                task.setAuthor(author);
                data.setValue(temp);
            }

            @Override
            public void OnNotAddedTask() {

            }
        }, eventId, "", "", "", 0);
    }

    public void loadTasks() {
        repository.loadTasks(new OnAddedTasksListener() {
            @Override
            public void OnAddedTasks(List<Task> data1) {
                data.setValue(data1);
            }

            @Override
            public void OnNotAddedTasks() {

            }
        }, eventId);
    }

    public void changeTask(){

    }

    public void deleteTask(){

    }

    @Override
    public void onPressed(String taskId, String title, String description, String author, int price, int percent, boolean isCompleted, boolean isSave) {
        if (isSave){
            repository.changeTask(new OnChangedTaskListener() {
                @Override
                public void OnChangedTask() {

                }

                @Override
                public void OnNotChangedTask() {

                }
            }, taskId, eventId, title, description, price, isCompleted, percent);
        }
    }

    @Override
    public void changeCompleted(String taskId, int percent, boolean isCompleted) {
        if (percent == 100)
            repository.changeCompleted(taskId, eventId, percent, true);
        else
            repository.changeCompleted(taskId, eventId, percent, isCompleted);
    }

    @Override
    public void deleteTask(String taskId) {
        repository.deleteTask(new OnDeletedTaskListener() {
            @Override
            public void OnDeletedTask() {
                List<Task> temp = data.getValue();
                for (Task task: temp)
                    if (Objects.equals(task.getId(), taskId)) {
                        temp.remove(task);
                        break;
                    }
                data.setValue(temp);
            }

            @Override
            public void OnNotDeletedTask() {

            }
        }, taskId, eventId);
    }
}

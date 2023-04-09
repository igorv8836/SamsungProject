package ru.example.samsungproject.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

//import ru.example.samsungproject.sql.NewsDB;
//import ru.example.samsungproject.sql.NewsDao;
import ru.example.samsungproject.repositories.FirestoreRepository;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.interfaces.OnNewsLoadedListener;

public class NewsFragmentViewModel extends ViewModel {

    public MutableLiveData<ArrayList<NewsElement>> news = new MutableLiveData<>();
    private final FirestoreRepository newsRepository;

    public NewsFragmentViewModel() {
        newsRepository = new FirestoreRepository();
    }

    public void LoadNewsData(){
        newsRepository.LoadNewsFromFirebase(new OnNewsLoadedListener() {
            @Override
            public void onNewsLoaded(ArrayList<NewsElement> newsElements) {
                news.setValue(newsElements);
            }

            @Override
            public void onNewsNotLoaded() {

            }
        });
    }

}

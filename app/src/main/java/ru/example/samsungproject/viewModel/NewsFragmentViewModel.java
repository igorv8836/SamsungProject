package ru.example.samsungproject.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

//import ru.example.samsungproject.sql.NewsDB;
//import ru.example.samsungproject.sql.NewsDao;
import ru.example.samsungproject.fragments.basic.NewsFragment;
import ru.example.samsungproject.repositories.NewsRepository;
import ru.example.samsungproject.supportingClass.NewsElement;
import ru.example.samsungproject.supportingClass.OnNewsLoadedListener;

public class NewsFragmentViewModel extends ViewModel {

    public MutableLiveData<ArrayList<NewsElement>> news = new MutableLiveData<>();
    public final NewsRepository newsRepository;

    public NewsFragmentViewModel(Application application) {
        newsRepository = new NewsRepository(application);
    }

    public void LoadNewsData(){
        newsRepository.LoadNewsFromNetwork(new OnNewsLoadedListener() {
            @Override
            public void onNewsLoaded(ArrayList<NewsElement> newsElements) {
                news.setValue(newsElements);
            }
        });
    }

}

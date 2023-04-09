package ru.example.samsungproject.viewModels;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

//import ru.example.samsungproject.sql.NewsDB;
//import ru.example.samsungproject.sql.NewsDao;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.example.samsungproject.repositories.NewsRepository;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.supportingClasses.OnNewsLoadedListener;

public class NewsFragmentViewModel extends ViewModel {

    public MutableLiveData<ArrayList<NewsElement>> news = new MutableLiveData<>();
    public final NewsRepository newsRepository;

    public NewsFragmentViewModel() {
        newsRepository = new NewsRepository();
    }

    public void LoadNewsData(){
        Disposable disposable;
        disposable = newsRepository.LoadNewsFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::LoadedNewsData);
    }

    public void LoadedNewsData(ArrayList<NewsElement> data){
        news.setValue(data);
    }

}

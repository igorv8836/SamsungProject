package ru.example.samsungproject.repositories;

import android.app.Application;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.example.samsungproject.Database.AppDB;
import ru.example.samsungproject.Database.FirestoreDB;
import ru.example.samsungproject.supportingClasses.NewsElement;
import ru.example.samsungproject.supportingClasses.OnNewsLoadedListener;

public class NewsRepository{
    private FirestoreDB firestoreDB;

    public NewsRepository() {
        firestoreDB = new FirestoreDB();
    }

    public Observable<ArrayList<NewsElement>> LoadNewsFromNetwork() {
        return Observable.create(new ObservableOnSubscribe<ArrayList<NewsElement>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<NewsElement>> emitter) throws Exception {
                firestoreDB.LoadNews(new OnNewsLoadedListener() {
                    @Override
                    public void onNewsLoaded(ArrayList<NewsElement> newsElements) {
                        emitter.onNext(newsElements);
                        emitter.onComplete();
                    }

                    @Override
                    public void onNewsNotLoaded() {
                        emitter.onError(new Exception("News not loaded"));
                    }
                });
            }
        });
    }
}


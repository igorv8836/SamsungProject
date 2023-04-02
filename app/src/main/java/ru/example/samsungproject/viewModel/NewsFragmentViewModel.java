package ru.example.samsungproject.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//import ru.example.samsungproject.sql.NewsDB;
//import ru.example.samsungproject.sql.NewsDao;
import ru.example.samsungproject.supportingClass.NewsElement;

public class NewsFragmentViewModel extends ViewModel {

    public MutableLiveData<ArrayList<NewsElement>> news = new MutableLiveData<>();


    //private Fragment fragment;
    //private NewsDB newsDB;
    //private NewsDao newsDao;
    //Disposable newsDisposable;

    //public NewsFragmentViewModel() {
        //this.fragment = fragment;
        //newsDB = NewsDB.getInstance(fragment.requireContext().getApplicationContext());
        //newsDao = newsDB.newsDao();
    //}

    public void LoadNewsFromLocal(){
//        newsDisposable = newsDao.getAllNews()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::LoadedNewsFromLocal);
    }

    void LoadedNewsFromLocal(ArrayList<NewsElement> data){
        news.setValue(data);
    }

    public void DownloadNews(){
        ArrayList<NewsElement> list = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (!(task.getResult().isEmpty())){
                                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                    list.add(new NewsElement(
                                            documentSnapshot.get("Title").toString(),
                                            documentSnapshot.get("Description").toString(),
                                            documentSnapshot.get("Date").toString()));
                                    Log.w("TAG", documentSnapshot.getId() + " --- " + documentSnapshot.getData());
                                }
                                news.setValue(list);
                            }
                        }
                    }
                });
    }

}

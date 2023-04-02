package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.example.samsungproject.adapters.NewsAdapter;
import ru.example.samsungproject.databinding.FragmentNewsBinding;
import ru.example.samsungproject.supportingClass.NewsElement;
import ru.example.samsungproject.viewModel.NewsFragmentViewModel;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsFragmentViewModel viewModel;
    NewsAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);

        ArrayList<NewsElement> newsElements = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            newsElements.add(new NewsElement(
                    "Обновление " + String.valueOf(i),
                    "fdsslkadjfkaspiowoqeruqjasdlfskdfjsdfsafsfjafskldhfiuehsdafs\nsadfasd\nsdfasdfewqrf\nasdfsadfasweqrqwefsad\nkslj",
                    "12.12.2020"
            ));

//        newsAdapter = new NewsAdapter(getActivity(), newsElements);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.recyclerView.setAdapter(newsAdapter);

        viewModel.news.observe(getViewLifecycleOwner(), news -> {
            Log.w("TAG", news.toString());
            if (!news.isEmpty()) {
                newsAdapter = new NewsAdapter(getActivity(), news);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerView.setAdapter(newsAdapter);
//                newsElements.clear();
//                for(NewsElement element: news)
//                    newsElements.add(element);
//                newsAdapter.setData(news);
//                newsAdapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.LoadNewsFromLocal();
        viewModel.DownloadNews();
    }
}
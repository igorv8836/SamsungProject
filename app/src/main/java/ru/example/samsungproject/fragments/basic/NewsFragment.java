package ru.example.samsungproject.fragments.basic;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
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
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NewsFragmentViewModel(getActivity().getApplication());
            }
        }).get(NewsFragmentViewModel.class);

        viewModel.LoadNewsData();

        viewModel.news.observe(getViewLifecycleOwner(), news -> {
            Log.w("TAG", news.toString());
            if (!news.isEmpty()) {
                newsAdapter = new NewsAdapter(getActivity(), news);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerView.setAdapter(newsAdapter);
            }
        });
        return binding.getRoot();
    }
}
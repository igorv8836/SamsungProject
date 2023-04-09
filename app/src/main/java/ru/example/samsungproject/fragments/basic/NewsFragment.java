package ru.example.samsungproject.fragments.basic;

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

import ru.example.samsungproject.adapters.NewsAdapter;
import ru.example.samsungproject.databinding.FragmentNewsBinding;
import ru.example.samsungproject.viewModels.NewsFragmentViewModel;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsFragmentViewModel viewModel;
    NewsAdapter newsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
        viewModel.LoadNewsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);

        viewModel.news.observe(getViewLifecycleOwner(), news -> {
            Log.w("TAG", news.toString());
            if (!news.isEmpty()) {
                binding.progressBar.setVisibility(View.GONE);
                newsAdapter = new NewsAdapter(getActivity(), news);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.recyclerView.setAdapter(newsAdapter);
            }
        });
        return binding.getRoot();
    }
}
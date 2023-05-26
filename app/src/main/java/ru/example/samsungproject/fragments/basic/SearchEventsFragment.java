package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import ru.example.samsungproject.adapters.SearchEventAdapter;
import ru.example.samsungproject.databinding.FragmentSearchEventsBinding;
import ru.example.samsungproject.viewModels.MyEventsFragmentViewModel;
import ru.example.samsungproject.viewModels.SearchEventFragmentViewModel;

public class SearchEventsFragment extends Fragment {

    private FragmentSearchEventsBinding binding;
    private SearchEventFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SearchEventFragmentViewModel.class);

        binding.buttonSearch.setOnClickListener(t ->
                viewModel.searchEvents(Objects.requireNonNull(binding.editTextTitleInputText.getText()).toString()));

        viewModel.events.observe(getViewLifecycleOwner(), t -> {
            SearchEventAdapter adapter = new SearchEventAdapter(requireContext(), t, viewModel);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}
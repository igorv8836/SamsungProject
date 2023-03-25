package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.R;
import ru.example.samsungproject.databinding.FragmentSearchEventsBinding;
import ru.example.samsungproject.databinding.FragmentSettingsBinding;

public class SearchEventsFragment extends Fragment {

    private FragmentSearchEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchEventsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
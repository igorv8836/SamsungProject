package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.databinding.FragmentMyEventsBinding;


public class MyEventsFragment extends Fragment {

    private FragmentMyEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false);






        return binding.getRoot();
    }
}
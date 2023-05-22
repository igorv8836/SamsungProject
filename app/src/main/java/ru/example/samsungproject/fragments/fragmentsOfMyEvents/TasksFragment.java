package ru.example.samsungproject.fragments.fragmentsOfMyEvents;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import ru.example.samsungproject.databinding.FragmentTasksBinding;


public class TasksFragment extends Fragment {

    private FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);

        getArguments();


        return binding.getRoot();
    }
}
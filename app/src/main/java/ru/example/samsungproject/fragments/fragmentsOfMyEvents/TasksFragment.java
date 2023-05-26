package ru.example.samsungproject.fragments.fragmentsOfMyEvents;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.example.samsungproject.adapters.TasksAdapter;
import ru.example.samsungproject.databinding.FragmentTasksBinding;
import ru.example.samsungproject.viewModels.MyEventsFragmentViewModel;
import ru.example.samsungproject.viewModels.TasksFragmentViewModel;


public class TasksFragment extends Fragment  {

    private FragmentTasksBinding binding;
    private TasksFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TasksFragmentViewModel.class);

        viewModel.receptionEventInfo(getArguments());

        binding.buttonAddTask.setOnClickListener(t -> viewModel.addNewTask());

        viewModel.data.observe(getViewLifecycleOwner(), t -> {
            TasksAdapter adapter = new TasksAdapter(requireContext(), t, viewModel);
            binding.listOfTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.listOfTasks.setAdapter(adapter);
        });


        return binding.getRoot();
    }

}
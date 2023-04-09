package ru.example.samsungproject.fragments.basic;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.databinding.FragmentSettingsBinding;
import ru.example.samsungproject.viewModels.SettingsFragmentViewModel;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsFragmentViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);


        viewModel.LoadUserData();

        viewModel.Email.observe(getViewLifecycleOwner(), email -> {
            binding.textViewEmail.setText(email);
        });

        viewModel.Name.observe(getViewLifecycleOwner(), name -> {
            binding.textViewName.setText(name);
        });

        return binding.getRoot();
    }
}
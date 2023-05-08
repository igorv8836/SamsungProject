package ru.example.samsungproject.fragments.fragmentsOfMyEvents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.R;
import ru.example.samsungproject.databinding.FragmentEditEventBinding;
import ru.example.samsungproject.viewModels.EditEventFragmentViewModel;


public class EditEventFragment extends Fragment {

    FragmentEditEventBinding binding;
    EditEventFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditEventBinding.inflate(inflater, container, false);
        viewModel = new EditEventFragmentViewModel();

        binding.buttonAddUser.setOnClickListener(t -> {
            viewModel.addUser(binding.editTextEmailNewUserInputText.getText().toString());
        });

        return binding.getRoot();
    }
}
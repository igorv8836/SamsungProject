package ru.example.samsungproject.fragments.fragmentsOfSettings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.example.samsungproject.databinding.FragmentReauthenticationBinding;
import ru.example.samsungproject.viewModels.ReauthenticationFragmentViewModel;

public class ReauthenticationFragment extends DialogFragment {

    FragmentReauthenticationBinding binding;
    ReauthenticationFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReauthenticationBinding.inflate(inflater, container, false);
        viewModel = new ReauthenticationFragmentViewModel();

        binding.buttonReLogin.setOnClickListener(view -> viewModel.ReLogin(binding.editTextPasswordInputText.getText().toString()));

        viewModel.reauthCheck.observe(getViewLifecycleOwner(), d -> {
            if (d)
                Toast.makeText(requireContext(), "Вход успешен, меняйте пароль", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            onDismiss(requireDialog());
        });

        return binding.getRoot();
    }
}
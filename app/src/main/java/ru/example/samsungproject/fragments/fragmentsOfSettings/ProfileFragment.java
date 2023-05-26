package ru.example.samsungproject.fragments.fragmentsOfSettings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import ru.example.samsungproject.databinding.FragmentProfileBinding;
import ru.example.samsungproject.viewModels.ProfileFragmentViewModel;

public class ProfileFragment extends Fragment{
    private FragmentProfileBinding binding;
    private ProfileFragmentViewModel viewModel;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel.startFragment(getArguments());
        progressDialog = new ProgressDialog(requireActivity());

        viewModel.profileData.observe(getViewLifecycleOwner(), data ->{
            binding.textViewEmail.setText(data[0]);
            binding.editTextNicknameInputText.setText(data[1]);
            binding.textViewName.setText(data[1]);
                });

        viewModel.loadedCheck.observe(getViewLifecycleOwner(), q -> {
            Toast.makeText(requireActivity(), "Дождитесь загрузки данных", Toast.LENGTH_SHORT).show();
        });

        binding.save.setOnClickListener(l ->{
            progressDialog.setMessage("Загрузка");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            viewModel.setNewName(Objects.requireNonNull(binding.editTextNicknameInputText.getText()).toString());
        });

        viewModel.sendedName.observe(getViewLifecycleOwner(), d -> {
            if (d)
                Toast.makeText(requireActivity(), "Имя поменяется после перезапуска", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireActivity(), "Имя не поменялось, ошибка", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        });

        binding.change.setOnClickListener(view -> {
            progressDialog.setMessage("Загрузка");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            viewModel.setNewPassword(Objects.requireNonNull(binding.editTextPasswordInputText.getText()).toString());
        });

        viewModel.sendedPassword.observe(getViewLifecycleOwner(), d -> {
            progressDialog.dismiss();
            if (d)
                Toast.makeText(requireActivity(), "Пароль поменялся успешно", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireActivity(), "Пароль не поменялся, возникла ошибка", Toast.LENGTH_SHORT).show();
        });

        viewModel.passwordError.observe(getViewLifecycleOwner(), t -> {
            Toast.makeText(requireActivity(), t, Toast.LENGTH_SHORT).show();
            DialogFragment dialogFragment = new ReauthenticationFragment();
            dialogFragment.show(getChildFragmentManager(), "reauth");
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
    }
}
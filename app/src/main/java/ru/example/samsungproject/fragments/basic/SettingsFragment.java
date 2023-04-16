package ru.example.samsungproject.fragments.basic;

import static android.view.View.GONE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ru.example.samsungproject.R;
import ru.example.samsungproject.databinding.FragmentSettingsBinding;
import ru.example.samsungproject.viewModels.SettingsFragmentViewModel;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsFragmentViewModel viewModel;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        viewModel = new ViewModelProvider(this).get(SettingsFragmentViewModel.class);

        viewModel.LoadUserData();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        viewModel.data.observe(getViewLifecycleOwner(), data ->{
            binding.textViewEmail.setText(data[0]);
            binding.textViewName.setText(data[1]);
            bundle.putString("Email", data[0]);
            bundle.putString("Name", data[1]);
            binding.progressBar.setVisibility(View.GONE);
            binding.imageView.setVisibility(View.VISIBLE);
            binding.textViewName.setVisibility(View.VISIBLE);
            binding.textViewEmail.setVisibility(View.VISIBLE);
        });


        binding.buttonProfile.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_profile, bundle));
        binding.buttonSettings.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_small_settings, bundle));
        binding.buttonAboutApp.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_about_app, bundle));
        binding.buttonFeedback.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_feedback, bundle));


        return binding.getRoot();
    }
}
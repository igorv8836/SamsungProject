package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.R;
import ru.example.samsungproject.databinding.FragmentMyEventsBinding;


public class MyEventsFragment extends Fragment {

    private FragmentMyEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false);

        binding.create.setOnClickListener(t ->
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.editEventFragment)
        );

        return binding.getRoot();
    }
}
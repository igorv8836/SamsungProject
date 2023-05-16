package ru.example.samsungproject.fragments.basic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.example.samsungproject.R;
import ru.example.samsungproject.adapters.EventsAdapter;
import ru.example.samsungproject.databinding.FragmentMyEventsBinding;
import ru.example.samsungproject.viewModels.MyEventsFragmentViewModel;


public class MyEventsFragment extends Fragment {

    private FragmentMyEventsBinding binding;
    MyEventsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyEventsBinding.inflate(inflater, container, false);
        viewModel = new MyEventsFragmentViewModel();

        viewModel.loadMyEvents();

        binding.create.setOnClickListener(t ->
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.editEventFragment)
        );

        binding.buttonMyEvents.setOnClickListener(t -> {
            viewModel.changeMyEventsRecyclerViewVisibility();
        });

        viewModel.myEventsRecyclerViewVisibility.observe(getViewLifecycleOwner(), t -> {
            binding.recyclerViewMyEvents.setVisibility(t);
        });

        viewModel.myEvents.observe(getViewLifecycleOwner(), t -> {
            EventsAdapter adapter = new EventsAdapter(requireContext(), t);
            binding.recyclerViewMyEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerViewMyEvents.setAdapter(adapter);
        });


        return binding.getRoot();
    }
}
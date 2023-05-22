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
import ru.example.samsungproject.adapters.InvitationsAdapter;
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
        viewModel.loadInvitations();

        binding.create.setOnClickListener(t ->
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.editEventFragment)
        );

        binding.buttonMyEvents.setOnClickListener(t -> {
            viewModel.changeMyEventsRecyclerViewVisibility();
        });

        binding.buttonOtherEvents.setOnClickListener(t -> {
            viewModel.changeOtherEventsRecyclerViewVisibility();
        });

        binding.buttonInvitations.setOnClickListener(t -> {
            viewModel.changeInvitationsRecyclerViewVisibility();
        });

        viewModel.myEventsRecyclerViewVisibility.observe(getViewLifecycleOwner(), t -> {
            binding.recyclerViewMyEvents.setVisibility(t);
        });

        viewModel.otherEventsRecyclerViewVisibility.observe(getViewLifecycleOwner(), t -> {
            binding.recyclerViewOtherEvents.setVisibility(t);
        });

        viewModel.invitationsRecyclerViewVisibility.observe(getViewLifecycleOwner(), t -> {
            binding.recyclerViewInvitations.setVisibility(t);
        });

        viewModel.myEvents.observe(getViewLifecycleOwner(), t -> {
            EventsAdapter adapter = new EventsAdapter(requireContext(), t, viewModel);
            binding.recyclerViewMyEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerViewMyEvents.setAdapter(adapter);
        });

        viewModel.otherEvents.observe(getViewLifecycleOwner(), t -> {
            EventsAdapter adapter = new EventsAdapter(requireContext(), t, viewModel);
            binding.recyclerViewOtherEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerViewOtherEvents.setAdapter(adapter);
        });

        viewModel.invitations.observe(getViewLifecycleOwner(), t -> {
            InvitationsAdapter adapter = new InvitationsAdapter(requireContext(), t, viewModel);
            binding.recyclerViewInvitations.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.recyclerViewInvitations.setAdapter(adapter);
        });

        viewModel.manageEvent.observe(getViewLifecycleOwner(), t -> {
            if (!t.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putString("id", t);
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_edit_event, bundle);
            }
        });

        viewModel.eventTasks.observe(getViewLifecycleOwner(), t -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", t);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_event_tasks, bundle);
        });


        return binding.getRoot();
    }
}
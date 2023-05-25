package ru.example.samsungproject.fragments.fragmentsOfMyEvents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.example.samsungproject.R;
import ru.example.samsungproject.adapters.UsersAdapter;
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

        if (getArguments() != null) {
            viewModel.loadFromBundle(getArguments());
        }
        viewModel.addCreatorUser();

        binding.buttonAddUser.setOnClickListener(t -> {
            viewModel.addUser(binding.editTextEmailNewUserInputText.getText().toString());
            binding.editTextEmailNewUserInputText.setText("");
        });

        viewModel.ToastText.observe(getViewLifecycleOwner(), t -> {
            Toast.makeText(requireContext(), t, Toast.LENGTH_SHORT).show();
        });

        viewModel.users.observe(getViewLifecycleOwner(), t -> {
            UsersAdapter adapter = new UsersAdapter(requireContext(), t, viewModel);
            binding.listOfPeople.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.listOfPeople.setAdapter(adapter);
        });

        binding.buttonSaveEvent.setOnClickListener(t -> {
            if (getArguments() == null)
                viewModel.createEvent(binding.editTextTitleInputText.getText().toString(),
                        binding.editTextDescriptionInputText.getText().toString(),
                        binding.editTextDateInputText.getText().toString(),
                        viewModel.users.getValue(),
                        binding.access.isActivated());
            else
                viewModel.update();
        });

        viewModel.eventIsCreated.observe(getViewLifecycleOwner(), t -> {
            if (t)
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.myEventsFragment);
        });

        viewModel.title.observe(getViewLifecycleOwner(), t -> {
            binding.editTextTitleInputText.setText(t);
        });

        viewModel.description.observe(getViewLifecycleOwner(), t -> {
            binding.editTextDescriptionInputText.setText(t);
        });

        viewModel.access.observe(getViewLifecycleOwner(), t -> {
            binding.access.setChecked(t);
        });

        return binding.getRoot();
    }
}
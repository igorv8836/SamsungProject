package ru.example.samsungproject.fragments.fragmentsOfMyEvents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import ru.example.samsungproject.R;
import ru.example.samsungproject.adapters.UsersAdapter;
import ru.example.samsungproject.databinding.FragmentEditEventBinding;
import ru.example.samsungproject.viewModels.EditEventFragmentViewModel;
import ru.example.samsungproject.viewModels.MyEventsFragmentViewModel;


public class EditEventFragment extends Fragment {

    FragmentEditEventBinding binding;
    EditEventFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditEventBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(EditEventFragmentViewModel.class);

        if (getArguments() != null) {
            viewModel.loadFromBundle(getArguments());
        }
        else
            viewModel.addCreatorUser();

        binding.buttonAddUser.setOnClickListener(t -> {
            viewModel.addUser(Objects.requireNonNull(binding.editTextEmailNewUserInputText.getText()).toString());
            binding.editTextEmailNewUserInputText.setText("");
        });

        viewModel.ToastText.observe(getViewLifecycleOwner(), t ->
                Toast.makeText(requireContext(), t, Toast.LENGTH_SHORT).show());

        viewModel.users.observe(getViewLifecycleOwner(), t -> {
            UsersAdapter adapter = new UsersAdapter(requireContext(), t, viewModel);
            binding.listOfPeople.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.listOfPeople.setAdapter(adapter);
        });

        binding.buttonSaveEvent.setOnClickListener(t -> {
            viewModel.createEvent(Objects.requireNonNull(binding.editTextTitleInputText.getText()).toString(),
                    Objects.requireNonNull(binding.editTextDescriptionInputText.getText()).toString(),
                    Objects.requireNonNull(binding.editTextDateInputText.getText()).toString(),
                    viewModel.users.getValue(),
                    binding.access.isChecked());
            Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        });

        viewModel.eventIsCreated.observe(getViewLifecycleOwner(), t -> {
            if (t)
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.myEventsFragment);
        });

        viewModel.title.observe(getViewLifecycleOwner(), t ->
                binding.editTextTitleInputText.setText(t));

        viewModel.description.observe(getViewLifecycleOwner(), t ->
                binding.editTextDescriptionInputText.setText(t));

        viewModel.access.observe(getViewLifecycleOwner(), t ->
                binding.access.setChecked(t));

        return binding.getRoot();
    }
}
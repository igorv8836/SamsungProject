package ru.example.samsungproject.fragments.fragmentsOfSettings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.example.samsungproject.databinding.FragmentFeedbackBinding;
import ru.example.samsungproject.viewModels.FeedbackFragmentViewModel;

public class FeedbackFragment extends Fragment{
    private FragmentFeedbackBinding binding;
    private FeedbackFragmentViewModel viewModel;
    ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        viewModel = new FeedbackFragmentViewModel();
        
        dialog = new ProgressDialog(requireActivity());

        binding.sendFeedack.setOnClickListener(t -> {
            dialog.setMessage("Загрузка");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
            viewModel.sendFeedback(
                    binding.topic.getText().toString(),
                    binding.textOfTopic.getText().toString());
        });
        
        viewModel.sendedCheck.observe(getViewLifecycleOwner(), d -> {
            dialog.dismiss();
            if (d)
                Toast.makeText(requireActivity(), "Успешно отправлено", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireActivity(), "Не отправлено, ошибка", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }
}
package ru.example.samsungproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import ru.example.samsungproject.R;
import ru.example.samsungproject.databinding.TaskBinding;
import ru.example.samsungproject.fragments.fragmentsOfMyEvents.TasksFragment;
import ru.example.samsungproject.interfaces.EventsListeners.OnTaskButtonListener;
import ru.example.samsungproject.supportingClasses.Task;
import ru.example.samsungproject.supportingClasses.User;
import ru.example.samsungproject.viewModels.TasksFragmentViewModel;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> data;
    private final LayoutInflater localInflater;
    private OnTaskButtonListener listener;

    public TasksAdapter(Context context, List<Task> data, TasksFragmentViewModel viewModel) {
        this.data = data;
        this.localInflater = LayoutInflater.from(context);
        this.listener = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskBinding binding = TaskBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = data.get(position);

        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.price.setText(String.valueOf(task.getPrice()));
        holder.isCompleted.setChecked(task.isCompleted());
        holder.author.setText(task.getAuthor());
        holder.percentCompleted.setProgress(task.getPercentCompleted());

        if (task.isEditable()){
            holder.title.setEnabled(true);
            holder.description.setEnabled(true);
            holder.price.setEnabled(true);
        } else{
            holder.title.setEnabled(false);
            holder.description.setEnabled(false);
            holder.price.setEnabled(false);
            holder.button.setText("Редактировать");
        }

        holder.button.setOnClickListener(t -> {
            if (task.isEditable()) {
                task.setEditable(false);
                String a = holder.price.getText().toString();
                int b = 0;
                if (!a.isEmpty())
                    b = Integer.parseInt(a);
                listener.onPressed(
                        task.getId(),
                        holder.title.getText().toString(),
                        holder.description.getText().toString(),
                        holder.author.getText().toString(),
                        b,
                        holder.percentCompleted.getProgress(),
                        holder.isCompleted.isChecked(),
                        true);
                task.setEditable(false);
                holder.title.setEnabled(false);
                holder.description.setEnabled(false);
                holder.price.setEnabled(false);
                holder.button.setText("Редактировать");
            } else{
                task.setEditable(true);
                holder.title.setEnabled(true);
                holder.description.setEnabled(true);
                holder.price.setEnabled(true);
                holder.button.setText("Сохранить");
            }
        });

        holder.percentCompleted.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.isCompleted.setChecked(progress == 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                listener.changeCompleted(task.getId(), seekBar.getProgress(), seekBar.getProgress() == 100);
            }
        });

        holder.isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int p = 100;
                if (!isChecked) {
                    p = 0;
                    holder.percentCompleted.setProgress(0, true);
                } else {
                    task.setPercentCompleted(100);
                    holder.percentCompleted.setProgress(100, true);
                }
                listener.changeCompleted(task.getId(), p, isChecked);
            }
        });

        holder.button_delete.setOnClickListener(t -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(localInflater.getContext());
            builder.setMessage("Вы точно хотите удалить?")
                    .setTitle("Удаление")
                    .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.deleteTask(task.getId());
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText title;
        EditText description;
        TextView author;
        EditText price;
        SeekBar percentCompleted;
        CheckBox isCompleted;
        MaterialButton button;
        ImageFilterView button_delete;
        public ViewHolder(@NonNull TaskBinding binding) {
            super(binding.getRoot());

            title = binding.name;
            description = binding.description;
            author = binding.author;
            price = binding.price;
            percentCompleted = binding.seekBar;
            isCompleted = binding.checkBox;
            button = binding.buttonSaveTask;
            button_delete = binding.buttonDelete;
        }
    }
}

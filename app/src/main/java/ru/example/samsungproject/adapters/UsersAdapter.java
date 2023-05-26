package ru.example.samsungproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.example.samsungproject.databinding.UserElementBinding;
import ru.example.samsungproject.interfaces.EventsListeners.OnUserStatusClickListener;
import ru.example.samsungproject.supportingClasses.User;
import ru.example.samsungproject.viewModels.EditEventFragmentViewModel;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> data;
    private final LayoutInflater localInflater;
    private OnUserStatusClickListener listener;

    public UsersAdapter(Context context, List<User> data, EditEventFragmentViewModel viewModel) {
        this.data = data;
        this.localInflater = LayoutInflater.from(context);
        this.listener = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserElementBinding binding = UserElementBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User item = data.get(position);
        holder.name.setText(item.getName());
        if (item.isCreator())
            holder.status.setText("Создатель");
        else if (item.isAdmin())
            holder.status.setText("Руководитель");
        else
            holder.status.setText("Участник");

        holder.email.setText(item.getEmail());

        holder.imagedown.setOnClickListener(t -> listener.onClickDown(data.get(position)));
        holder.imageup.setOnClickListener(t -> listener.onClickUp(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView email;
        TextView status;
        ImageView imageup;
        ImageView imagedown;

        public ViewHolder(@NonNull UserElementBinding binding) {
            super(binding.getRoot());

            name = binding.name;
            imageup = binding.imageArrowUp;
            imagedown = binding.imageArrowDown;
            email = binding.email;
            status = binding.status;
        }
    }
}

package ru.example.samsungproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.example.samsungproject.databinding.InvitationBinding;
import ru.example.samsungproject.interfaces.EventsListeners.OnUserAdoptedListener;

public class ParticipationsAdapter extends RecyclerView.Adapter<ParticipationsAdapter.ViewHolder> {
    private final List<String> data;
    private final LayoutInflater localInflater;
    private final OnUserAdoptedListener viewModel;

    public ParticipationsAdapter(Context context, List<String> data, OnUserAdoptedListener viewModel) {
        this.data = data;
        localInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ParticipationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvitationBinding binding = InvitationBinding.inflate(localInflater, parent, false);
        return new ParticipationsAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ParticipationsAdapter.ViewHolder holder, int position) {
        String email = data.get(position);

        holder.sender.setText("Отправитель: " + email);

        holder.button_yes.setOnClickListener(t -> viewModel.onUserAdopted(email));

        holder.button_no.setOnClickListener(t -> viewModel.onNotUserAdopted(email));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sender;
        ImageView button_yes;
        ImageView button_no;


        public ViewHolder(InvitationBinding binding) {
            super(binding.getRoot());

            sender = binding.senderEmail;
            button_yes = binding.buttonYes;
            button_no = binding.buttonNo;
        }


    }
}

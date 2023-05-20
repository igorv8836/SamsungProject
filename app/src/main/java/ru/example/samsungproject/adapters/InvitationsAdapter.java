package ru.example.samsungproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.example.samsungproject.databinding.InvitationBinding;
import ru.example.samsungproject.interfaces.UserListener.OnResponseToInvitationListener;
import ru.example.samsungproject.supportingClasses.Invitation;

public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.ViewHolder> {
    private final List<Invitation> data;
    private final LayoutInflater localInflater;
    private final OnResponseToInvitationListener viewModel;

    public InvitationsAdapter(Context context, List<Invitation> data, OnResponseToInvitationListener viewModel) {
        this.data = data;
        localInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public InvitationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvitationBinding binding = InvitationBinding.inflate(localInflater, parent, false);
        return new InvitationsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationsAdapter.ViewHolder holder, int position) {
        Invitation invitation = data.get(position);

        holder.eventName.setText(invitation.getEventName());
        holder.sender.setText("Создатель: " + invitation.getAdminEmail());
        //holder.countMembers.setText("Кол-во участников: " + invitation.getCountMembers());

        holder.button_yes.setOnClickListener(t -> {
            viewModel.OnUserAgreed(invitation.geteventId());
        });

        holder.button_no.setOnClickListener(t -> {
            viewModel.OnUserDisagreed(invitation.geteventId());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView sender;
        TextView countMembers;
        ImageView button_yes;
        ImageView button_no;


        public ViewHolder(InvitationBinding binding) {
            super(binding.getRoot());

            eventName = binding.eventName;
            sender = binding.senderEmail;
            //countMembers = binding.;
            button_yes = binding.buttonYes;
            button_no = binding.buttonNo;
        }


    }
}

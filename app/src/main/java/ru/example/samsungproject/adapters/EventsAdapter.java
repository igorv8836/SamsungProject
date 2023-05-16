package ru.example.samsungproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ru.example.samsungproject.databinding.MyEventElementBinding;
import ru.example.samsungproject.supportingClasses.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> data;
    private final LayoutInflater localInflater;

    public EventsAdapter(Context context, List<Event> data) {
        this.data = data;
        localInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyEventElementBinding binding = MyEventElementBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = data.get(position);

        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.countPeople.setText("Количество людей: " + event.sizeMembers());
        holder.geolocation.setVisibility(View.GONE);
        if (event.getAccess())
            holder.access.setText("Публичный");
        else
            holder.access.setText("Закрытый");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView countPeople;
        TextView geolocation;
        TextView access;

        MaterialButton buttonAllTask;
        MaterialButton buttonManageEvent;
        MaterialButton buttonAllMembers;


        public ViewHolder(MyEventElementBinding binding) {
            super(binding.getRoot());

            title = binding.eventName;
            description = binding.description;
            countPeople = binding.numberPeople;
            geolocation = binding.location;
            access = binding.access;

            buttonAllTask = binding.buttonListTasks;
            buttonManageEvent = binding.buttonManage;
            buttonAllMembers = binding.buttonListPeople;
        }


    }
}

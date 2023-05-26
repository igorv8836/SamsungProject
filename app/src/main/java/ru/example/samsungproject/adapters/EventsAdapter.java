package ru.example.samsungproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import ru.example.samsungproject.databinding.MyEventElementBinding;
import ru.example.samsungproject.interfaces.EventsListeners.OnEventManagedListener;
import ru.example.samsungproject.supportingClasses.Event;
import ru.example.samsungproject.supportingClasses.User;
import ru.example.samsungproject.viewModels.MyEventsFragmentViewModel;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>  {

    private final List<Event> data;
    private final LayoutInflater localInflater;

    private final OnEventManagedListener listener;

    public EventsAdapter(Context context, List<Event> data, MyEventsFragmentViewModel viewModel) {
        this.data = data;
        localInflater = LayoutInflater.from(context);
        listener = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyEventElementBinding binding = MyEventElementBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
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

        for (User user : event.getMembers()){
            if (user.getEmail().equals(event.getCurrentUserEmail())){
                if (!user.isAdmin())
                    holder.buttonManageEvent.setVisibility(View.GONE);
                break;
            }
        }

        holder.buttonManageEvent.setOnClickListener(t -> listener.OnEventManaged(event));

        holder.buttonAllTask.setOnClickListener(t -> listener.OnTasksShowed(event.getId()));
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

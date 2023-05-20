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
import ru.example.samsungproject.databinding.SearchEventElementBinding;
import ru.example.samsungproject.interfaces.EventsListeners.OnEventJoinedListener;
import ru.example.samsungproject.supportingClasses.Event;

public class SearchEventAdapter extends RecyclerView.Adapter<SearchEventAdapter.ViewHolder> {

    private final List<Event> data;
    private final LayoutInflater localInflater;
    private OnEventJoinedListener listener;

    public SearchEventAdapter(Context context, List<Event> data, OnEventJoinedListener listener) {
        this.data = data;
        localInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchEventElementBinding binding = SearchEventElementBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = data.get(position);

        holder.title.setText(event.getTitle());
        holder.countPeople.setText("Количество людей: " + event.getUsers().size());
        holder.geolocation.setVisibility(View.GONE);
        holder.admin.setText("Создатель: " + event.getAdmin());
        if (event.getAccess()) {
            holder.buttonJoin.setText("Присоединиться");
            holder.access.setText("Публичный");
        } else {
            holder.buttonJoin.setText("Подать заявку");
            holder.access.setText("Закрытый");
        }

        holder.buttonJoin.setOnClickListener(t -> {
            listener.OnJoined(event.getId());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView countPeople;
        TextView geolocation;
        TextView access;
        TextView admin;
        MaterialButton buttonJoin;

        public ViewHolder(SearchEventElementBinding binding) {
            super(binding.getRoot());

            title = binding.eventName;
            countPeople = binding.numberPeople;
            geolocation = binding.location;
            access = binding.access;
            admin = binding.admin;

            buttonJoin = binding.buttonJoin;
        }


    }
}

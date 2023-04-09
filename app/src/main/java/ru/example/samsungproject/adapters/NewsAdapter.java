package ru.example.samsungproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.example.samsungproject.databinding.NewsItemBinding;
import ru.example.samsungproject.supportingClasses.NewsElement;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsElement> data;
    private final LayoutInflater layoutInflater;
    int mExpandedPosition = -1;

    public NewsAdapter(Context context, List<NewsElement> data){
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<NewsElement> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        NewsElement item = data.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.date.setText(item.getDate());

        final boolean isExpanded = position == mExpandedPosition;

        holder.description.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        holder.itemView.setOnClickListener(v -> {
            mExpandedPosition = isExpanded ? -1:position;
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;

        public ViewHolder(@NonNull NewsItemBinding binding) {
            super(binding.getRoot());
            title = binding.title;
            description = binding.description;
            date = binding.date;

        }
    }
}

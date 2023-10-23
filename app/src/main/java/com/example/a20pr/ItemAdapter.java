package com.example.a20pr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ListItem> items;

    public ItemAdapter(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ListItem item = items.get(position);

        // Устанавливаем текст из объекта ListItem в соответствующие TextView
        holder.text1TextView.setText(item.getText1());
        holder.text2TextView.setText(item.getText2());
        holder.text3TextView.setText(item.getText3());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView text1TextView;
        public TextView text2TextView;
        public TextView text3TextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text1TextView = itemView.findViewById(R.id.text1TextView);
            text2TextView = itemView.findViewById(R.id.text2TextView);
            text3TextView = itemView.findViewById(R.id.text3TextView);
            //text3TextView = itemView.findViewById(R.id.text3TextView);
        }
    }
}



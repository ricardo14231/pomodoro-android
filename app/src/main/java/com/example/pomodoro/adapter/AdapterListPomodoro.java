package com.example.pomodoro.adapter;


import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoro.R;
import com.example.pomodoro.model.ListItemPomodoro;

import java.util.ArrayList;
import java.util.List;

public class AdapterListPomodoro extends RecyclerView.Adapter<AdapterListPomodoro.MyViewHolder> {

    private List<ListItemPomodoro> listItem = new ArrayList<>();

    public AdapterListPomodoro(List<ListItemPomodoro> listItem) {
        this.listItem = listItem;
    }

    private OnItemClickListener itemListener;

    public interface OnItemClickListener {
        void longClickListener(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent,false);

        return new MyViewHolder(itemList, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ListItemPomodoro item = listItem.get(position);

        //Seta o título está tachado, caso esteja.
        if(item.getTacked()) {
            holder.titleItem.setText(item.getTitle());
            holder.titleItem.setPaintFlags(holder.titleItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.titleItem.setText(item.getTitle());
            holder.titleItem.setPaintFlags(holder.titleItem.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.bntEdit.setImageResource(R.drawable.ic_edit_black_24dp);
        holder.bntDelete.setImageResource(R.drawable.ic_delete_black_24dp);

    }

    @Override
    public int getItemCount() {
        return this.listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleItem;
        ImageView bntEdit;
        ImageView bntDelete;

        public MyViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            titleItem = itemView.findViewById(R.id.tv_title_item);
            bntEdit = itemView.findViewById(R.id.iv_edit);
            bntDelete = itemView.findViewById(R.id.iv_delete);

            titleItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.longClickListener(position);
                        }
                    }
                    return true;
                }
            });

            bntDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(listener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                   }

                }
            });

        }

    }

}

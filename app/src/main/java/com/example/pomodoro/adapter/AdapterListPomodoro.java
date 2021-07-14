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

    private List<ListItemPomodoro> listaItem = new ArrayList<>();

    public AdapterListPomodoro(List<ListItemPomodoro> itemLista) {
        this.listaItem = itemLista;
    }

    private OnItemClickListener itemListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent,false);

        return new MyViewHolder(itemLista, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ListItemPomodoro item = listaItem.get(position);

        //Seta o título está tachado, caso esteja.
        if(item.getTacked()) {
            holder.title_item.setText(item.getTitle());
            holder.title_item.setPaintFlags(holder.title_item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title_item.setText(item.getTitle());
            holder.title_item.setPaintFlags(holder.title_item.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.edit.setImageResource(R.drawable.ic_edit_black_24dp);
        holder.delete.setImageResource(R.drawable.ic_delete_black_24dp);

        /*
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaItem.remove(position);
                notifyItemRemoved(position);
            }
        });

         */
    }

    @Override
    public int getItemCount() {
        return this.listaItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_item;
        ImageView edit;
        ImageView delete;

        public MyViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            title_item = itemView.findViewById(R.id.tv_title_item);
            edit = itemView.findViewById(R.id.iv_edit);
            delete = itemView.findViewById(R.id.iv_delete);
/*
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(listener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                   }

                }
            });


 */
        }

    }

}

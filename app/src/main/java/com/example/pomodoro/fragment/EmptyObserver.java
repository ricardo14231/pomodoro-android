package com.example.pomodoro.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class EmptyObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView recyclerView;
    private View viewEmpty;

    EmptyObserver(RecyclerView recyclerView, View view) {
        this.recyclerView = recyclerView;
        this.viewEmpty = view;
        System.out.println("TEste constructor");

    }

    @Override
    public void onChanged() {
        super.onChanged();

        checkRecyclerViewEmpty();
    }

    private void checkRecyclerViewEmpty() {
        if( recyclerView.getAdapter().getItemCount() == 0 ) {
            viewEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            System.out.println("TEste ");
            return;
        }

        viewEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}

package com.example.pomodoro.fragment;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView recyclerView;
    private View viewEmpty;

    EmptyObserver(RecyclerView recyclerView, View view) {
        this.recyclerView = recyclerView;
        this.viewEmpty = view;
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
            return;
        }

        viewEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}

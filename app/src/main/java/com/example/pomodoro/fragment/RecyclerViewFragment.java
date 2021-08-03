package com.example.pomodoro.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoro.R;
import com.example.pomodoro.adapter.AdapterListPomodoro;
import com.example.pomodoro.model.ListItemPomodoro;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterListPomodoro adapterList;
    private List<ListItemPomodoro> listItem = new ArrayList<>();
    private int indexRemoveItemList;
    private boolean isTacked = false;
    private EmptyObserver emptyObserver;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        ImageButton btnNewItem = view.findViewById(R.id.ib_new_item_list);

        buildRecyclerView(view);

        adapterList.setOnItemClickListener(new AdapterListPomodoro.OnItemClickListener() {

            @Override
            public void longClickListener(int position) {
                isTacked = !isTacked;
                listItem.get(position).setTacked(isTacked);
                adapterList.notifyItemChanged(position);
            }

            @Override
            public void onEditClick(int position) {
                dialogTitleItem(listItem.get(position).getTitle(), true, position);
            }

            @Override
            public void onDeleteClick(int position) {
                indexRemoveItemList = position;
                deleteItemDialog();
            }
        });

        btnNewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogTitleItem("", false, -1);
            }
        });

        return view;
    }

    private void addItemList(String textItem) {
        if(textItem.length() > 0) {
            ListItemPomodoro item = new ListItemPomodoro(textItem);
            this.listItem.add( item );

            //Notifica a alteração no adapter para exibir/ocultar a view f_item_list_empty.
            adapterList.notifyDataSetChanged();
        }
    }

    private void buildRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv_fragment);

        //Adapter
        adapterList = new AdapterListPomodoro( listItem );

        emptyObserver = new EmptyObserver (recyclerView, view.findViewById(R.id.f_item_list_empty));
        adapterList.registerAdapterDataObserver(emptyObserver);

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapterList );

        adapterList.notifyDataSetChanged();
    }

    private void dialogTitleItem(String textItem, final boolean editTextTitle, final int position) {

        AlertDialog.Builder newDialog = new AlertDialog.Builder(recyclerView.getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View viewNewItemList = inflater.inflate(R.layout.new_item_list, null);

        final EditText text = viewNewItemList.findViewById(R.id.et_new_item_list);
        text.requestFocus();
        text.setText(textItem);

        showKeyboard(true);

        newDialog.setView(viewNewItemList)
                .setPositiveButton(R.string.confirmAction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        setTextTitleItem(text.getText().toString(), editTextTitle, position);

                        showKeyboard(false);

                    }
                }).setNegativeButton(R.string.cancelAction, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showKeyboard(false);
                        dialog.cancel();
                    }
                });
         newDialog.create();
         newDialog.show();
    }

    private void setTextTitleItem(String text, boolean editTextTitle, int position) {
        if(editTextTitle) {
            listItem.get(position).setTitle(text);
            adapterList.notifyItemChanged(position);
        } else {
            addItemList(text);
        }
    }

    private void deleteItemDialog() {

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(recyclerView.getContext());

        deleteDialog.setTitle(R.string.deleteItemList);
        deleteDialog.setMessage(R.string.deleteItemMessage);

        deleteDialog.setPositiveButton(R.string.confirmAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                listItem.remove(indexRemoveItemList);
                adapterList.notifyItemRemoved(indexRemoveItemList);
            }
        }).setNegativeButton(R.string.cancelAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        deleteDialog.create();
        deleteDialog.show();
    }

    private void showKeyboard(Boolean show) {
        if( show ) {
            //Abre o teclado do aparelho.
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            return;
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}

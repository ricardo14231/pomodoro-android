package com.example.pomodoro.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.pomodoro.R;
import com.example.pomodoro.adapter.AdapterListPomodoro;
import com.example.pomodoro.model.ListItemPomodoro;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterListPomodoro adapterList;
    private List<ListItemPomodoro> listItem = new ArrayList<>();
    private ImageButton btnNewItem;
    private ImageButton btnDeleteItem;
    private boolean isTacked = false;
    private  EmptyObserver emptyObserver;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        btnNewItem = view.findViewById(R.id.ib_new_item_list);
        btnDeleteItem = view.findViewById(R.id.iv_delete);

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


/*
        adapter_list.setOnItemClickListener(new AdapterListPomodoro.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                System.out.println("Foi");
                deleteItemDialog();
            }
        });
*/
        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(
                    getContext(),
                    recyclerView,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            isTacked = !isTacked;
                            setTacked(position, isTacked);
                        }

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
                    }
            )
        );

        btnNewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newItemDialog();
            }
        });

        return view;
    }

    public void AddItemList(String itemList) {
        ListItemPomodoro item = new ListItemPomodoro(itemList);
        this.listItem.add( item );

        //Notifica a alteração no adapter para exibir/ocultar a view f_item_list_empty.
        adapterList.notifyDataSetChanged();
    }


    public void newItemDialog() {

        AlertDialog.Builder newDialog = new AlertDialog.Builder(recyclerView.getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewNewItemList = inflater.inflate(R.layout.new_item_list, null);

        final EditText textTitle = viewNewItemList.findViewById(R.id.et_new_item_list);
        textTitle.requestFocus();

        showKeyboard(true);

        newDialog.setView(viewNewItemList)
                .setPositiveButton(R.string.confirmAction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AddItemList(textTitle.getText().toString());
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

    public void deleteItemDialog() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(recyclerView.getContext());

        deleteDialog.setTitle(R.string.deleteItemList);

        deleteDialog.setMessage(R.string.deleteItemMessage);
        deleteDialog.setPositiveButton(R.string.confirmAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                listItem.remove(position);
                adapterList.notifyItemChanged(position);
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

    private void addTeste() {
        btnDeleteItem = recyclerView.findViewById(R.id.iv_delete);

        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemDialog();
            }
        });
    }

    private void setTacked(int position, boolean tacked) {
        String title = listItem.get(position).getTitle();
        listItem.get(position).setTitle(title);
        listItem.get(position).setTacked(tacked);
        adapterList.notifyItemChanged(position);
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

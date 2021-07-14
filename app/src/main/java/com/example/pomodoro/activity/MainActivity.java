package com.example.pomodoro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pomodoro.R;
import com.example.pomodoro.fragment.RecyclerViewFragment;
import com.example.pomodoro.fragment.TimeFragment;

public class MainActivity extends AppCompatActivity {

    private Button btnTime, btnList;
    private TimeFragment fragmentTime;
    private RecyclerViewFragment fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Exibe o Fragment Time como inicial.
        fragmentTime = new TimeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main, fragmentTime);
        transaction.commit();

        btnFragmentTime();
        btnFragmentList();

    }

    private void btnFragmentTime() {
        btnTime = findViewById(R.id.b_time);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundColorButton(btnTime, true);
                setBackgroundColorButton(btnList, false);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_main, fragmentTime);
                transaction.commit();
            }
        });
    }

    private void btnFragmentList() {
        fragmentList = new RecyclerViewFragment();
        btnList = findViewById(R.id.b_list);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBackgroundColorButton(btnTime, false);
                setBackgroundColorButton(btnList, true);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_main, fragmentList);
                transaction.commit();
            }
        });
    }

    private void setBackgroundColorButton(Button btn, boolean btnActive) {
        if(btnActive) {
            btn.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn.setBackgroundColor(Color.WHITE);
            return;
        }
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

}

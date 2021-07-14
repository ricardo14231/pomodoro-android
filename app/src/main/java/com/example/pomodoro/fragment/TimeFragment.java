package com.example.pomodoro.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.pomodoro.R;


public class TimeFragment extends Fragment {

    private ImageButton btnPlayPause;
    private TextView tvTime;
    private ProgressBar progressBar;
    private String textViewTime = "25:00";
    private boolean play = false;
    private long totalTime = 1500000;
    private long intervalTime = 1500000;
    private  CountDownTimer countTimer;
    private int stepTimePomodoro = 1;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time, container, false);

        btnPlayPause = view.findViewById(R.id.ib_play);
        tvTime = view.findViewById(R.id.tv_time);
        progressBar = view.findViewById(R.id.progress_bar);

        //Atualiza o text view ao retornar para o fragment.
        updateData();

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play = !play;

                if(play) {
                    setInterval();
                    btnPlayPause.setImageResource(R.drawable.ic_pause_black);
                } else {
                    countTimer.cancel();
                    btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
                }
            }
        });

        btnPlayPause.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetData();
                return true;
            }
        });

        return view;
    }


    private void setInterval() {
        countTimer = new CountDownTimer(totalTime, 1000) {

            public void onTick(long millisUntilFinished) {
                String min = String.format("%02d", millisUntilFinished/60000);
                int seg = (int)( (millisUntilFinished%60000)/1000);
                textViewTime = (min +":"+String.format("%02d",seg));
                tvTime.setText(textViewTime);
                totalTime = millisUntilFinished;
                progressBar.setProgress( (int) millisUntilFinished);
            }

            public void onFinish() {
                audioPlay();
                totalTime = setStepPomodoro();
                intervalTime = totalTime;
                resetData();
            }
        }.start();

    }

    //Ao retornar para o fragment "time" atualiza o TextView tv_time.
    private void updateData() {
        tvTime.setText(textViewTime);
        progressBar.setMax((int) intervalTime);

        if(play)
            btnPlayPause.setImageResource(R.drawable.ic_pause_black);
    }

    private int setStepPomodoro() {
        stepTimePomodoro++;

        if(stepTimePomodoro == 8) {
           stepTimePomodoro = 1;
           return 1800000;
        }
        if(stepTimePomodoro % 2 == 0) {
            return 300000;
        }
        return 1500000;
    }

    //Seta o Time no TextView no fim do ciclo ou após reiniciar o cronômetro.
    private void setTimeTextView() {

        if(intervalTime == 1800000){
            textViewTime = "30:00";
            tvTime.setText("30:00");
            return;
        }

        if(intervalTime == 1500000){
            textViewTime = "25:00";
            tvTime.setText("25:00");
            return;
        }
        textViewTime = "05:00";
        tvTime.setText("05:00");
    }


    private void resetData() {
        play = false;
        totalTime = intervalTime;

        progressBar.setMax(( int) intervalTime );
        progressBar.setProgress( (int) intervalTime);
        btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
        //TODO verificar o time correto para setar no TextView

        setTimeTextView();

        if(countTimer != null) {
            countTimer.cancel();
        }
    }

    private void audioPlay() {

        mediaPlayer = mediaPlayer.create(getContext(), R.raw.beep);

        if(mediaPlayer != null)
            mediaPlayer.start();
    }

}

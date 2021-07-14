package com.example.pomodoro.model;

public class ListItemPomodoro {

    private String title;
    private boolean tacked = false;

    public ListItemPomodoro(String titulo) {

        this.title = titulo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {

        //TODO Verificar se o título é válido
        this.title = titulo;
    }

    public boolean getTacked() {
        return this.tacked;
    }

    public void setTacked(boolean tacked) {
        this.tacked = tacked;
    }

}

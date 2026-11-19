package com.egzamin;

import com.egzamin.controller.AppController;
import com.egzamin.model.DatabaseManager;
import com.egzamin.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        MainFrame view = new MainFrame();
        new AppController(db, view);
        view.setVisible(true);
    }
}

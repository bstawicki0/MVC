package com.egzamin.controller;

import com.egzamin.model.DatabaseManager;
import com.egzamin.model.Employee;
import com.egzamin.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppController {
    private DatabaseManager db;
    private MainFrame view;

    public AppController(DatabaseManager db, MainFrame view) {
        this.db = db;
        this.view = view;

        // podpiecie przyciskow
        this.view.btnDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajPracownika();
            }
        });

        this.view.btnUsun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usunPracownika();
            }
        });

        this.view.btnWyczysc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.wyczyscFormularz();
            }
        });

        // startowe ladowanie tabeli
        odswiezTabele();
    }

    private void dodajPracownika() {
        String imie = view.txtImie.getText().trim();
        String nazwisko = view.txtNazwisko.getText().trim();
        String stanowisko = (String) view.cbStanowisko.getSelectedItem();
        String pensjaTekst = view.txtPensja.getText().trim();

        // prosta walidacja
        if (imie.equals("") || nazwisko.equals("") || pensjaTekst.equals("")) {
            JOptionPane.showMessageDialog(view, "Wypelnij wszystkie pola!");
            return;
        }

        double pensja = 0;
        try {
            pensja = Double.parseDouble(pensjaTekst.replace(',', '.'));
            if (pensja <= 0) {
                JOptionPane.showMessageDialog(view, "Pensja musi byc wieksza od zera!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Pensja musi byc liczba!");
            return;
        }

        Employee p = new Employee(imie, nazwisko, stanowisko, pensja);
        db.dodajPracownika(p);

        JOptionPane.showMessageDialog(view, "Dodano pracownika do bazy.");
        view.wyczyscFormularz();
        odswiezTabele();
    }

    private void usunPracownika() {
        int wiersz = view.tabela.getSelectedRow();
        if (wiersz == -1) {
            JOptionPane.showMessageDialog(view, "Zaznacz najpierw pracownika w tabeli!");
            return;
        }

        int id = Integer.parseInt(view.modelTabeli.getValueAt(wiersz, 0).toString());

        int odp = JOptionPane.showConfirmDialog(view, "Czy na pewno usunac pracownika o ID: " + id + "?", "Pytanie", JOptionPane.YES_NO_OPTION);
        if (odp == JOptionPane.YES_OPTION) {
            db.usunPracownika(id);
            odswiezTabele();
            view.wyczyscFormularz();
            JOptionPane.showMessageDialog(view, "Usunieto pracownika.");
        }
    }

    private void odswiezTabele() {
        view.modelTabeli.setRowCount(0);
        List<Employee> lista = db.pobierzWszystkich();
        for (Employee p : lista) {
            view.modelTabeli.addRow(new Object[]{
                    p.getId(),
                    p.getImie(),
                    p.getNazwisko(),
                    p.getStanowisko(),
                    p.getPensja()
            });
        }
    }
}

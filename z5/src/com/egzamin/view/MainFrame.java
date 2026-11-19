package com.egzamin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {

    // pola formularza
    public JTextField txtImie = new JTextField(12);
    public JTextField txtNazwisko = new JTextField(12);
    public JComboBox<String> cbStanowisko = new JComboBox<>(new String[]{
            "Programista", "Tester", "Analityk", "Kierownik"
    });
    public JTextField txtPensja = new JTextField(12);

    // przyciski
    public JButton btnDodaj = new JButton("Dodaj pracownika");
    public JButton btnUsun = new JButton("Usun zaznaczonego");
    public JButton btnWyczysc = new JButton("Wyczysc formularz");

    // tabela
    public DefaultTableModel modelTabeli = new DefaultTableModel(
            new String[]{"ID", "Imie", "Nazwisko", "Stanowisko", "Pensja"}, 0
    );
    public JTable tabela = new JTable(modelTabeli);

    public MainFrame() {
        super("Aplikacja Pracownicy - INF.04");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // panel na formularz i przyciski
        JPanel panelGora = new JPanel(new BorderLayout());

        JPanel panelFormularz = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormularz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelFormularz.add(new JLabel("Imie:"));
        panelFormularz.add(txtImie);
        panelFormularz.add(new JLabel("Nazwisko:"));
        panelFormularz.add(txtNazwisko);
        panelFormularz.add(new JLabel("Stanowisko:"));
        panelFormularz.add(cbStanowisko);
        panelFormularz.add(new JLabel("Pensja:"));
        panelFormularz.add(txtPensja);

        JPanel panelPrzyciski = new JPanel(new FlowLayout());
        panelPrzyciski.add(btnDodaj);
        panelPrzyciski.add(btnUsun);
        panelPrzyciski.add(btnWyczysc);

        panelGora.add(panelFormularz, BorderLayout.CENTER);
        panelGora.add(panelPrzyciski, BorderLayout.SOUTH);

        // dodanie do okna
        add(panelGora, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    public void wyczyscFormularz() {
        txtImie.setText("");
        txtNazwisko.setText("");
        txtPensja.setText("");
        cbStanowisko.setSelectedIndex(0);
        tabela.clearSelection();
    }
}

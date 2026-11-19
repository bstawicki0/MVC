package com.egzamin.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection conn;
    private List<Employee> listaAwaryjna = new ArrayList<>();
    private int licznikId = 1;
    private boolean trybAwaryjny = false;

    public DatabaseManager() {
        polacz();
        utworzTabele();
    }

    private void polacz() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:pracownicy.db");
        } catch (Exception e) {
            System.out.println("Nie znaleziono sterownika sqlite, uzywam pamieci ram.");
            trybAwaryjny = true;
            // przykladowe dane startowe
            listaAwaryjna.add(new Employee(licznikId++, "Jan", "Kowalski", "Programista", 5000));
            listaAwaryjna.add(new Employee(licznikId++, "Anna", "Nowak", "Tester", 4200));
        }
    }

    private void utworzTabele() {
        if (trybAwaryjny) return;
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS pracownicy (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "imie TEXT, " +
                    "nazwisko TEXT, " +
                    "stanowisko TEXT, " +
                    "pensja REAL);";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajPracownika(Employee p) {
        if (trybAwaryjny) {
            p.setId(licznikId++);
            listaAwaryjna.add(p);
            return;
        }

        try {
            String sql = "INSERT INTO pracownicy (imie, nazwisko, stanowisko, pensja) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getImie());
            ps.setString(2, p.getNazwisko());
            ps.setString(3, p.getStanowisko());
            ps.setDouble(4, p.getPensja());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> pobierzWszystkich() {
        if (trybAwaryjny) {
            return listaAwaryjna;
        }

        List<Employee> lista = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pracownicy");
            while (rs.next()) {
                Employee p = new Employee(
                        rs.getInt("id"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("stanowisko"),
                        rs.getDouble("pensja")
                );
                lista.add(p);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void usunPracownika(int id) {
        if (trybAwaryjny) {
            listaAwaryjna.removeIf(p -> p.getId() == id);
            return;
        }

        try {
            String sql = "DELETE FROM pracownicy WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

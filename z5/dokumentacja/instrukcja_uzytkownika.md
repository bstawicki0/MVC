# Instrukcja Użytkownika

Program: System Pracownicy (Egzamin INF.04)

### 1. Opis programu
Program okienkowy w Java Swing do zapisywania pracowników w bazie danych. Kod podzielony jest na Model, View i Controller.

### 2. Wymagania i uruchomienie
- Zainstalowana Java 11 lub nowsza.
- Można uruchomić w IntelliJ klikając Run w pliku Main.java albo z pliku JAR:
  `java -jar ProjektEgzaminacyjny.jar`

### 3. Jak obsługiwać program
1. **Dodawanie pracownika:**
   - Wpisz imię, nazwisko, wybierz stanowisko z listy i wpisz pensję.
   - Kliknij przycisk "Dodaj pracownika".
   - Jeśli pola są puste lub pensja nie jest liczbą, program pokaże okienko z błędem.
2. **Usuwanie pracownika:**
   - Kliknij na wiersz w tabeli, który chcesz usunąć.
   - Kliknij "Usun zaznaczonego".
   - Potwierdź usunięcie w okienku pytania.
3. **Czyszczenie pól:**
   - Przycisk "Wyczysc formularz" kasuje wpisany tekst w polach.

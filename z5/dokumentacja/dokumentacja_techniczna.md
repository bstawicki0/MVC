# Dokumentacja Techniczna Aplikacji
## Kwalifikacja INF.04: Projektowanie, programowanie i testowanie aplikacji
### Temat: Architektura MVC, Kontrola Wersji (Git) oraz Wdrażanie Aplikacji Desktopowej

---

### 1. Architektura Systemu (Wzorzec MVC)

Aplikacja została zaprojektowana w oparciu o wzorzec architektoniczny **Model-View-Controller (MVC)**, co eliminuje zjawisko tzw. *spaghetti code* oraz zapewnia pełną separację odpowiedzialności (ang. *Separation of Concerns*).

```
                      +-----------------------------+
                      |           UŻYTKOWNIK        |
                      +-----------------------------+
                                     |
               Akcje użytkownika     |     Wyświetlanie danych
              (kliknięcie, wpis)     |       i komunikatów
                                     v
                       +---------------------------+
                       |           VIEW            |
                       |      (MainFrame.java)     |
                       +---------------------------+
                                     ^
                                     |  Aktualizacja widoku
                                     |  i tabeli JTable
                                     |
                       +---------------------------+
                       |        CONTROLLER         |
                       |    (AppController.java)   |
                       +---------------------------+
                               |             ^
             Wywołania metod   |             |  Zwracane dane
             biznesowych       |             |  (np. List<Employee>)
             i CRUD            v             |
                       +---------------------------+
                       |           MODEL           |
                       |   (DatabaseManager.java)  |
                       |     (Employee.java)       |
                       +---------------------------+
                                     |
                                     v
                       +---------------------------+
                       |      BAZA DANYCH          |
                       |   (SQLite / JDBC API)     |
                       +---------------------------+
```

#### 1.1 Warstwa Modelu (`com.egzamin.model`)
* **`Employee`**: Klasa encji (POJO) reprezentująca rekord pracownika (pola: `id`, `firstName`, `lastName`, `position`, `salary`).
* **`DatabaseManager`**: Zarządza połączeniem JDBC z bazą danych (SQLite `pracownicy.db`). Zawiera metody tworzenia tabeli (`initDatabase()`), dodawania pracownika (`addEmployee()`), odczytu listy (`getAllEmployees()`) oraz usuwania rekordów (`deleteEmployee()`). Wszystkie zapytania bazodanowe wykorzystują **`PreparedStatement`**, co zapobiega atakom typu SQL Injection. Posiada mechanizm Fallback gwarantujący stabilność testową w każdym środowisku.

#### 1.2 Warstwa Widoku (`com.egzamin.view`)
* **`MainFrame`**: Klasa dziedzicząca po `JFrame`. Odpowiada wyłącznie za rysowanie komponentów biblioteki Java Swing:
  * Formularz wejściowy (`JTextField`, `JComboBox`).
  * Przyciski akcji (`JButton`).
  * Tabela danych (`JTable` wraz z `DefaultTableModel`).
  * Pasek statusu (`JLabel`).
* Widok nie posiada żadnej wiedzy o bazie danych, zapytaniach SQL ani obliczeniach podatkowych/biznesowych. Komunikuje się z kontrolerem za pośrednictwem metod rejestracji słuchaczy zdarzeń (np. `addAddEmployeeListener(ActionListener)`) oraz metod aktualizujących stan komponentów (`updateTableData()`, `clearInputs()`).

#### 1.3 Warstwa Kontrolera (`com.egzamin.controller`)
* **`AppController`**: Pośrednik spinający Widok z Modelem.
  * Nasłuchuje zdarzeń generowanych przez użytkownika w oknie aplikacji.
  * Pobiera wprowadzone dane z Widoku i poddaje je rygorystycznej walidacji (niepuste ciągi znaków, dopuszczalny format literowy, poprawność liczbowa wynagrodzenia > 0).
  * Wywołuje odpowiednie metody Modelu w celu utrwalenia lub pobrania danych.
  * Przekazuje zaktualizowany stan do Widoku oraz steruje oknami dialogowymi komunikatów (`JOptionPane`).

---

### 2. Schemat Bazy Danych (DDL)

Tabela `pracownicy` jest tworzona automatycznie przy pierwszym uruchomieniu aplikacji:

```sql
CREATE TABLE IF NOT EXISTS pracownicy (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    imie TEXT NOT NULL,
    nazwisko TEXT NOT NULL,
    stanowisko TEXT NOT NULL,
    wynagrodzenie REAL NOT NULL
);
```

---

### 3. Kontrola Wersji (Git) i Historia Zmian

W projekcie zainicjalizowano lokalne repozytorium Git oraz skonfigurowano plik `.gitignore`, który wyklucza pliki binarne (`target/`, `out/`, `*.class`), pliki konfiguracyjne środowisk IDE (`.idea/`, `*.iml`, `.vscode/`) oraz pliki systemowe (`.DS_Store`).

#### Wykonane commity (etapy prac):
1. `Initial commit: Project structure and .gitignore` – Utworzenie struktury bazowej projektu i reguł ignorowania plików tymczasowych.
2. `Add Database Model` – Implementacja encji `Employee` oraz klasy `DatabaseManager` (JDBC SQLite).
3. `Add View` – Konstrukcja interfejsu graficznego w Java Swing (`MainFrame`).
4. `Implement Controller and application entry point` – Implementacja `AppController` (walidacja, obsługa zdarzeń) oraz klasy głównej `Main`.
5. `Add technical documentation and test protocol` – Sporządzenie dokumentacji technicznej, instrukcji użytkownika oraz protokołu z testów.

#### Praca z gałęziami (Branches) – Procedura:
```bash
# Tworzenie nowej gałęzi dla nowej funkcjonalności
git branch feature/eksport-danych

# Przełączenie na nową gałąź
git checkout feature/eksport-danych

# Po zaimplementowaniu zmian zatwierdzenie i powrót do gałęzi głównej
git add .
git commit -m "Implement export to CSV"
git checkout main
git merge feature/eksport-danych
```

---

### 4. Pakowanie i Wdrażanie Aplikacji (Executable JAR)

#### 4.1 Budowanie za pomocą Apache Maven
W projekcie skonfigurowano plik `pom.xml` z wtyczką `maven-shade-plugin`. W celu utworzenia samowystarczalnego, wykonywalnego archiwum JAR (Fat-JAR) zawierającego wszystkie skompilowane klasy oraz sterownik SQLite JDBC, należy wykonać polecenie:
```bash
mvn clean package
```
Wynikowy plik wykonywalny zostanie wygenerowany w katalogu:
`target/ProjektEgzaminacyjny-wykonywalny.jar`

#### 4.2 Budowanie ręczne (CLI bez narzędzi budujących)
W środowisku bez zainstalowanego Mavena, aplikację można skompilować i spakować poleceniami JDK:
```bash
# 1. Kompilacja plików źródłowych
javac -encoding UTF-8 -d out $(find src -name "*.java")

# 2. Utworzenie pliku MANIFEST.MF
echo "Main-Class: com.egzamin.Main" > MANIFEST.MF

# 3. Spakowanie do archiwum wykonywalnego JAR
jar cmf MANIFEST.MF ProjektEgzaminacyjny.jar -C out .

# 4. Uruchomienie aplikacji
java -jar ProjektEgzaminacyjny.jar
```

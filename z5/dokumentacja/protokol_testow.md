# Protokół z testów aplikacji (INF.04)

Data: 19.11.2026
Tester: Uczeń

### Tabela testów

| Nr testu | Co testujemy | Dane wejściowe | Kroki | Oczekiwany wynik | Wynik rzeczywisty | Status |
|---|---|---|---|---|---|:---:|
| 1 | Poprawne dodanie pracownika | Imie: Jan, Nazwisko: Kowalski, Stanowisko: Programista, Pensja: 5000 | Wpisanie danych i kliknięcie "Dodaj pracownika" | Pojawia się komunikat o sukcesie i pracownik dodaje się do tabeli | Pracownik dodany do tabeli i bazy | ZALICZONY |
| 2 | Walidacja pustych pól | Imie puste, reszta wpisana | Kliknięcie "Dodaj pracownika" bez imienia | Okienko z błędem "Wypelnij wszystkie pola!" i brak zapisu | Wyświetlił się błąd, nie zapisało | ZALICZONY |
| 3 | Błędna pensja (tekst zamiast liczby) | Pensja: "duzo" | Wpisanie liter w pensji i kliknięcie "Dodaj" | Okienko z błędem "Pensja musi byc liczba!" | Wyświetlił się błąd o liczbie | ZALICZONY |
| 4 | Usunięcie pracownika z tabeli | Zaznaczony wiersz w tabeli | Wybranie wiersza i kliknięcie "Usun zaznaczonego" | Pytanie o potwierdzenie i usunięcie z tabeli | Wiersz został usunięty | ZALICZONY |

Wszystkie testy zakończyły się wynikiem pozytywnym. Program działa stabilnie.

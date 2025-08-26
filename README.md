# Sistem upravljanja bibliotekama

Ovo je **Java Spring Boot** web aplikacija za upravljanje bibliotekom. Aplikacija omogućava upravljanje knjigama, korisnicima i zaduženjima, uz integrisanu autentifikaciju i autorizaciju korisnika.

## Funkcionalnosti

- **Upravljanje knjigama:** Dodavanje, pregled, ažuriranje i brisanje knjiga
- **Upravljanje autorima:** Dodavanje i upravljanje autorima knjiga
- **Upravljanje kategorijama:** Organizacija knjiga po kategorijama
- **Upravljanje korisnicima:** Registracija korisnika, dodjela uloga (admin/korisnik)
- **Zaduženja:** Evidencija zaduženja knjiga i status vraćanja
- **Validacija formi:** Polja validirana pomoću anotacija (@NotNull, @Size, @Email...)
- **Autentifikacija i autorizacija:** Prijava/odjava korisnika putem Spring Security
- **Uloge korisnika:**
  - **Admin:** upravlja knjigama, autorima, kategorijama, korisnicima i zaduženjima
  - **Korisnik:** može pretraživati knjige i zaduživati ih
- **Pregled dostupnih knjiga:** Pretraživanje po naslovu
- **Thymeleaf predlošci:** Dinamičko generisanje stranica

## Tehnologije

### Backend
- Java Spring Boot 3.3.5
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate

### Baza podataka
- H2 (in-memory baza podataka)

### Frontend
- Thymeleaf (HTML template engine)
- Custom CSS

## Pokretanje aplikacije

### Potrebno instalirati
- Java JDK 17+
- Maven

### Build i Run

Pokretanje aplikacije lokalno:

```bash
.\mvnw.cmd spring-boot:run
```

### Pristup aplikaciji
- **Glavna aplikacija:** http://localhost:8080
- **H2 konzola:** http://localhost:8080/h2-console

## Struktura projekta

```
src/main/java/org/example/subwp/
├── controller/     # Spring MVC kontroleri
├── model/         # JPA entiteti
├── repository/    # Spring Data JPA repozitoriji
├── service/       # Business logika
├── security/      # Spring Security konfiguracija
└── validation/    # Custom validatori
```

## Inicijalni podaci

Aplikacija automatski učitava početne podatke o:
- Bosanskim autorima (Andrić, Selimović, Kikić, Dizdar, Kulenović)
- Kategorijama knjiga (Roman, Poezija, Drama, Povijest, Filozofija, Znanost)
- Knjigama sa bosanskim autorima

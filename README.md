##Bibliotekarski sistem – Spring Boot Web Aplikacija

Ovo je **Java Spring Boot** web aplikacija za upravljanje bibliotekom.  
Aplikacija omogućava upravljanje knjigama, korisnicima i posudbama, uz integrisanu autentifikaciju i autorizaciju korisnika.

## Funkcionalnosti

- **Upravljanje knjigama:** Dodavanje, pregled, ažuriranje i brisanje knjiga.
- **Upravljanje korisnicima:** Registracija korisnika, dodjela uloga (admin/korisnik).
- **Posudbe i rezervacije:** Evidencija posudbi knjiga i status vraćanja.
- **Validacija formi:** Polja validirana pomoću anotacija (@NotNull, @Size, @Email...).
- **Autentifikacija i autorizacija:** Prijava/odjava korisnika putem Spring Security.
- **Uloge korisnika:**
    - **Admin:** upravlja knjigama, korisnicima i posudbama.
    - **Korisnik:** može pretraživati i rezervisati knjige.
- **Pregled dostupnih knjiga:** Pretraživanje po nazivu, autoru ili žanru.
- **Thymeleaf predlošci:** Dinamičko generisanje stranica (lista knjiga, forma za registraciju, itd.).
## ️ Tehnologije

### Backend
- Java Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate

### Baza podataka
- H2 (in-memory testna baza)
- MySQL  (poteskoce)

### Frontend
- Thymeleaf (HTML template engine)
- Bootstrap & Custom CSS

##  Pokretanje aplikacije

### Potrebno instalirati
- Java JDK 17+
- Maven

### Build i Run

Pokretanje aplikacije lokalno u bash-u:

```bash
.\mvnw.cmd clean spring-boot:run -DskipTests

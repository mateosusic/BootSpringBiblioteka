# BootSpring Biblioteka

**Sistem upravljanja bibliotekama** - Java Spring Boot web aplikacija za upravljanje bibliotekom s integrisanom autentifikacijom i autorizacijom korisnika.

## 🚀 Funkcionalnosti

### 📚 Upravljanje knjigama
- Dodavanje, pregled, ažuriranje i brisanje knjiga
- Pretraživanje knjiga po naslovu
- Validacija ISBN brojeva
- Organizacija po kategorijama

### 👥 Upravljanje korisnicima
- Registracija novih korisnika
- Prijava/odjava korisnika
- Dodjela uloga (Admin/Korisnik)
- Upravljanje korisničkim profilima

### 📖 Upravljanje sadržajem
- **Autori:** Dodavanje i upravljanje autorima knjiga
- **Kategorije:** Organizacija knjiga po kategorijama
- **Zaduženja:** Evidencija zaduženja knjiga i status vraćanja

### 🔐 Sigurnost
- **Spring Security** integracija
- **Autentifikacija** korisnika
- **Autorizacija** na osnovu uloga
- **Validacija formi** pomoću anotacija

### 👨‍💼 Uloge korisnika
- **Admin:** Potpuna kontrola nad sistemom
  - Upravljanje knjigama, autorima, kategorijama
  - Upravljanje korisnicima i zaduženjima
  - Pristup svim funkcionalnostima
- **Korisnik:** Ograničene funkcionalnosti
  - Pretraživanje knjiga
  - Zaduživanje knjiga
  - Pregled vlastitih zaduženja

## 🛠️ Tehnologije

### Backend
- **Java 17+**
- **Spring Boot 3.3.5**
- **Spring MVC** - Web kontroleri
- **Spring Data JPA** - Pristup bazi podataka
- **Spring Security** - Autentifikacija i autorizacija
- **Hibernate** - ORM framework

### Baza podataka
- **H2 Database** - In-memory baza podataka

### Frontend
- **Thymeleaf** - HTML template engine
- **Custom CSS** - Stiliziranje stranica
- **Bootstrap** - Responsive dizajn

## 📋 Preduvjeti

- **Java JDK 17** ili noviji
- **Maven 3.6+**
- **Web browser** (Chrome, Firefox, Safari, Edge)

## 🚀 Pokretanje aplikacije

### 1. Kloniranje repozitorija
```bash
git clone https://github.com/mateosusic/BootSpringBiblioteka.git
cd BootSpringBiblioteka
```

### 2. Pokretanje aplikacije
```bash
# Windows
.\mvnw.cmd spring-boot:run



### 3. Pristup aplikaciji
- **Glavna aplikacija:** http://localhost:8080
- **H2 konzola:** http://localhost:8080/h2-console

## 📁 Struktura projekta

```
src/main/java/org/example/BootSpringBiblioteka/
├── controller/          # Spring MVC kontroleri
│   ├── BookController.java
│   ├── AuthorController.java
│   ├── CategoryController.java
│   ├── UserController.java
│   ├── LoanController.java
│   └── LoginController.java
├── model/              # JPA entiteti
│   ├── Book.java
│   ├── Author.java
│   ├── Category.java
│   ├── User.java
│   ├── Loan.java
│   └── Role.java
├── repository/         # Spring Data JPA repozitoriji
│   ├── BookRepository.java
│   ├── AuthorRepository.java
│   ├── CategoryRepository.java
│   ├── UserRepository.java
│   └── LoanRepository.java
├── service/           # Business logika
│   ├── BookService.java
│   ├── AuthorService.java
│   ├── CategoryService.java
│   ├── UserService.java
│   └── LoanService.java
├── security/          # Spring Security konfiguracija
│   └── SecurityConfig.java
└── validation/        # Custom validatori
    ├── UniqueISBN.java
    └── UniqueISBNValidator.java
```

## 🔧 Konfiguracija

Glavne postavke se nalaze u `application.properties`:
- Konfiguracija H2 baze podataka
- JPA/Hibernate postavke
- Spring Security konfiguracija

## 👨‍💻 Razvoj

### Build projekta
```bash
mvn clean install
```

### Pokretanje testova
```bash
mvn test
```

## 📝 Licenca

Ovaj projekt je kreiran za edukacijske svrhe.

## 🤝 Doprinosi

Doprinosi su dobrodošli! Molimo vas da:
1. Fork repozitorija
2. Kreirate feature granu
3. Napravite commit promjena
4. Otvorite Pull Request

## 📞 Kontakt

Za pitanja i podršku, otvorite Issue na GitHub-u.

---

**Napomena:** Ova aplikacija koristi H2 in-memory bazu podataka, što znači da se svi podaci brišu nakon restartovanja aplikacije.

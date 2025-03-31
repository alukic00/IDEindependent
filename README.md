# 📦 Sistem za upravljanje porudžbinama  

Ovaj projekat predstavlja **REST API** za upravljanje porudžbinama sa podrškom za **autentifikaciju i autorizaciju**.  
Korisnici mogu:
✔️ Registrovati se i prijaviti putem JWT autentifikacije
✔️ Kreirati, pregledati i brisati porudžbine
✔️ Dohvatiti listu Top 10 kupovnih i prodajnih porudžbina

Aplikacija koristi Spring Boot, SQLite bazu podataka i JWT za sigurnost.

## 🛠️ Preduslovi  

- **Java 17** ili novija verzija  
- **Maven 3.8+**  
- **Nije potrebna dodatna baza podataka** – koristi se ugrađeni **SQLite**  

## 📥 Instalacija  

1. Raspakujte **ZIP** fajl sa projektom  
2. Otvorite terminal u raspakovanom folderu  

## ⚙️ Konfiguracija  

Projekat je već konfigurisan za rad sa **SQLite** bazom podataka.  
Baza će se automatski kreirati u **`database`** folderu pri prvom pokretanju aplikacije.  

## ⚙️ Kako funkcioniše procesiranje porudžbina?
## 1️⃣ Kreiranje porudžbine:

Korisnik kreira BUY (kupovnu) ili SELL (prodajnu) porudžbinu putem API-ja.

Porudžbina se odmah čuva u SQLite bazi podataka sa statusom ACTIVE.

## 2️⃣ Sortiranje u knjigu porudžbina (Order Book):

BUY porudžbine se sortiraju opadajuće po ceni (veća cena ima prioritet).

SELL porudžbine se sortiraju rastuće po ceni (niža cena ima prioritet).

## 3️⃣ Izvršenje trgovine (matching engine):

Kada stigne nova BUY porudžbina, ona se uparuje sa najjeftinijom dostupnom SELL porudžbinom.

Kada stigne nova SELL porudžbina, ona se uparuje sa najskupljom dostupnom BUY porudžbinom.

Ako postoji dovoljno likvidnosti, porudžbina se odmah zatvara i označava kao COMPLETED.

Ako nije u potpunosti izvršena, deo porudžbine ostaje otvoren u knjizi.

## 4️⃣ Dohvatanje podataka:

Korisnici mogu tražiti Top 10 kupovnih i prodajnih porudžbina kako bi videli tržišne trendove.

## 🚀 Pokretanje aplikacije  

```bash
mvn spring-boot:run
```

Aplikacija će biti dostupna na: [http://localhost:8080](http://localhost:8080)  

---

## 🛠️ Testiranje API-ja pomoću Postmana  

### 🔑 **Autentifikacija**  

#### 1️⃣ Registracija korisnika  
- **Metoda:** `POST`  
- **URL:** `http://localhost:8080/auth/register`  
- **Headers:**  
  - `Content-Type: application/json`  
- **Body (JSON):**  
  ```json
  {
      "username": "testuser",
      "password": "testpassword"
  }
  ```

#### 2️⃣ Prijava korisnika  
- **Metoda:** `POST`  
- **URL:** `http://localhost:8080/auth/login`  
- **Headers:**  
  - `Content-Type: application/json`  
- **Body (JSON):**  
  ```json
  {
      "username": "testuser",
      "password": "testpassword"
  }
  ```
✅ **Odgovor:** JWT token koji se koristi u ostalim zahtevima.  

#### 3️⃣ Odjava korisnika  
- **Metoda:** `POST`  
- **URL:** `http://localhost:8080/auth/logout`  
- **Headers:**  
  - `Authorization: Bearer <token>`  
  - `Content-Type: application/json`  

---

## 📑 **Upravljanje porudžbinama**  

> 🔒 **Samo autorizovani korisnici mogu pristupiti metodama koje zahtevaju autentifikaciju!**  

### 🛍️ **1. Kreiranje porudžbine**  
- **Metoda:** `POST`  
- **URL:** `http://localhost:8080/orders`  
- **Headers:**  
  - `Authorization: Bearer <token>`  
  - `Content-Type: application/json`  
- **Body (JSON):**  
  ```json
  [
      {
          "price": 155.75,
          "amount": 5,
          "type": "BUY"
      }
  ]
  ```

### 📋 **2. Dohvatanje svih porudžbina**  
- **Metoda:** `GET`  
- **URL:** `http://localhost:8080/orders`  
- **Headers:** *Nema potrebnih headera*  

### 💰 **3. Dohvatanje top 10 kupovnih porudžbina**  
- **Metoda:** `GET`  
- **URL:** `http://localhost:8080/orders/top-buys`  

### 🏷️ **4. Dohvatanje top 10 prodajnih porudžbina**  
- **Metoda:** `GET`  
- **URL:** `http://localhost:8080/orders/top-sells`  

### 📌 **5. Dohvatanje aktivnih porudžbina**  
- **Metoda:** `GET`  
- **URL:** `http://localhost:8080/orders/active`  

### ❌ **6. Brisanje porudžbine**  
- **Metoda:** `DELETE`  
- **URL:** `http://localhost:8080/orders/{id}`  
- **Headers:**  
  - `Authorization: Bearer <token>`  
  - `Content-Type: application/json`  
- **Primer:**  
  ```plaintext
  http://localhost:8080/orders/1
  ```

---

## 📝 **Dodatne napomene:**  
✔ Za metode koje zahtevaju autentifikaciju (`@PreAuthorize`), obavezno dodajte `Authorization` header sa validnim **JWT tokenom**  
✔ Prilikom testiranja DELETE metode, zamenite `{id}` sa stvarnim **ID-jem** porudžbine  
✔ Za kreiranje porudžbina možete slati i **jednu porudžbinu** unutar niza (lista sa jednim elementom)  

🚀 **Sada možete testirati API i raditi sa porudžbinama!**

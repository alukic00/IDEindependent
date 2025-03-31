# 📦 Sistem za upravljanje porudžbinama  

Ovaj projekat predstavlja **REST API** za upravljanje porudžbinama sa podrškom za **autentifikaciju i autorizaciju**.  

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

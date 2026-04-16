# Java Contact Management Application

**Standalone Java console application for managing personal and business contacts** — developed as my **final thesis (Bachelor's degree project)**.

The application provides full **CRUD** functionality and follows a clean **multi-layered architecture**, ensuring separation of concerns, maintainability, and scalability.

![Java](https://img.shields.io/badge/Java-21-orange) 
![Maven](https://img.shields.io/badge/Maven-Yes-blue) 
![OpenCSV](https://img.shields.io/badge/OpenCSV-Used-green)

---

## Features

- View all contacts  
- Add new contacts  
- Update existing contacts  
- Delete contacts  
- Manage multiple contact details (phone numbers, emails, links)  
- Search contacts by name and/or surname  
- Input validation and proper exception handling  

---

## Architecture

The project is built using a **four-layered architecture**:

- **Presentation Layer** — Console-based user interface (menu and user input)
- **Service Layer** — Business logic and data validation
- **DAO Layer** — Data access and persistence (CSV file operations)
- **Model Layer** — Domain classes (`Kontakt`, `KontaktDetalji`, `Email`, `Telefon`, `Link`)

This design follows **Object-Oriented Programming principles** and the **Separation of Concerns** principle.

---

## Data Persistence

Data is stored in **CSV files** using the **OpenCSV** library:

- `kontakt.csv` — Stores basic contact information  
- `kontakt_detalji.csv` — Stores additional details (one-to-many relationship)

---

## Technologies & Tools

- **Java 21** (LTS)
- **Maven** — Dependency and build management
- **OpenCSV** — CSV parsing and generation
- **IntelliJ IDEA** Community Edition

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/milos-7a/JavaContactsMngr.git
   cd JavaContactsMngr
2. Run the application using Maven:
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.milos.contactsmanager.main"
  Or simply run the Main class directly from IntelliJ IDEA.

 ## Author 
 
 Miloš Nikolić, Bachelor of Science in Information Technology, Final Thesis Project

 ## License
 This project is licensed under the MIT License.


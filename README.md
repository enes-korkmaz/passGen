# 🔐 PassGen – Simulated Cloud Service for One-Time Locker Access

## 📘 Project Overview

As part of the **DevTools** course in the 3rd semester, we developed the project **PassGen** in a **micro-team of four students**.  
PassGen is a component-based system simulating a cloud service, inspired by modern locker systems (e.g. automated parcel stations).

The goal was to create a **modular Java application** that allows users to authenticate, select a digital locker, and request a **one-time access token** to temporarily unlock a compartment. An admin account enables the creation and management of lockers and their compartments.

---

## 🧩 Architecture & Structure

The focus was on implementing a **well-defined component interface (facade)** based on a given interface contract.  
To ensure correctness and reliability, we wrote extensive **JUnit tests**, aiming for a **minimum code coverage of 80%**, measured using **Jacoco**.

For clean separation between interfaces and implementation, the project was split into **multiple Java modules**:

- [apis](apis): Public interfaces and contracts
- [components](components): Implementation logic (with `provider` subpackages)
- [javafx](javafx): UI layer and demo frontend (optional)

---

## ⚙️ Tooling & Build System

We used **Gradle** for:

- Build automation and dependency management
- Managing libraries like `JavaFX`, `JUnit`, etc.
- Generating documentation (`Javadoc`)
- Running tests and generating coverage reports (`Jacoco`)
- Structuring multi-module projects

---

## 👥 Team Collaboration

A unique challenge was that **multiple micro-teams worked in the same Git repository**.  
This required:

- Consistent use of **Git workflows** (branches, merge requests)
- Strict adherence to **coding conventions**
- Coordination and integration across module boundaries

---

## 🚀 Getting Started

- If the **Gradle Wrapper** is missing, you can generate it using the following command:

  ```bash
  gradle wrapper
  ```
- 📄 User and developer documentation was maintained externally on Confluence and is not included in this repository.

## 🔐 Test Credentials

To test the application, the following test accounts can be used.
Note: These are pseudo-credentials for simulation purposes only.

- User 1 
  - Email: user1 
  - Password: password1


- User 2 
  - Email: user2 
  - Password: password2


- ...


- Admin (token login disabled)
  - Email: admin 
  - Password: admin
  
## ▶️ Launching the Application

To start the application, run the following class: [Main.java](javafx/src/main/java/de/hhn/it/devtools/javafx/Main.java)

Recommended: Use Gradle to run the application:

```bash
  ./gradlew run 
```

Or via the Gradle task: `Tasks -> application -> run` in your IDE.

---

## 📄 License

This project is licensed under the [GNU General Public License v3.0](LICENSE).

⚠️ Some files included in this project were provided as part of the university course material and are not subject to the license applied to this repository, 
as well as Gradle wrapper files (gradlew, gradlew.bat, and files inside the gradle/ directory), which are licensed under the Apache License 2.0.

---
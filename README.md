# MedLedger – Immutable Medical History Tracking System
📌 Overview

MedLedger is a backend system designed to securely manage and track patient medical history with a strong focus on immutability and auditability.

Instead of allowing silent updates to critical health records, MedLedger treats every modification like a “commit” — creating a transparent, traceable history of changes similar to version control systems.

The goal is simple: medical records should never be editable without accountability.

🚩 Problem Statement

Medical data today is often fragmented across hospitals, clinics, and systems. This fragmentation increases the risk of incomplete context during treatment, potentially leading to incorrect decisions.

Additionally, most systems allow updates to records without maintaining a clear and tamper-proof history of changes.

MedLedger addresses this by introducing an immutable audit layer where every modification is recorded, versioned, and cryptographically secured — ensuring trust and traceability.

🚀 Planned Features

🔐 JWT Authentication
Secure stateless authentication using JSON Web Tokens.

👥 Role-Based Access Control (RBAC)
Two primary roles:

Doctor

Patient

📜 Immutable Medical Records
Records cannot be directly overwritten. Each update creates a new version.

🧾 Git-Style Commit Audit Log
Every change is stored as a commit containing:

Previous state reference

Change metadata

Timestamp

Author (Doctor/Patient)

⚙️ Optimistic Locking
Prevents lost updates in concurrent modification scenarios.

🔎 Hash-Based Tamper Detection
Each record version contains a cryptographic hash to detect unauthorized modifications.

☁️ Cloud Deployment (Planned)
Containerized using Docker and deployed to AWS.

🛠️ Tech Stack

Spring Boot – Backend framework

Spring Security – Authentication & authorization

JPA / Hibernate – ORM & persistence layer

MySQL / PostgreSQL – Relational database

Docker – Containerization

AWS (Planned) – Cloud infrastructure deployment

🧠 Design Philosophy

MedLedger is built around three principles:

Immutability over mutation

Transparency over hidden updates

Security by design

By combining role-based access, audit logging, and cryptographic verification, the system aims to simulate how version control systems maintain trust — but applied to healthcare data.

📌 Project Status

Currently under active development. Core authentication, role management, and versioning architecture are in progress.

Future improvements may include:

Event-driven architecture

Advanced audit visualization

AI-based anomaly detection in medical updates


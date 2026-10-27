# 🛡️ Bantay Baryo: Incident and Complaint Reporting System

## 📌 Project Description

**Bantay Baryo** is a Java-based desktop application designed to organize and monitor incidents and complaints within a barangay. Manual reporting can make submitted concerns difficult to organize, monitor, and follow up.

Through this system, residents can submit an incident or complaint by providing its type, category, location, description, and other relevant details. Each submission receives a unique Report ID that serves as a reference when monitoring its status. Authorized barangay personnel can review reports, update their status, and send notifications to residents.

## ✨ Key Features

- **Role-Based Access Control:** Three distinct user roles (Resident, Barangay Staff, Admin) with tailored dashboards and permissions.
- **Dynamic Report Submission:** Residents can submit Incidents (e.g., Theft, Fire) or Complaints (e.g., Noise Disturbance, Boundary Disputes). The category dropdown adapts based on the report type selected.
- **Report Queue & Management:** Barangay Staff can view all reports, filter them by status, and update them to "In Progress" or "Resolved".
- **Real-time Notifications:** Residents receive alerts when their report status changes, and Staff are alerted when new reports are filed.
- **User Management:** Admins can register new users (generating role-specific IDs like `R-`, `S-`, or `A-`), deactivate/reactivate accounts, and oversee the system.
- **Dashboard Overview:** A summary dashboard with clickable stat cards to filter reports by Pending, In Progress, Resolved, or Total.

## 🛠️ Technology Stack

- **Language:** Java
- **GUI Framework:** Java Swing (AWT)
- **Data Storage:** Java Object Serialization (`.dat` files)

## 📂 Project Structure & Data Management

The system uses Object-Oriented Programming principles and saves data locally using `FileHandler.java`.

- `users.dat` - Stores all user accounts (Residents, Staff, Admins).
- `reports.dat` - Stores all submitted Incident and Complaint reports.
- `notifications.dat` - Stores system alerts sent to users.

### Core Classes

- `User` (Abstract) -> `Resident`, `BarangayStaff` -> `Admin`
- `Report` (Abstract) -> `IncidentReport`, `ComplaintReport`
- `Notification`, `FileHandler`, `Main`

## 🚀 How to Run the System

### Prerequisites

- Java Development Kit (JDK) 8 or higher installed on your machine.
- An IDE (IntelliJ IDEA, Eclipse, or VS Code) or a terminal.

### Steps

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/Bantay-Baryo.git
    ```

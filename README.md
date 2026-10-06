# 🛡️ BantayBaryo: Incident and Complaint Reporting System

A Java-based desktop application designed to streamline the reporting, organization, and monitoring of localized community concerns. Built as the final performance task for **PF 101: Object-Oriented Programming**.

**SDG Alignment:** [SDG 16 - Peace, Justice and Strong Institutions](https://sdgs.un.org/goals/goal16)  
This system promotes organized, accountable, and transparent handling of community grievances by ensuring every submitted incident or complaint is logged, tracked, and systematically addressed.

---

## 🚀 Core Features
* **Role-Based Workflows:** Distinct access levels for **Residents** (submitting concerns, tracking statuses) and **Barangay Staff** (reviewing reports, logging actions, updating statuses).
* **Automated Ticket Tracking:** Generates unique, immutable Report IDs to track concerns across a transparent lifecycle (`PENDING` → `IN_PROGRESS` → `RESOLVED`).
* **Persistent Data Storage:** Operates without an external database server, utilizing Java File I/O to read, write, and update records in local `.txt` files.
* **Modern GUI:** Features a clean, responsive CardLayout interface built using Java Swing.

---

## 🧩 Object-Oriented Architecture
This system was engineered from the ground up to demonstrate the four core pillars of Object-Oriented Programming:

1. **Encapsulation:** All data models (`User`, `Report`) use strictly `private` attributes, safeguarded by public getter and setter methods to ensure data integrity.
2. **Abstraction:** Employs abstract base classes (`User` and `Report`) to establish mandatory structural blueprints without defining implementation details.
3. **Inheritance:** Concrete subclasses (`Resident`, `BarangayStaff`, `IncidentReport`, `ComplaintReport`) `extend` their abstract parents to inherit foundational attributes, reducing code redundancy.
4. **Polymorphism:** System operations dynamically handle generic `Report` or `User` objects, seamlessly executing overridden methods (e.g., `getReportType()`) based on the specific instantiated subclass at runtime.

---

## 📂 System File Structure (Makeshift Database)
To fulfill course constraints, data persistence is handled natively via Java File Handling (`BufferedReader` / `BufferedWriter`) using the pipe (`|`) delimiter.
* `users.txt` - Stores authentication credentials, roles, and demographic data.
* `reports.txt` - Stores incident and complaint tickets.
* `actions.txt` - Stores the chronological audit trail of staff interventions.

---

## 💻 Getting Started (For Developers)

### Prerequisites
* Java Development Kit (JDK) 17 or higher.
* IntelliJ IDEA, NetBeans, or VS Code.

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/yourusername/BantayBaryo.git](https://github.com/yourusername/BantayBaryo.git)
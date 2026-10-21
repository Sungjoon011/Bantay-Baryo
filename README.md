# 🛡️ Bantay Baryo

## Community Incident and Complaint Management System

Bantay Baryo is a Java Swing desktop application designed to help residents submit barangay incidents and complaints while allowing Barangay Staff and Administrators to manage, monitor, and update submitted reports.

The system provides role-based dashboards, report tracking, notification updates, user management, and persistent local data storage.

> This project was developed as an Object-Oriented Programming project demonstrating inheritance, polymorphism, abstraction, encapsulation, Java Swing GUI development, and object serialization.

---

## ✨ Features

### 🏠 Public Home Page

- Displays the Bantay Baryo branding and community-focused introduction.
- Provides navigation to the Home and Login pages.
- Includes a custom application logo.
- Explains that user accounts are created and managed by the Barangay Administrator.

### 🔐 Login and Authentication

- Login using a username and password.
- Prevents login when required fields are empty.
- Validates user credentials.
- Prevents inactive users from logging in.
- Includes a password visibility toggle.
- Supports different user roles:
  - Resident
  - Barangay Staff
  - Admin

### 👤 Resident Features

Residents can:

- File a new incident report.
- File a new complaint report.
- Select a report category.
- Enter the report location.
- Provide a detailed report description.
- Add the incident date and time.
- Provide the names of persons involved in an incident.
- Provide the respondent or entity involved in a complaint.
- View their submitted reports.
- Filter reports by status.
- View complete report details.
- View the report status and action history.
- Receive notifications when their report status changes.
- Update their personal information.
- Change their account password.

### 🧑‍💼 Barangay Staff Features

Barangay Staff can:

- View the report dashboard.
- View summary statistics for:
  - Pending reports
  - In-progress reports
  - Resolved reports
  - Total reports
- View the complete report queue.
- Filter reports by status.
  - The queue refreshes in place when the status selection changes.
  - Supported filters include All Statuses, `PENDING`, `IN PROGRESS`, and `RESOLVED`.
- Open detailed report information.
- Mark reports as `IN_PROGRESS`.
- Mark reports as `RESOLVED`.
- Receive notifications when new reports are submitted.
- View their account profile.
- Update their personal information and password.

### 🛠️ Administrator Features

Administrators can:

- Use the Barangay Staff report-management features.
- View all submitted reports.
- Register new users.
- Create Resident, Barangay Staff, and Admin accounts.
- View registered users in a user-management table.
- Activate or deactivate user accounts.
- Preserve historical report references when accounts are deactivated.
- Update personal information and passwords.

### 🔔 Notification System

The application includes a notification system that:

- Notifies Barangay Staff and Administrators when a new report is submitted.
- Notifies residents when their report status changes.
- Displays an unread notification indicator.
- Supports filtering notifications by:
  - All
  - Unread
  - Read
- Automatically marks notifications as read when viewed.
- Allows users to delete individual notifications.
- Allows users to clear all of their notifications.
- Allows notifications to open the related report details.

### 📊 Report Management

Reports follow a status workflow:

```
PENDING → IN_PROGRESS → RESOLVED
```

Each report contains information such as:

- Report ID
- Report type
- Reporter ID
- Category
- Location
- Description
- Date submitted
- Current status
- Persons involved or respondent name

Report IDs use the following formats:

- `IR-####` for Incident Reports
- `CR-####` for Complaint Reports

Resolved reports cannot be moved back to an earlier status.

---

## 🖥️ User Interface

Bantay Baryo uses Java Swing and provides:

- A modern card-based interface.
- Role-based dashboard navigation.
- Sidebar navigation.
- Responsive table layouts.
- Status badges and colored status indicators.
- Modal dialogs for report details and user registration.
- Scrollable profile and report forms.
- Custom branding colors and logo support.
- Interactive dashboard statistic cards.
- Double-click report detail viewing for staff users.

---

## 🧱 Object-Oriented Design

The project demonstrates the four major principles of Object-Oriented Programming.

### Encapsulation

Class fields are kept private and accessed through getter and setter methods.

Examples include:

- User information
- Report information
- Notification information
- Account status
- Profile details

### Abstraction

Abstract base classes define common properties and behaviors shared by related classes.

Examples:

- `User`
- `Report`

### Inheritance

Specialized classes inherit common functionality from parent classes.

```
User
├── Resident
└── BarangayStaff
    └── Admin

Report
├── IncidentReport
└── ComplaintReport
```

### Polymorphism

The application uses parent-class references to work with different object types.

For example, the application can handle both `IncidentReport` and `ComplaintReport` objects through the common `Report` type.

---

## 💾 Data Persistence

The application stores data locally using Java object serialization.

The following files are created in the project root:

| File | Purpose |
| `users.dat` | Stores registered user accounts |
| `reports.dat` | Stores incident and complaint reports |
| `notifications.dat` | Stores user notifications |

The `FileHandler` class is responsible for:

- Saving users
- Loading users
- Saving reports
- Loading reports
- Saving notifications
- Loading notifications

The application automatically creates sample data when no user data exists.

> The `.dat` files contain serialized Java objects. They should not be manually edited as plain text.

---

## 📁 Project Structure

```
Bantay-Baryo/
├── bantaybaryo/
│   ├── Admin.java
│   ├── BarangayStaff.java
│   ├── ComplaintReport.java
│   ├── DashboardPanel.java
│   ├── FileHandler.java
│   ├── HomePanel.java
│   ├── IncidentReport.java
│   ├── LoginPanel.java
│   ├── Main.java
│   ├── Notification.java
│   ├── Report.java
│   ├── Resident.java
│   ├── User.java
│   └── resources/
│       └── logo.png
│
├── Bantay-Baryo Proposal/
│   ├── PF101_Proposal_Document.docx
│   └── PF_Proposal.drawio.png
│
├── notifications.dat
├── reports.dat
├── users.dat
├── .gitignore
└── README.md
```

---

## ⚙️ Requirements

Before running the application, make sure you have:

- Java Development Kit 17 or higher
- Java Swing support
- IntelliJ IDEA, NetBeans, VS Code, or another Java IDE

No external database or third-party library is required.

---

## 🚀 Running the Application

### Option 1: Run Using an IDE

1. Clone the repository:

   ```bash
   git clone https://github.com/Sungjoon011/Bantay-Baryo.git
   ```

2. Open the project in your preferred Java IDE.

3. Make sure the project uses JDK 17 or higher.

4. Locate:

   ```
   bantaybaryo/Main.java
   ```

5. Run the `Main` class.

### Option 2: Compile and Run Using the Terminal

From the repository root, compile the Java source files:

```bash
javac bantaybaryo/*.java
```

Run the application:

```bash
java bantaybaryo.Main
```

If the application cannot find the logo, make sure the following file exists:

```text
bantaybaryo/resources/logo.png
```

---

## 🔑 Default Demo Accounts

When the application is launched for the first time without existing user data, sample accounts are created automatically.

| Role | Username | Password |
| Resident | `user` | `user123` |
| Barangay Staff | `staff` | `staff123` |
| Admin | `admin` | `admin123` |

> These are demo credentials only. They should be changed before using the system in a real environment.

---

## 🔄 Report Workflow

### Resident Workflow

```
Login
  ↓
File a Report
  ↓
Submit Incident or Complaint
  ↓
Report receives a unique ID
  ↓
View report under My Reports
  ↓
Receive status notifications
```

### Staff Workflow

```
Login
  ↓
Open Report Queue
  ↓
Review submitted report
  ↓
Mark as In Progress
  ↓
Mark as Resolved
  ↓
Resident receives a notification
```

### Administrator Workflow

```
Login
  ↓
Manage Users
  ↓
Register new accounts
  ↓
Activate or deactivate accounts
  ↓
Manage reports and notifications
```

---

## 📌 Report Types

### Incident Report

An Incident Report is used for events involving a specific incident, such as:

- Public safety concerns
- Noise disturbances
- Fights
- Accidents
- Other community incidents

Incident reports may include:

- Incident date and time
- Persons involved
- Location
- Detailed description

### Complaint Report

A Complaint Report is used to report an issue involving a person, organization, or service, such as:

- Garbage disposal problems
- Infrastructure concerns
- Boundary disputes
- Community service complaints
- Other barangay concerns

Complaint reports may include:

- Respondent name or entity
- Location
- Detailed description

---

## 🧪 Sample Categories

The application currently supports categories such as:

- Noise Disturbance
- Sanitation
- Infrastructure
- Boundary Dispute
- Public Safety
- Others

---

## ⚠️ Current Limitations

- The system uses local serialized `.dat` files instead of a database server.
- Passwords are stored locally for demonstration purposes.
- The application is intended for desktop use.
- There is no online or multi-device synchronization.
- The system does not currently include email or SMS notifications.
- Data files should be backed up before deleting or replacing them.
- The application is designed primarily as an academic and prototype system.

---

## 🎯 Project Purpose

Bantay Baryo demonstrates how object-oriented programming can be used to create a practical community incident and complaint management system.

The project focuses on:

- Java inheritance
- Abstract classes
- Polymorphism
- Encapsulation
- Java Swing GUI development
- File-based data persistence
- Role-based access
- Report status management
- Notification handling
- User account management

---

## 🌍 SDG Alignment

This project supports:

### SDG 16: Peace, Justice and Strong Institutions

Bantay Baryo promotes:

- Organized handling of community concerns
- Transparent report tracking
- Accountable status updates
- Accessible reporting for residents
- Improved communication between residents and barangay staff

---

## 📚 Academic Context

This application was developed as a performance project for demonstrating:

- Object-Oriented Programming concepts
- Java desktop application development
- GUI design using Java Swing
- File input and output
- Basic user authentication
- Role-based system functionality

---
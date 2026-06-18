# 🔥 Firebase CRUD Project

An Android application built using **Java**, **Firebase Firestore**, and **RecyclerView** that demonstrates complete **CRUD (Create, Read, Update, Delete)** operations with real-time database synchronization.

The application allows users to add, view, update, and delete user records stored in Firebase Firestore while automatically reflecting changes in the user interface.

---

# 🚀 Problem Statement

Managing user information efficiently is a common requirement in modern applications. Traditional local storage solutions make data sharing and synchronization difficult.

Developers need a cloud-based solution that enables:

* Real-time data synchronization
* Easy data management
* Scalability
* Secure cloud storage
* Fast CRUD operations

This project solves these challenges by integrating Firebase Firestore with Android to perform seamless Create, Read, Update, and Delete operations.

---

# 💡 Solution

Firebase CRUD Project provides a simple and efficient interface for managing user data using Firebase Firestore.

Users can:

1. Add new user records.
2. View all records in real time.
3. Update existing user information.
4. Delete unwanted records.
5. Automatically synchronize changes with Firebase.

The application uses Firestore Snapshot Listeners to instantly update the UI whenever data changes.

---

# 🎯 Objectives

* Learn Firebase Firestore integration in Android.
* Implement complete CRUD functionality.
* Understand RecyclerView and Adapter architecture.
* Demonstrate real-time database updates.
* Build scalable cloud-connected Android applications.

---

# 🛠 Technology Stack

## Mobile Development

* Java
* Android SDK
* Android Studio

## Backend

* Firebase Firestore

## UI Components

* RecyclerView
* EditText
* Button
* AlertDialog
* LinearLayout

---

# 🧠 Firebase Firestore

Firebase Firestore is a NoSQL cloud database that stores data as collections and documents.

### Collection Used

```text
user
```

### Document Structure

```json
{
  "uid": "abc123",
  "name": "John Doe",
  "email": "john@gmail.com"
}
```

---

# 📂 Project Structure

```text
FirebaseCRUDProject
│
├── app
│
├── java
│   └── com.example.crud_firebase
│       │
│       ├── MainActivity.java
│       ├── updateActivity.java
│       ├── ReadDataAdapter.java
│       └── UserModel.java
│
├── res
│   ├── layout
│   │   ├── activity_main.xml
│   │   ├── activity_update.xml
│   │   └── item_data.xml
│
└── README.md
```

---

# ⚙️ Application Workflow

## Step 1: Add User

The user enters:

* Name
* Email

and clicks:

```text
Add to Firebase
```

### Firestore Operation

```java
db.collection("user")
  .document(uid)
  .set(data);
```

---

## Step 2: Read Users

The application listens for Firestore changes in real time.

```java
db.collection("user")
  .addSnapshotListener(...)
```

Whenever data changes:

* RecyclerView updates automatically.
* No manual refresh is required.

---

## Step 3: Update User

Users can click:

```text
Update
```

or select a RecyclerView item.

The selected user's information is loaded into the update screen.

```java
db.collection("user")
  .document(key)
  .set(updatedUser);
```

---

## Step 4: Delete User

Clicking the Delete button shows a confirmation dialog.

```java
builder.setMessage(
    "Are you sure you want to delete this user?"
);
```

If confirmed:

```java
db.collection("user")
  .document(uid)
  .delete();
```

The user is immediately removed from Firestore and RecyclerView.

---

# 📱 User Interface

## Main Screen

Contains:

* Name Input Field
* Email Input Field
* Add to Firebase Button
* RecyclerView

### Purpose

Allows users to create and view records.

---

## Update Screen

Contains:

* Name Input Field
* Email Input Field
* Update Button

### Purpose

Allows modification of existing user data.

---

## RecyclerView Item

Each item displays:

* User Name
* User Email
* Update Button
* Delete Button

---

# ✨ Features

* Create new user records.
* Read data in real time.
* Update existing users.
* Delete users with confirmation dialog.
* Firebase Firestore integration.
* RecyclerView implementation.
* Real-time synchronization.
* Cloud database storage.
* Automatic UI updates.
* Simple and responsive design.

---

# 🔥 CRUD Operations

## Create

Adds new user data to Firestore.

```java
db.collection("user")
  .document(uid)
  .set(data);
```

---

## Read

Fetches all user records.

```java
db.collection("user")
  .addSnapshotListener(...)
```

---

## Update

Updates existing user details.

```java
db.collection("user")
  .document(uid)
  .set(updatedUser);
```

---

## Delete

Removes user data from Firestore.

```java
db.collection("user")
  .document(uid)
  .delete();
```

---

# 📊 Benefits

## Real-Time Updates

Changes are instantly reflected across the application.

## Cloud Storage

Data remains available across devices.

## Scalability

Firestore can handle large amounts of data.

## Reduced Complexity

Firebase manages backend infrastructure.

## User-Friendly

Simple interface for managing records.

---

# 🔄 Data Flow

```text
User Input
    │
    ▼
MainActivity
    │
    ▼
Firebase Firestore
    │
    ▼
Snapshot Listener
    │
    ▼
RecyclerView Adapter
    │
    ▼
Updated UI
```

---

# 🔮 Future Enhancements

* User Authentication
* Search Functionality
* Pagination
* Profile Images
* Form Validation
* Email Verification
* Sorting and Filtering
* Dark Mode
* Offline Data Persistence
* MVVM Architecture

---

# 📸 Screenshots

Add your project screenshots here.

## Home Screen

![Home Screen](screenshots/home.png)

## Update Screen

![Update Screen](screenshots/update.png)

## Delete Confirmation

![Delete Confirmation](screenshots/delete.png)

---

# 🧪 How to Run

## Clone Repository

```bash
git clone https://github.com/your-username/FirebaseCRUDProject.git
```

---

## Open in Android Studio

```text
File → Open → Select Project Folder
```

---

## Configure Firebase

1. Create a Firebase Project.
2. Register Android Application.
3. Download:

```text
google-services.json
```

4. Place it inside:

```text
app/
```

---

## Sync Gradle

```text
Sync Project with Gradle Files
```

---

## Run Application

Run on:

* Android Emulator
* Physical Android Device

---

# 📚 Learning Outcomes

This project demonstrates:

* Firebase Firestore Integration
* Android RecyclerView
* Java Android Development
* CRUD Operations
* Real-Time Database Synchronization
* Adapter and ViewHolder Pattern
* Firestore Snapshot Listeners
* Cloud-Based Data Management

---

# 👨‍💻 Author

**Vaidik Nakrani**

B.Tech Computer Science & Engineering

GitHub:
https://github.com/vaidiknakrani

---

# 📄 License

This project is created for educational and learning purposes.

Feel free to use, modify, and extend it for academic projects, practice, and research.

# Stumble Grievance App

## Overview
The Stumble Grievance App is designed to streamline the process of submitting and managing student grievances. This Android application allows students to submit grievances anonymously under specific categories and enables staff to view these grievances sorted by category. Both students and staff have their separate login portals, and students can register themselves.

## Features
- **Student Registration and Login**: Students can register themselves and log in to submit their grievances.
- **Staff Login**: Staff members can log in to view and manage grievances.
- **Anonymous Grievance Submission**: Students can submit grievances anonymously.
- **Category-wise Grievance Management**: Grievances are categorized into:
  - Food
  - Hostel
  - Classroom
  - Other

## User Roles
### Students
- **Register**: Create an account using a registration form.
- **Login**: Access the system using their credentials.
- **Submit Grievances**: Anonymously submit grievances under specific categories.

### Staff
- **Login**: Access the system using their credentials.
- **View Grievances**: View submitted grievances sorted by category.

## Installation
To install and run the Stumble Grievance App on your Android device, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone https://github.com/karankumar-g/Stumble-Grievance.git
    cd Stumble-Grievance
    ```

2. **Open in Android Studio**:
    - Open Android Studio.
    - Click on "Open an existing Android Studio project".
    - Navigate to the `Stumble-Grievance` directory and open it.

3. **Build the Project**:
    - Click on "Build" in the menu bar.
    - Select "Rebuild Project".

4. **Run the App**:
    - Connect your Android device via USB or start an emulator.
    - Click on "Run" in the menu bar and select "Run 'app'".
    - Choose your device and run the application.

## Technologies Used
- **Frontend**: XML for UI layouts
- **Backend**: Java for Android development
- **Database**: Sqlite
- **Authentication**: Custom Authentication

## Usage
### Student
1. **Register**: Navigate to the registration screen and create an account.
2. **Login**: Use your credentials to log in.
3. **Submit Grievance**:
    - Choose a category (Food, Hostel, Classroom, Other).
    - Enter your grievance details.
    - Submit anonymously.

### Staff
1. **Login**: Use your credentials to log in.
2. **View Grievances**:
    - Access grievances sorted by categories.
    - Manage and address grievances as needed.

## Contributing
We welcome contributions! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

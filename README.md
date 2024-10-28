# Login-With-Compose

## Overview

Login With Compose is a modern Android application that provides a seamless experience for users to register, log in, and manage their accounts. Built with Jetpack Compose and integrated with Firebase, this app leverages the latest technologies to offer a user-friendly interface and robust backend services.

### Features

- User registration
- Login with email and password
- Anonymous login
- User logout

## Technologies Used

- **Kotlin**: The primary programming language for Android development.
- **Jetpack Compose**: A modern toolkit for building native UI.
- **Firebase Authentication**: For secure user authentication and management.
- **Hilt**: A dependency injection library for Android.
- **Coroutines**: For asynchronous programming and managing background tasks.

## Screenshots

<div style="display: flex; justify-content: space-around;">
    <img src="https://github.com/user-attachments/assets/87c5ba54-8731-4a29-a8a0-af9468c2661c" alt="Login Screen" width="200" style="margin-right: 20px;"/>
    <img src="https://github.com/user-attachments/assets/a099144e-f293-460f-8661-ea4cdb92bc40" alt="Home Screen" width="200"/>
</div>

## Getting Started

To run the application on your local machine, follow these steps:

### Prerequisites

- **Android Studio**: Make sure you have the latest version installed. Download it from [here](https://developer.android.com/studio).
- **Java Development Kit (JDK)**: JDK 1.8 or higher is required.

## Firebase Setup

#### Create a Firebase Project

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Click on "Add project" and follow the setup steps to create a new project.

#### Register Your App

1. In your Firebase project, click on the "Add app" button and select **Android**.
2. Enter your app's package name (e.g., `com.example.loginwithcompose`) and other required details.

#### Download `google-services.json`

1. After registering your app, you will be prompted to download the `google-services.json` file.
2. Place this file in the `app/` directory of your project. This file contains important configuration information for Firebase services.

#### Enable Firebase Authentication

1. In the Firebase console, navigate to the **Authentication** section.
2. Click on "Get Started" to enable the authentication module.
3. Under the **Sign-in method** tab, enable the sign-in methods you wish to support (e.g., **Email/Password**). This will allow users to register and log in using their email addresses.

#### Set Up Firestore (Optional)

- If your app requires Firestore for storing user data, navigate to the **Firestore Database** section in the Firebase console and create a database. Follow the prompts to set up Firestore.

### Configure Your Project

#### Open `build.gradle` Files

1. Ensure that the `google-services` plugin is applied in your project-level `build.gradle` file:

   ```groovy
   buildscript {
       dependencies {
           classpath 'com.google.gms:google-services:4.3.10' // Update to the latest version
       }
   }

### Clone the Repository

Open your terminal and clone the repository using the following command:

```bash
git clone [https://github.com/AlexRaya25/Login-With-Compose.git]
cd Login-With-Compose

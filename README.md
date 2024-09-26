# Tip Calculator App

## Overview
The Tip Calculator App is a simple Android application designed to help users easily calculate tips and split bills among multiple people. Users can input their bill amount, adjust the tip percentage using a SeekBar, buttons, or user input, and see the calculated tip, total amount due, and the amount each person needs to pay.

## Project Details
**Course:** CSC 3312 - Make Your Own Tip Calculator  
**Instructor:** Dr. Williams, High Point University  
**Semester:** Fall 2024

### Assignment Goals
The objective of this assignment is to create a functional Tip Calculator using Android Studio, practicing Android app development fundamentals, including:
- User interface (UI) design
- Event handling
- Input validation
- Implementing advanced features to showcase creativity and problem-solving skills.

### Features
- **Basic Features (C-Level Requirements):**
  - Accept user input for the bill amount.
  - Allow selection of tip percentage using buttons (e.g., 10%, 15%, etc.).
  - Display calculated tip amount and total bill.
  - Functionality on the Pixel 5 Emulator.

- **Intermediate Features (B-Level Requirements):**
  - Input validation to ensure valid inputs.
  - Graceful handling of incorrect or missing inputs with error messages.

- **Advanced Features (A-Level Requirements):**
  - **Custom Tip Percentage:** Allow user to enter a custom tip percentage.
  - **Bill Splitting:** Functionality to split the bill among a user-defined number of people, displaying each person’s contribution.

## Getting Started

### Prerequisites
- Android Studio

### Installation
1. Clone the repository or download the source code.
2. Open Android Studio and import the project.
3. Build and run the application on an emulator or physical device.

### Usage
1. Enter the total bill amount in the "Bill Total" field.
2. Adjust the tip percentage using the SeekBar, "+" and "-" buttons, or by typing in the "Tip Percent" field.
3. Enter the number of people in the party to split the bill.
4. The app will automatically calculate and display:
   - Tip amount
   - Total amount due 
   - Amount per person

## Code Structure
- `MainActivity.java`: The main activity containing the logic for user input, calculations, and UI updates.
- UI components include:
  - `EditText` for user inputs bill total, tip percentage, Split amount
  - `SeekBar` for adjusting the tip percentage
  - `Buttons` for increasing/decreasing tip percentage and number of people

## Built With
- Java
- Android SDK
- AndroidX libraries

# 🌅 HorizonTrack

**HorizonTrack** is a clean, modern, and intuitive progress-tracking Android app designed to help you move steadily toward your personal and professional goals.

Define your goals, break them down into milestones, and track your daily progress with clarity and focus. HorizonTrack keeps your eyes on the horizon — one step at a time.

---

## ✨ Features

- 🎯 Create and manage goals with custom milestones  
- ✅ Log daily progress and completed tasks  
- 📈 Visualize progress over time  
- 🧘 Minimalist and distraction-free UI  
- 🌙 Light & Dark mode support  
- 🔄 Offline-first experience  
- 🚀 Smooth animations with Jetpack Compose  

---

## 🖼️ Screenshots

> _Coming soon_

---

## 🏗️ Architecture

HorizonTrack is built following **Clean Architecture principles**, ensuring scalability, testability, and long-term maintainability.

data
├── local
├── remote
├── repository
domain
├── model
├── repository
├── usecase
ui
├── screens
├── components
├── viewmodel

---

### Architectural Pattern
- **MVVM (Model–View–ViewModel)**
- **Unidirectional Data Flow (UDF)**
- **Single Source of Truth**

---

## 🛠️ Tech Stack

### Core
- **Kotlin**
- **Jetpack Compose**
- **Android SDK (API 26+)**

### Architecture & State
- ViewModel
- Kotlin Coroutines
- StateFlow & Flow

### Dependency Injection
- **Hilt (Dagger)**

### Persistence
- **Room** (local database)
- **DataStore** (preferences)

### Navigation
- **Jetpack Navigation Compose**

### UI
- Material 3
- Compose Animations
- Adaptive layouts

---

## 🧪 Testing

HorizonTrack is designed with testability in mind.

### Unit Tests
- JUnit
- MockK
- Coroutine Test

### UI Tests
- Compose UI Testing
- Espresso (interop)

### Coverage
- Domain layer fully unit-tested
- ViewModels tested with fake repositories

---

## 📦 Modules

- `core`: shared utilities and base classes  
- `data`: repositories, data sources, mappers  
- `domain`: business logic and use cases  
- `ui`: screens, composables, view models  

---

## 🔄 State Management

- Immutable UI state
- `UiState` sealed classes
- Explicit event handling via `UiEvent`

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- Kotlin 1.9+

### Clone the repository

```bash
git clone https://github.com/your-username/HorizonTrack.git
Open in Android Studio
Open Android Studio

Select Open an existing project
``````

Run on an emulator or physical device

📌 Roadmap
📊 Advanced analytics & charts

☁️ Cloud sync

🔔 Smart reminders

🏆 Gamification & streaks

📱 Wear OS companion app

🤝 Contributing
Contributions are welcome!

Fork the repository

Create a feature branch

Commit your changes

Open a Pull Request

📄 License

Copyright (c) 2025 Sergio

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
👨‍💻 Author
Sergio
Android Developer • Kotlin & Jetpack Compose
Clean Architecture enthusiast

Focus on the horizon. Progress happens step by step. 🌅


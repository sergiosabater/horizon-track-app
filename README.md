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


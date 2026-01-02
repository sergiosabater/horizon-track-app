# 🌅 HorizonTrack

<div align="center">
  <img src="https://img.shields.io/badge/Platform-iOS%20%7C%20Android-blue?style=for-the-badge&logo=flutter" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Dart-0175C2?style=for-the-badge&logo=dart" alt="Language">
  <img src="https://img.shields.io/badge/Framework-Flutter-02569B?style=for-the-badge&logo=flutter" alt="Framework">
  <img src="https://img.shields.io/badge/Architecture-Clean%20MVVM-orange?style=for-the-badge" alt="Architecture">
  <img src="https://img.shields.io/badge/State-BLoC%2FProvider-purple?style=for-the-badge" alt="State Management">
  <img src="https://img.shields.io/badge/Theme-Light%20%26%20Dark-yellow?style=for-the-badge&logo=flutter" alt="Theme">
</div>

<div align="center">

### 🎯 Keep Your Eyes on the Horizon 🎯
*The minimalist progress tracker that turns your ambitions into achievable milestones.*

</div>

---

## 📱 About the Project

**HorizonTrack** is your personal compass for achieving meaningful goals.  
In a world full of distractions, HorizonTrack provides a clean, focused space to define what matters most, break it into actionable steps, and celebrate every milestone along the way.

No clutter. No noise. Just you, your goals, and the horizon ahead.

### ✨ What Makes HorizonTrack Special?

- 🎯 **Goal-Driven Design** – Create ambitious goals and break them down into manageable milestones
- ✅ **Daily Progress Logging** – Track completed tasks and build momentum day by day
- 📊 **Visual Progress Insights** – Beautiful charts that show how far you've come
- 🧘 **Distraction-Free Experience** – Minimalist UI that keeps you focused on what matters
- 🌓 **Adaptive Theming** – Seamlessly switch between light and dark modes
- 📴 **Offline-First** – Your progress is always accessible, no internet required
- 🎨 **Smooth Animations** – Delightful micro-interactions that feel natural
- 📱 **Cross-Platform** – Native experience on both iOS and Android

---

## 🖼️ Screenshots

<div align="center">
  <img src="placeholder-goals.png" width="250" alt="Goals Dashboard"> &nbsp;&nbsp;
  <img src="placeholder-progress.png" width="250" alt="Progress Tracking"> &nbsp;&nbsp;
  <img src="placeholder-analytics.png" width="250" alt="Analytics View"> &nbsp;&nbsp;
</div>

*Beautiful screenshots coming soon!*

---

## 🛠️ Tech Stack

### 🏗️ **Architecture & Design Patterns**
- **Clean Architecture** – Clear separation of concerns across layers
- **MVVM (Model–View–ViewModel)** – Reactive UI with state management
- **Unidirectional Data Flow (UDF)** – Predictable state updates and easier debugging
- **Single Source of Truth** – Centralized state management for consistency

### 🎨 **Framework & UI**
- **Flutter** – Cross-platform framework for native iOS & Android apps
- **Dart** – Fast, modern programming language optimized for UI
- **Material Design** – Clean, intuitive design language
- **Custom Animations** – Smooth, performant UI transitions
- **Responsive Layouts** – Adaptive UI for different screen sizes

### 💉 **State Management**
- **Provider / BLoC / Riverpod** – Reactive and efficient state management
- **Stream Controllers** – Real-time data flow management
- **ChangeNotifier** – Simple state updates for UI

### 💾 **Data Persistence**
- **SQLite / Hive** – Local database for fast and reliable data storage
- **Shared Preferences** – Lightweight key-value storage for settings
- **Offline-First Architecture** – Seamless experience without internet dependency

### 🌊 **Asynchronous Programming**
- **Future & async/await** – Asynchronous operations
- **Streams** – Reactive data flows
- **Isolates** – Background processing without blocking UI

### 📊 **Data Visualization**
- **fl_chart** – Beautiful, customizable charts and graphs
- **Custom Painters** – Unique visual elements

### 🧪 **Testing**
- **flutter_test** – Widget and unit testing framework
- **mockito** – Mocking library for tests
- **bloc_test** – Testing utilities for BLoC pattern
- **integration_test** – End-to-end testing

---

## 🗺️ Project Structure

HorizonTrack follows a **modular Clean Architecture** with clear layer separation:

```plaintext
📦 lib/
 ┣ 📂 core/
 ┃ ┣ 📂 constants/
 ┃ ┃ ┣ 📜 app_constants.dart
 ┃ ┃ ┗ 📜 strings.dart
 ┃ ┣ 📂 theme/
 ┃ ┃ ┣ 🎨 app_colors.dart
 ┃ ┃ ┣ 🌓 app_theme.dart
 ┃ ┃ ┗ ✍️ text_styles.dart
 ┃ ┣ 📂 utils/
 ┃ ┃ ┣ 📅 date_utils.dart
 ┃ ┃ ┣ 📊 progress_calculator.dart
 ┃ ┃ ┗ 🔧 extensions.dart
 ┃ ┣ 📂 error/
 ┃ ┃ ┣ 📜 failures.dart
 ┃ ┃ ┗ 📜 exceptions.dart
 ┃ ┗ 📂 widgets/
 ┃   ┣ 🔘 custom_button.dart
 ┃   ┣ 📝 custom_text_field.dart
 ┃   ┗ 📦 loading_indicator.dart
 ┣ 📂 features/
 ┃ ┣ 📂 goals/
 ┃ ┃ ┣ 📂 data/
 ┃ ┃ ┃ ┣ 📂 models/
 ┃ ┃ ┃ ┃ ┣ 🎯 goal_model.dart
 ┃ ┃ ┃ ┃ ┗ 📍 milestone_model.dart
 ┃ ┃ ┃ ┣ 📂 datasources/
 ┃ ┃ ┃ ┃ ┣ 💾 goal_local_datasource.dart
 ┃ ┃ ┃ ┃ ┗ 📂 database/
 ┃ ┃ ┃ ┃   ┗ 📜 database_helper.dart
 ┃ ┃ ┃ ┗ 📂 repositories/
 ┃ ┃ ┃   ┗ 📜 goal_repository_impl.dart
 ┃ ┃ ┣ 📂 domain/
 ┃ ┃ ┃ ┣ 📂 entities/
 ┃ ┃ ┃ ┃ ┣ 🎯 goal.dart
 ┃ ┃ ┃ ┃ ┗ 📍 milestone.dart
 ┃ ┃ ┃ ┣ 📂 repositories/
 ┃ ┃ ┃ ┃ ┗ 📜 goal_repository.dart
 ┃ ┃ ┃ ┗ 📂 usecases/
 ┃ ┃ ┃   ┣ 📜 create_goal.dart
 ┃ ┃ ┃   ┣ 📜 get_all_goals.dart
 ┃ ┃ ┃   ┣ 📜 update_goal.dart
 ┃ ┃ ┃   ┣ 📜 delete_goal.dart
 ┃ ┃ ┃   ┗ 📜 get_goal_by_id.dart
 ┃ ┃ ┗ 📂 presentation/
 ┃ ┃   ┣ 📂 screens/
 ┃ ┃   ┃ ┣ 🖼️ home_screen.dart
 ┃ ┃   ┃ ┣ 🖼️ create_goal_screen.dart
 ┃ ┃   ┃ ┗ 🖼️ goal_detail_screen.dart
 ┃ ┃   ┣ 📂 widgets/
 ┃ ┃   ┃ ┣ 💳 goal_card.dart
 ┃ ┃   ┃ ┣ 📍 milestone_list_item.dart
 ┃ ┃   ┃ ┗ 📊 progress_indicator.dart
 ┃ ┃   ┗ 📂 providers/
 ┃ ┃     ┣ 📦 goal_provider.dart
 ┃ ┃     ┗ 📋 goal_state.dart
 ┃ ┣ 📂 progress/
 ┃ ┃ ┣ 📂 data/
 ┃ ┃ ┃ ┣ 📂 models/
 ┃ ┃ ┃ ┃ ┗ 📊 progress_model.dart
 ┃ ┃ ┃ ┣ 📂 datasources/
 ┃ ┃ ┃ ┃ ┗ 💾 progress_local_datasource.dart
 ┃ ┃ ┃ ┗ 📂 repositories/
 ┃ ┃ ┃   ┗ 📜 progress_repository_impl.dart
 ┃ ┃ ┣ 📂 domain/
 ┃ ┃ ┃ ┣ 📂 entities/
 ┃ ┃ ┃ ┃ ┗ 📊 progress_entry.dart
 ┃ ┃ ┃ ┣ 📂 repositories/
 ┃ ┃ ┃ ┃ ┗ 📜 progress_repository.dart
 ┃ ┃ ┃ ┗ 📂 usecases/
 ┃ ┃ ┃   ┣ 📜 log_progress.dart
 ┃ ┃ ┃   ┣ 📜 get_progress_history.dart
 ┃ ┃ ┃   ┗ 📜 calculate_completion.dart
 ┃ ┃ ┗ 📂 presentation/
 ┃ ┃   ┣ 📂 screens/
 ┃ ┃   ┃ ┗ 🖼️ progress_screen.dart
 ┃ ┃   ┣ 📂 widgets/
 ┃ ┃   ┃ ┣ ✅ progress_log_item.dart
 ┃ ┃   ┃ ┗ 📊 progress_chart.dart
 ┃ ┃   ┗ 📂 providers/
 ┃ ┃     ┗ 📦 progress_provider.dart
 ┃ ┗ 📂 analytics/
 ┃   ┣ 📂 presentation/
 ┃   ┃ ┣ 📂 screens/
 ┃   ┃ ┃ ┗ 🖼️ analytics_screen.dart
 ┃   ┃ ┗ 📂 widgets/
 ┃   ┃   ┣ 📈 line_chart_widget.dart
 ┃   ┃   ┣ 🥧 pie_chart_widget.dart
 ┃   ┃   ┗ 📊 stats_card.dart
 ┃   ┗ 📂 providers/
 ┃     ┗ 📦 analytics_provider.dart
 ┣ 📂 config/
 ┃ ┣ 📜 routes.dart
 ┃ ┗ 📜 dependency_injection.dart
 ┣ 🏠 main.dart
 ┗ 🌍 app.dart
```

### 📦 Module Organization

- **`core/`** – Shared utilities, theme, constants, and common widgets
- **`features/`** – Feature modules following Clean Architecture
  - **`data/`** – Data sources, models, and repository implementations
  - **`domain/`** – Business logic, entities, use cases, and repository interfaces
  - **`presentation/`** – Screens, widgets, and state management
- **`config/`** – App configuration, routes, and dependency injection

---

## 🚀 Getting Started

### Prerequisites

- **Flutter SDK** (3.0 or higher)
- **Dart SDK** (3.0 or higher)
- **Android Studio** / **VS Code** with Flutter extension
- **Xcode** (for iOS development on macOS)
- **iOS Simulator** / **Android Emulator**

### Installation

```bash
# Clone the repository
git clone https://github.com/sergiosabater/HorizonTrack.git

# Navigate to project directory
cd HorizonTrack

# Install dependencies
flutter pub get

# Run the app
flutter run
```

### Platform-Specific Setup

#### 🤖 Android
```bash
# Check for any issues
flutter doctor

# Run on Android device/emulator
flutter run
```

#### 🍎 iOS
```bash
# Install CocoaPods dependencies
cd ios && pod install && cd ..

# Run on iOS simulator/device
flutter run
```

---

## 💡 Usage Guide

1. **🎯 Define Your First Goal**  
   Tap the **"+"** floating action button to create a new goal with a clear, inspiring description.

2. **📍 Break It Into Milestones**  
   Add specific, measurable milestones that mark your progress toward the goal.

3. **✅ Log Daily Progress**  
   Check off completed tasks and watch your progress indicators fill up in real-time.

4. **📊 Visualize Your Journey**  
   Navigate to the **Analytics** tab to see beautiful charts of your achievements over time.

5. **🔄 Stay Consistent**  
   Build momentum by logging progress daily. Small steps lead to big results!

6. **🎨 Customize Your Experience**  
   Switch between light and dark modes in settings to match your preference and time of day.

---

## 🎯 State Management Philosophy

HorizonTrack implements robust state management patterns:

### Immutable State
```dart
class GoalState {
  final List<Goal> goals;
  final bool isLoading;
  final String? errorMessage;
  
  const GoalState({
    this.goals = const [],
    this.isLoading = false,
    this.errorMessage,
  });
}
```

### Unidirectional Data Flow
```dart
// Events
abstract class GoalEvent {}
class CreateGoal extends GoalEvent {
  final Goal goal;
  CreateGoal(this.goal);
}

// State Updates
class GoalProvider extends ChangeNotifier {
  GoalState _state = GoalState();
  
  void createGoal(Goal goal) async {
    _state = _state.copyWith(isLoading: true);
    notifyListeners();
    // ... business logic
  }
}
```

---

## 🧪 Testing Strategy

HorizonTrack is designed with comprehensive testing:

### 🔬 Unit Tests
- **Domain Layer** – Use cases and business logic fully tested
- **Data Layer** – Repository implementations with mock data sources
- **Utilities** – Helper functions and extensions

```bash
# Run all unit tests
flutter test
```

### 🎨 Widget Tests
- **UI Components** – Individual widgets tested in isolation
- **Screens** – Complete screen flows with mock providers

```bash
# Run widget tests
flutter test test/widgets
```

### 🚀 Integration Tests
- **End-to-End Flows** – Complete user journeys
- **Performance** – Smooth animations and responsiveness

```bash
# Run integration tests
flutter test integration_test
```

### 📊 Test Coverage
- **Domain Layer**: 100% coverage
- **Data Layer**: 95%+ coverage
- **Presentation Layer**: 80%+ coverage

```bash
# Generate coverage report
flutter test --coverage
genhtml coverage/lcov.info -o coverage/html
```

---

## 📝 Roadmap

### 🎯 Version 1.1 (Q2 2025)

- ☁️ **Cloud Sync** – Backup and sync across devices
- 🔔 **Smart Notifications** – Gentle reminders for daily progress
- 🎨 **Custom Themes** – Personalize colors and styles

### 🚀 Version 1.2 (Q3 2025)

- 🏆 **Gamification** – Streaks, achievements, and rewards
- 👥 **Shared Goals** – Collaborate with friends or accountability partners
- 📊 **Advanced Analytics** – Deeper insights and predictive trends

### 🌟 Version 2.0 (Q4 2025)

- 🤖 **AI Insights** – Smart suggestions based on your patterns
- 📱 **Wear OS / watchOS** – Track progress from your wrist
- 🌍 **Web Version** – Access HorizonTrack from any browser
- 🎯 **Goal Templates** – Pre-built goal structures for common objectives

---

## 🤝 Contributing

Contributions make the open-source community an amazing place to learn and create!

### How to Contribute

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Contribution Guidelines

- Follow the existing code style and architecture patterns
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed
- Be respectful and constructive in discussions

---

## 📄 License

This project is licensed under the **MIT License**.

```
Copyright (c) 2025 Sergio Sabater

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```

See the [LICENSE](LICENSE) file for full details.

---

## 📧 Contact

**Sergio Sabater**  
Android & Flutter Developer • Clean Architecture Enthusiast

- 📧 Email: sergiosabater@gmail.com
- 💼 LinkedIn: [linkedin.com/in/sergiosabater](https://linkedin.com/in/sergiosabater)
- 🐦 Twitter: [@sergiosabater](https://twitter.com/sergiosabater)
- 🌐 Portfolio: [sergiosabater.dev](https://sergiosabater.dev)

---

<div align="center">

### 🌅 Focus on the horizon. Progress happens step by step. 🌅
*Built with ❤️, Flutter, and the belief that small consistent actions lead to extraordinary results.*

<img src="https://media.giphy.com/media/3oKIPnAiaMCws8nOsE/giphy.gif" width="200" alt="Progress Animation">

<br><br>

**⭐ Star this repo if HorizonTrack helps you reach your goals! ⭐**

</div>

---

<div align="center">
  Made with 💙 by <strong>Sergio Sabater</strong>
</div>

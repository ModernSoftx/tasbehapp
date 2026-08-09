# Tasbeh App - UI/UX Design Plan

## 🎯 App Overview
A beautiful, minimalist digital Tasbeh (prayer counter) app for Android that helps users keep track of their dhikr with haptic feedback, sound effects, and custom counters.

---

## 📱 Screen Architecture

### 1. **Home/Counter Screen** (Main Screen)
The primary interface where users interact with the counter.

#### Layout Components:
- **Top Section** (20% height)
  - Current Tasbeh title (center)
  - Settings icon (top-right)
  - Info/Help icon (top-left)
  
- **Middle Section** (60% height)
  - Large circular counter display (center)
    - Current count (large, bold number)
    - Target count below (smaller, muted)
    - Progress ring/circle animation around the number
  - Ripple effect on tap
  
- **Bottom Section** (20% height)
  - Reset button (left)
  - Change Tasbeh button (center)
  - Menu/List icon (right)

#### Interactions:
- **Tap anywhere on middle section** → Increment counter + haptic + sound
- **Long press counter** → Quick reset with confirmation dialog
- **Swipe left/right** → Navigate between saved Tasbehs
- **Progress ring fills** → Celebration animation when target reached

#### Animations:
- Counter number scale animation on increment
- Ripple effect from tap point
- Progress ring smooth fill
- Celebration confetti/glow when target reached
- Smooth transitions between Tasbehs

---

### 2. **Tasbeh List Screen**
Browse and manage all saved Tasbehs.

#### Layout Components:
- **Top Bar**
  - Screen title: "My Tasbehs"
  - Search icon
  - Add new Tasbeh FAB (Floating Action Button)
  
- **List Section**
  - Card-based layout for each Tasbeh
  - Each card shows:
    - Tasbeh name
    - Arabic text (if applicable)
    - Current count / Target count
    - Last used timestamp
    - Progress bar
  - Swipe actions:
    - Swipe right → Edit
    - Swipe left → Delete

#### Interactions:
- Tap card → Navigate to Counter screen with selected Tasbeh
- Long press → Multi-select mode for batch operations
- Search → Filter Tasbehs by name

#### Animations:
- Card entrance animation (staggered fade-in)
- Swipe reveal actions
- FAB rotation on scroll
- Delete confirmation with card slide-out

---

### 3. **Add/Edit Tasbeh Screen**
Create or modify custom Tasbehs.

#### Layout Components:
- **Input Fields**
  - Tasbeh name (required)
  - Arabic text (optional)
  - Target count (default: 33, 99, 100 quick select buttons)
  - Description/Notes (optional)
  - Category selection (optional: Dhikr, Du'a, Custom)
  
- **Action Buttons**
  - Save button (top-right)
  - Cancel button (top-left/back)
  
- **Quick Presets Section**
  - Common Tasbehs (SubhanAllah, Alhamdulillah, Allahu Akbar)
  - One-tap to populate fields

#### Validation:
- Name cannot be empty
- Target count must be positive number
- Show error states with helpful messages

#### Animations:
- Field focus animations
- Save button ripple effect
- Success checkmark animation on save

---

### 4. **Settings Screen**
Customize app behavior and appearance.

#### Settings Categories:

**Sound & Haptics**
- Enable/Disable sound effects
- Choose sound type (click, beep, custom)
- Sound volume slider
- Enable/Disable haptic feedback
- Haptic intensity (light, medium, strong)

**Display**
- Theme selection (Light, Dark, Auto)
- Color accent picker
- Font size for counter
- Keep screen on while counting

**Counter Behavior**
- Auto-reset on target reached
- Show celebration animation
- Vibration pattern on target reached

**Data & Backup**
- Export all Tasbehs (JSON)
- Import Tasbehs
- Reset all counters
- Clear app data

**About**
- App version
- Rate app
- Share app
- Privacy policy

#### Animations:
- Toggle switch animations
- Slider value change animations
- Color picker preview

---

## 🎨 Design System

### Color Palette
**Light Theme:**
- Primary: #2E7D32 (Islamic Green)
- Secondary: #FFB300 (Gold accent)
- Background: #FAFAFA
- Surface: #FFFFFF
- Text Primary: #212121
- Text Secondary: #757575

**Dark Theme:**
- Primary: #4CAF50
- Secondary: #FFC107
- Background: #121212
- Surface: #1E1E1E
- Text Primary: #FFFFFF
- Text Secondary: #B0B0B0

### Typography
- **Counter Number**: 72sp, Bold, Rubik/Poppins
- **Tasbeh Title**: 24sp, Medium
- **Arabic Text**: 20sp, Traditional Arabic font
- **Body Text**: 16sp, Regular
- **Target Count**: 14sp, Light

### Spacing
- Padding: 16dp standard, 24dp for major sections
- Card margin: 8dp
- Element spacing: 8dp, 16dp, 24dp (small, medium, large)

### Elevation
- Cards: 2dp
- FAB: 6dp
- Dialogs: 24dp

---

## 🔊 Sound & Haptic Feedback

### Sound Effects
- **Counter increment**: Soft click sound (100ms)
- **Target reached**: Success chime (500ms)
- **Reset**: Gentle whoosh (200ms)
- **Navigation**: Subtle tap (50ms)

### Haptic Patterns
- **Counter increment**: Light tap (10ms)
- **Target reached**: Success pattern (50ms pulse)
- **Long press**: Medium vibration (100ms)
- **Error**: Double tap (20ms, 20ms)

---

## 🌟 Key Features & Enhancements

### Must-Have Features:
1. ✅ Tap-to-count with immediate feedback
2. ✅ Custom Tasbeh creation
3. ✅ Sound and haptic feedback
4. ✅ Progress tracking
5. ✅ Persistent data (Room Database)
6. ✅ Multiple Tasbehs management
7. ✅ Reset functionality

### Suggested Enhancements:
1. **Statistics Dashboard**
   - Total counts today/week/month
   - Most used Tasbehs
   - Streak tracking
   
2. **Daily Goals**
   - Set daily Tasbeh goals
   - Progress notifications
   
3. **Widget Support**
   - Home screen widget for quick counting
   
4. **Themes**
   - Multiple color themes
   - Custom wallpaper support
   
5. **Accessibility**
   - TalkBack support
   - Large text mode
   - High contrast mode
   
6. **Sharing**
   - Share progress with friends
   - Export statistics

---

## 🎭 Animation Details

### Counter Screen Animations:
1. **On tap**: 
   - Scale animation (1.0 → 1.1 → 1.0, 150ms)
   - Ripple effect from tap point
   - Progress ring fill animation
   
2. **On target reached**:
   - Celebration animation (confetti or glow)
   - Counter pulses 3 times
   - Optional full-screen overlay
   
3. **On Tasbeh switch**:
   - Slide transition (300ms)
   - Fade out old, fade in new
   
4. **On reset**:
   - Counter spin backward animation
   - Progress ring empty animation

### List Screen Animations:
1. **Card entrance**: Staggered fade-in with slide up (100ms delay each)
2. **Card selection**: Scale up slightly (1.0 → 1.02)
3. **Swipe actions**: Reveal with color change
4. **Delete**: Slide out with fade

### Transition Animations:
- Screen transitions: Slide + fade (300ms)
- Dialog appearance: Scale from center (200ms)
- FAB: Rotate on scroll, hide on scroll down

---

## 📊 User Flow Diagram

```
App Launch
    ↓
[Home/Counter Screen]
    ↓
    ├─→ Tap to count → Update DB → Feedback
    ├─→ Long press → Reset Dialog → Confirm → Reset
    ├─→ Swipe → Change Tasbeh
    ├─→ Menu button → [Tasbeh List]
    │                      ↓
    │                      ├─→ Select → [Counter Screen]
    │                      ├─→ Swipe card → Edit/Delete
    │                      └─→ FAB → [Add Tasbeh Screen]
    │                                     ↓
    │                                     └─→ Save → [List]
    └─→ Settings icon → [Settings Screen]
```

---

## 🎯 Success Metrics

### User Experience Goals:
- Counter response time: < 50ms
- Smooth animations: 60fps
- Intuitive navigation: < 3 taps to any feature
- Error-free data persistence

### Design Goals:
- Clean, distraction-free interface
- Accessible to all age groups
- Culturally appropriate aesthetics
- Minimal battery consumption

---

## 📝 Notes for Development

1. **Performance**: Use RecyclerView with ViewBinding for list optimization
2. **Database**: Use Room with LiveData for reactive updates
3. **Sound**: Use SoundPool for low-latency sound effects
4. **Haptics**: Use VibrationEffect API for precise control
5. **Animations**: Use ObjectAnimator and ViewPropertyAnimator for smooth animations
6. **MVVM**: Separate UI logic from business logic for testability

---

## 🚀 Implementation Priority

**Phase 1 (MVP):**
- Counter screen with basic functionality
- Add custom Tasbeh
- Room database integration
- Sound and haptic feedback

**Phase 2:**
- Tasbeh list with full CRUD
- Swipe gestures
- All animations
- Settings screen

**Phase 3:**
- Statistics and analytics
- Themes
- Widget support
- Export/Import


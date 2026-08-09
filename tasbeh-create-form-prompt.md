# AI Design Prompt for Create New Tasbeh Screen

Create a mobile form interface for creating a new Tasbeh counter with iOS-inspired design:

## Status Bar & Navigation
- Status bar: 48px height, white background, "9:41" left, system icons right
- Navigation bar: white, border-bottom (#F5F5F5)
  - Left: green chevron + "Cancel" (#2E7D32, 17px)
  - Center: "New Tasbeh" title (17px, semibold, #1C1C1E)
  - Right: empty space for balance

## Form Layout (Scrollable)
White background, 20px horizontal padding, 24px top padding

### 1. Tasbeh Details Section
- Label: "TASBEH DETAILS" (13px, uppercase, #8E8E93, medium weight)
- Input 1: Light gray (#F2F2F7) rounded (12px), 14px vertical padding
  - Placeholder: "Tasbeh Name (e.g. Daily Dhikr)"
  - 17px text size, #8E8E93 placeholder
  - Focus state: white background, green ring
- Input 2: Same styling, RTL direction, Amiri font
  - Placeholder: "Arabic Text (Optional)"

### 2. Target Count Section
- Header row: "TARGET COUNT" label left, "Custom" (green) right
- Count input + pills:
  - Left: numeric input box (96px wide, centered text, 20px size, bold)
  - Right: horizontal scroll of count chips:
    - Active chip: green background (#2E7D32), white text, rounded-full
    - Inactive: light gray (#F2F2F7), gray text
    - Options: 33, 99, 100, ∞
    - Size: 32px vertical padding, 16px horizontal
- Helper text: "The counter will notify you when this number is reached." (12px, #9CA3AF)

### 3. Divider
Full-width, 1px, #F5F5F5

### 4. Quick Presets Section
- Label: "QUICK PRESETS" (13px, uppercase, #8E8E93)
- Preset cards (white, rounded 12px, subtle border #F5F5F5):
  - Top row: Tasbeh name left (16px, medium) + Arabic right (18px, gray, Amiri)
  - Bottom: English translation (12px, #757575)
  - Padding: 16px
  - Tap scale: 0.95
  - Hover: green border
  
**Presets:**
1. SubhanAllah - سُبْحَانَ ٱللَّٰهِ - Glory be to Allah
2. Alhamdulillah - ٱلْحَمْدُ لِلَّٰهِ - Praise be to Allah
3. Allahu Akbar - ٱللَّٰهُ أَكْبَرُ - Allah is the Greatest
4. Astaghfirullah - أَسْتَغْفِرُ ٱللَّٰهَ - I seek forgiveness from Allah

## Bottom Action Area
- Fixed position, white background, top border, shadow upward
- Padding: 20px horizontal, 32px bottom
- Button: Full-width, green (#2E7D32), rounded (12px)
  - Text: "Save Tasbeh" (17px, semibold, white)
  - Checkmark icon left
  - Vertical padding: 14px
  - Tap scale: 0.98
  - Shadow: soft green

## Home Indicator
- Bottom center, gray rounded bar (128px × 4px, #D1D5DB)
- 4px from bottom

## Design Specifications
- Typography: Inter for UI, Amiri for Arabic
- Form inputs: iOS-style (#F2F2F7 background, rounded, focus transitions)
- Spacing: 24px between sections, 12px between related items
- Colors:
  - Primary Green: #2E7D32
  - Background: #FAFAFA
  - Input Background: #F2F2F7
  - Text Primary: #1C1C1E
  - Text Secondary: #8E8E93
  - Borders: #F5F5F5

## Interactions
- All inputs smooth focus transitions (200ms)
- Count chips toggle active state
- Preset cards scale down on tap
- Save button scales on press
- Smooth scrolling with bottom padding

Generate an iOS-style form screen matching this specification.

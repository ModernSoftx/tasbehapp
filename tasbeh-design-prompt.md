# AI Design Prompt for Tasbeh Counter App

Create a mobile app interface for a digital Islamic Tasbeh (prayer bead) counter with the following exact specifications:

## Overall Layout
- Clean, minimal design with an off-white background (#FAFAFA)
- Three-section vertical layout: header (15%), main content (70%), footer (15%)
- Mobile-first design, optimized for portrait orientation
- Adequate padding and spacing throughout (24px default)

## Header Section (Top)
- Centered heading "SubhanAllah" in medium-weight Poppins font, size 24px, color #212121
- Below it, Arabic text "سبحان الله" in lighter weight, size 14px, color #757575
- Settings gear icon on the top-right corner (28px, outlined style, #757575)
- Left side empty for balance

## Main Counter Circle (Center)
- Large circular progress indicator, 288px diameter
- Progress ring with two layers:
  - Background track: light gray (#E0E0E0), 3px stroke width
  - Active progress: Islamic green (#2E7D32), 3px stroke width, rounded ends
  - Progress fills clockwise from top (currently showing 33% progress)
- Inside the circle:
  - Very large counter number "33" in Rubik font, 96px size, bold weight (#212121)
  - Below it: "TARGET: 100" in uppercase, 14px, medium weight, #757575 with slight opacity
- The entire circle acts as a tap target with subtle scale-down animation on press

## Footer Section (Bottom)
- Three elements in a horizontal row with space-between alignment:

### Left: Reset Button
- Circular button with restart/refresh icon (outlined, 28px)
- Color: #757575
- Active state: slight background (#F5F5F5) and darker icon color

### Center: Change Tasbeh Button
- Pill-shaped button with white background
- Subtle border (#E5E5E5) and shadow
- Text: "Change Tasbeh" in medium weight, 14px, #212121
- Dropdown chevron icon on the right (20px)
- Padding: 12px horizontal, 16px vertical
- Active state: pressed inward effect with darker shadow

### Right: List Button
- Circular button with bulleted list icon (outlined, 28px)
- Same styling as reset button

## Design Principles
- Use Google Material Symbols (outlined variant, weight 300)
- Fonts: Poppins for UI text, Rubik for numbers
- Consistent icon size: 28px for primary actions, 20px for secondary
- Active states: subtle scale, background color, or shadow changes
- No harsh shadows - keep it minimal and clean
- Islamic green (#2E7D32) as the primary accent color
- Proper spacing hierarchy: larger gaps between sections, smaller within components

## Interactive Elements
- All buttons should have gentle tap feedback
- The main circle should feel responsive but not bouncy
- Smooth progress ring animation (0.35s ease)
- Icons should be touch-friendly (minimum 40px touch target)

## Color Palette
- Primary Green: #2E7D32
- Background: #FAFAFA
- Primary Text: #212121
- Secondary Text: #757575
- Track/Border: #E0E0E0
- White: #FFFFFF

Generate a mobile app design that exactly matches this specification with pixel-perfect attention to spacing, typography, and visual hierarchy.

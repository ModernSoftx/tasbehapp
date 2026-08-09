# AI Design Prompt for Tasbeh List View Screen

Create a mobile app list view interface for managing multiple Islamic Tasbeh counters with these exact specifications:

## Status Bar
- Time "9:41" on left, system icons (cellular, wifi, battery) on right
- Height: 48px, background: #FAFAFA
- Icons: 16px, black color

## Header Section
- Title: "My Tasbehs" - bold, 24px, #212121
- Profile icon on right: circular, green (#2E7D32), 32px
- Search bar below:
  - Rounded corners (12px), white background
  - Search icon on left (gray)
  - Placeholder: "Search your prayers..." in gray (#9E9E9E)
  - Padding: 12px vertical, subtle shadow
- Header padding: 24px horizontal

## Tasbeh Cards (Scrollable List)
Each card has white background, rounded corners (16px), padding 20px, subtle shadow, and tap scale effect (0.98):

### Card Structure:
**Top Row:**
- Left: Tasbeh name (18px, semibold, #212121) + English translation below (12px, #757575)
- Right: Arabic text (24px, green #2E7D32, Amiri font)

**Progress Section (16px gap from top):**
- Current count: large bold number (32px, #212121)
- Target: "/ 100" (14px, #757575, aligned right)
- Progress bar:
  - Background: light gray (#F5F5F5), 8px height, fully rounded
  - Fill: varies by status (green #2E7D32, amber #FFB300)
  - Width matches completion percentage

**Bottom Row:**
- Left: timestamp with clock icon (10px, gray)
- Right: action button ("RESUME", "START", or "RESET" - 12px, semibold, green or gray)

### Card Variations:
1. **Active (in-progress):** Green progress, "RESUME" button, recent timestamp
2. **Nearly complete (90%+):** Amber (#FFB300) progress bar
3. **Completed:** Green checkmark icon next to count, 100% progress, "RESET" button, reduced opacity (75%)
4. **Not started:** Gray progress (0%), "START" button

### Example Cards:
- SubhanAllah: 33/100 (33% green) - "2 mins ago" - RESUME
- Alhamdulillah: 89/99 (90% amber) - "1 hour ago" - RESUME  
- Allahu Akbar: 33/33 (100% green, checkmark) - "Yesterday" - RESET
- Astaghfirullah: 0/100 (0% gray) - "3 days ago" - START
- Salawat: 15/1000 (1.5% green) - "5 days ago" - RESUME

## Floating Action Button
- Bottom right corner, 24px margin
- Size: 56px × 56px, green (#2E7D32), rounded (16px)
- Plus icon: white, 32px
- Shadow: soft green shadow
- Tap scale: 0.95

## Bottom Navigation
- Height: 80px, white background, top border (#F5F5F5)
- 4 tabs with icons (24px) and labels (10px):
  1. Home - gray outline house icon
  2. **My Tasbehs** - green filled list icon (ACTIVE STATE)
  3. Stats - gray bar chart icon
  4. Settings - gray settings icon
- Active tab: green icon + green text
- Inactive: gray (#9E9E9E)

## Layout & Spacing
- Card spacing: 16px vertical gaps
- Horizontal padding: 24px throughout
- List scrollable with bottom padding (96px for FAB clearance)
- Smooth scrolling, no scrollbar visible

## Typography
- Headers: Poppins/Rubik, bold
- Body: Poppins, regular/medium
- Arabic: Amiri, regular
- Numbers: Rubik, bold

## Colors
- Primary Green: #2E7D32
- Accent Amber: #FFB300  
- Background: #FAFAFA
- Cards: #FFFFFF
- Text Primary: #212121
- Text Secondary: #757575
- Progress Track: #F5F5F5
- Borders: #E5E5E5

## Interactions
- Card tap: scale down to 98%
- Smooth transitions (100ms)
- FAB tap: scale to 95%
- Active states on all buttons

Generate a pixel-perfect mobile list view matching this specification.

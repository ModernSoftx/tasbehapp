# Tasbeh App - Development Guide Part 2: XML Layouts & Activities

## 📐 XML Layouts

### Step 19: Create activity_counter.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?android:attr/colorBackground"
    tools:context=".ui.counter.CounterActivity">

    <!-- Top Section -->
    <ImageView
        android:id="@+id/ivInfo"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:layout_margin="16dp"
        android:padding="12dp"
        android:src="@drawable/ic_info"
        android:contentDescription="@string/info"
        android:background="?attr/selectableItemBackgroundBorderless"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:tint="?android:attr/textColorSecondary" />

    <TextView
        android:id="@+id/tvTasbehTitle"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="16dp"
        android:layout_marginTop="20dp"
        android:gravity="center"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="?android:attr/textColorPrimary"
        tools:text="SubhanAllah"
        app:layout_constraintEnd_toStartOf="@id/ivSettings"
        app:layout_constraintStart_toEndOf="@id/ivInfo"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tvArabicText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="16dp"
        android:layout_marginTop="8dp"
        android:gravity="center"
        android:textSize="20sp"
        android:textColor="?android:attr/textColorSecondary"
        android:fontFamily="sans-serif"
        tools:text="سُبْحَانَ ٱللَّٰهِ"
        app:layout_constraintEnd_toStartOf="@id/ivSettings"
        app:layout_constraintStart_toEndOf="@id/ivInfo"
        app:layout_constraintTop_toBottomOf="@id/tvTasbehTitle" />

    <ImageView
        android:id="@+id/ivSettings"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:layout_margin="16dp"
        android:padding="12dp"
        android:src="@drawable/ic_settings"
        android:contentDescription="@string/settings"
        android:background="?attr/selectableItemBackgroundBorderless"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:tint="?android:attr/textColorSecondary" />

    <!-- Counter Section -->
    <FrameLayout
        android:id="@+id/counterContainer"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_margin="32dp"
        app:layout_constraintBottom_toTopOf="@id/bottomSection"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvArabicText">

        <!-- Progress Circle Background -->
        <View
            android:id="@+id/progressBackground"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@drawable/circle_progress_background" />

        <!-- Progress Circle -->
        <com.yourname.tasbehapp.ui.custom.CircularProgressView
            android:id="@+id/progressCircle"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <!-- Counter Display -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="vertical"
            android:gravity="center">

            <TextView
                android:id="@+id/tvCurrentCount"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="72sp"
                android:textStyle="bold"
                android:textColor="?android:attr/textColorPrimary"
                android:fontFamily="sans-serif-medium"
                tools:text="0" />

            <View
                android:layout_width="60dp"
                android:layout_height="2dp"
                android:layout_marginVertical="8dp"
                android:background="?android:attr/textColorSecondary"
                android:alpha="0.3" />

            <TextView
                android:id="@+id/tvTargetCount"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="24sp"
                android:textColor="?android:attr/textColorSecondary"
                tools:text="33" />

        </LinearLayout>

        <!-- Ripple Effect Overlay -->
        <View
            android:id="@+id/rippleOverlay"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:clickable="true"
            android:focusable="true" />

    </FrameLayout>

    <!-- Bottom Section -->
    <LinearLayout
        android:id="@+id/bottomSection"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="24dp"
        android:gravity="center"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnReset"
            style="@style/Widget.Material3.Button.OutlinedButton"
            android:layout_width="0dp"
            android:layout_height="56dp"
            android:layout_weight="1"
            android:layout_marginEnd="8dp"
            android:text="@string/reset"
            android:textSize="16sp"
            app:icon="@drawable/ic_reset"
            app:iconGravity="textStart" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnChangeTasbeh"
            style="@style/Widget.Material3.Button.TonalButton"
            android:layout_width="0dp"
            android:layout_height="56dp"
            android:layout_weight="1"
            android:layout_marginHorizontal="8dp"
            android:text="@string/change"
            android:textSize="16sp"
            app:icon="@drawable/ic_change" />

        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/fabList"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:src="@drawable/ic_list"
            android:contentDescription="@string/tasbeh_list"
            app:tint="@android:color/white" />

    </LinearLayout>

    <!-- Celebration Overlay (initially invisible) -->
    <FrameLayout
        android:id="@+id/celebrationOverlay"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone"
        android:background="#80000000"
        android:clickable="true"
        android:focusable="true">

        <TextView
            android:id="@+id/tvCelebration"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="@string/target_reached"
            android:textSize="32sp"
            android:textStyle="bold"
            android:textColor="@android:color/white"
            android:padding="24dp" />

    </FrameLayout>

    <!-- Empty State -->
    <LinearLayout
        android:id="@+id/emptyStateLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:gravity="center"
        android:padding="32dp"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <ImageView
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:src="@drawable/ic_empty_tasbeh"
            android:alpha="0.3"
            app:tint="?android:attr/textColorSecondary" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="@string/no_tasbeh_selected"
            android:textSize="18sp"
            android:textColor="?android:attr/textColorSecondary" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnAddFirst"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:text="@string/add_tasbeh"
            app:icon="@drawable/ic_add" />

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Step 20: Create activity_tasbeh_list.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?android:attr/colorBackground">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appBarLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?android:attr/colorBackground"
        app:elevation="0dp">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:title="@string/my_tasbehs"
            app:titleTextColor="?android:attr/textColorPrimary"
            app:navigationIcon="@drawable/ic_back" />

        <com.google.android.material.search.SearchBar
            android:id="@+id/searchBar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="16dp"
            android:layout_marginBottom="8dp"
            android:hint="@string/search_tasbehs" />

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvTasbehs"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:paddingTop="8dp"
        android:paddingBottom="88dp"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        tools:listitem="@layout/item_tasbeh" />

    <!-- Empty State -->
    <LinearLayout
        android:id="@+id/emptyStateLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:orientation="vertical"
        android:gravity="center"
        android:padding="32dp"
        android:visibility="gone">

        <ImageView
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:src="@drawable/ic_empty_list"
            android:alpha="0.3"
            app:tint="?android:attr/textColorSecondary" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="@string/no_tasbehs_yet"
            android:textSize="18sp"
            android:gravity="center"
            android:textColor="?android:attr/textColorSecondary" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@string/tap_add_to_create"
            android:textSize="14sp"
            android:gravity="center"
            android:textColor="?android:attr/textColorSecondary" />

    </LinearLayout>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAdd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="24dp"
        android:src="@drawable/ic_add"
        android:contentDescription="@string/add_tasbeh"
        app:tint="@android:color/white" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

### Step 21: Create item_tasbeh.xml (RecyclerView Item)

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="16dp"
    android:layout_marginVertical="6dp"
    app:cardElevation="2dp"
    app:cardCornerRadius="12dp"
    app:strokeWidth="0dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp">

        <!-- Active Indicator -->
        <View
            android:id="@+id/activeIndicator"
            android:layout_width="4dp"
            android:layout_height="0dp"
            android:background="@color/primary"
            android:visibility="gone"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:visibility="visible" />

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:layout_marginStart="12dp"
            app:layout_constraintBottom_toTopOf="@id/progressBar"
            app:layout_constraintEnd_toStartOf="@id/countSection"
            app:layout_constraintStart_toEndOf="@id/activeIndicator"
            app:layout_constraintTop_toTopOf="parent">

            <TextView
                android:id="@+id/tvName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="?android:attr/textColorPrimary"
                android:maxLines="1"
                android:ellipsize="end"
                tools:text="SubhanAllah" />

            <TextView
                android:id="@+id/tvArabic"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="4dp"
                android:textSize="16sp"
                android:textColor="?android:attr/textColorSecondary"
                android:maxLines="1"
                android:ellipsize="end"
                tools:text="سُبْحَانَ ٱللَّٰهِ" />

            <TextView
                android:id="@+id/tvLastUsed"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:textSize="12sp"
                android:textColor="?android:attr/textColorSecondary"
                tools:text="Last used 2 hours ago" />

        </LinearLayout>

        <LinearLayout
            android:id="@+id/countSection"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="end"
            app:layout_constraintBottom_toTopOf="@id/progressBar"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <TextView
                android:id="@+id/tvCount"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="28sp"
                android:textStyle="bold"
                android:textColor="?android:attr/textColorPrimary"
                tools:text="25" />

            <TextView
                android:id="@+id/tvTarget"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="14sp"
                android:textColor="?android:attr/textColorSecondary"
                tools:text="/ 33" />

        </LinearLayout>

        <com.google.android.material.progressindicator.LinearProgressIndicator
            android:id="@+id/progressBar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="12dp"
            app:trackCornerRadius="4dp"
            app:trackThickness="6dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            tools:progress="75" />

    </androidx.constraintlayout.widget.ConstraintLayout>

</com.google.android.material.card.MaterialCardView>
```

### Step 22: Create activity_add_edit_tasbeh.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?android:attr/colorBackground">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?android:attr/colorBackground"
        app:elevation="0dp">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:title="@string/add_tasbeh"
            app:titleTextColor="?android:attr/textColorPrimary"
            app:navigationIcon="@drawable/ic_back"
            app:menu="@menu/menu_save" />

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fillViewport="true"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <!-- Quick Presets -->
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/quick_presets"
                android:textSize="14sp"
                android:textStyle="bold"
                android:textColor="?android:attr/textColorPrimary"
                android:layout_marginBottom="8dp" />

            <com.google.android.material.chip.ChipGroup
                android:id="@+id/chipGroupPresets"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="24dp"
                app:singleSelection="true"
                app:selectionRequired="false">

                <com.google.android.material.chip.Chip
                    android:id="@+id/chipSubhanAllah"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="SubhanAllah" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chipAlhamdulillah"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Alhamdulillah" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chipAllahuAkbar"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Allahu Akbar" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chipAstaghfirullah"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Astaghfirullah" />

            </com.google.android.material.chip.ChipGroup>

            <!-- Tasbeh Name -->
            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilName"
                style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                android:hint="@string/tasbeh_name"
                app:endIconMode="clear_text">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etName"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="textCapWords"
                    android:maxLines="1" />

            </com.google.android.material.textfield.TextInputLayout>

            <!-- Arabic Text -->
            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilArabicText"
                style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                android:hint="@string/arabic_text_optional"
                app:endIconMode="clear_text">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etArabicText"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="text"
                    android:maxLines="1" />

            </com.google.android.material.textfield.TextInputLayout>

            <!-- Target Count -->
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/target_count"
                android:textSize="14sp"
                android:textStyle="bold"
                android:textColor="?android:attr/textColorPrimary"
                android:layout_marginBottom="8dp" />

            <com.google.android.material.chip.ChipGroup
                android:id="@+id/chipGroupTargets"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="8dp"
                app:singleSelection="true">

                <com.google.android.material.chip.Chip
                    android:id="@+id/chip11"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="11" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chip33"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="33"
                    android:checked="true" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chip99"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="99" />

                <com.google.android.material.chip.Chip
                    android:id="@+id/chip100"
                    style="@style/Widget.Material3.Chip.Filter"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="100" />

            </com.google.android.material.chip.ChipGroup>

            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilTargetCount"
                style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                android:hint="@string/custom_target">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etTargetCount"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="number"
                    android:maxLines="1"
                    tools:text="33" />

            </com.google.android.material.textfield.TextInputLayout>

            <!-- Category -->
            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilCategory"
                style="@style/Widget.Material3.TextInputLayout.OutlinedBox.ExposedDropdownMenu"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                android:hint="@string/category">

                <AutoCompleteTextView
                    android:id="@+id/actvCategory"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="none"
                    tools:text="Dhikr" />

            </com.google.android.material.textfield.TextInputLayout>

            <!-- Description -->
            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilDescription"
                style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                android:hint="@string/description_optional"
                app:counterEnabled="true"
                app:counterMaxLength="200">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etDescription"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="textMultiLine"
                    android:lines="3"
                    android:gravity="top|start"
                    android:maxLength="200" />

            </com.google.android.material.textfield.TextInputLayout>

        </LinearLayout>

    </androidx.core.widget.NestedScrollView>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

---

## 🎬 Activity Implementations

### Step 23: Create CounterActivity.kt

```kotlin
package com.yourname.tasbehapp.ui.counter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.databinding.ActivityCounterBinding
import com.yourname.tasbehapp.ui.list.TasbehListActivity
import com.yourname.tasbehapp.ui.settings.SettingsActivity
import com.yourname.tasbehapp.utils.HapticManager
import com.yourname.tasbehapp.utils.PreferenceManager
import com.yourname.tasbehapp.utils.SoundManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CounterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCounterBinding
    private val viewModel: CounterViewModel by viewModels()
    
    @Inject
    lateinit var soundManager: SoundManager
    
    @Inject
    lateinit var hapticManager: HapticManager
    
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPreferences()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupPreferences() {
        // Keep screen on if enabled
        if (preferenceManager.keepScreenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        // Apply preferences
        soundManager.setEnabled(preferenceManager.isSoundEnabled)
        soundManager.setVolume(preferenceManager.soundVolume)
        hapticManager.setEnabled(preferenceManager.isHapticEnabled)
        hapticManager.setIntensity(
            HapticManager.HapticIntensity.valueOf(preferenceManager.hapticIntensity)
        )
    }

    private fun setupClickListeners() {
        // Counter tap
        binding.counterContainer.setOnClickListener {
            viewModel.incrementCount()
        }

        // Long press to reset
        binding.counterContainer.setOnLongClickListener {
            hapticManager.performLongPressFeedback()
            showResetDialog()
            true
        }

        // Reset button
        binding.btnReset.setOnClickListener {
            showResetDialog()
        }

        // Change Tasbeh button
        binding.btnChangeTasbeh.setOnClickListener {
            // TODO: Show bottom sheet to select another Tasbeh
        }

        // List FAB
        binding.fabList.setOnClickListener {
            startActivity(Intent(this, TasbehListActivity::class.java))
        }

        // Settings
        binding.ivSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Add first Tasbeh
        binding.btnAddFirst.setOnClickListener {
            startActivity(Intent(this, TasbehListActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                is CounterViewState.Loading -> {
                    // Show loading if needed
                }
                is CounterViewState.Success -> {
                    showCounterState(state)
                }
                is CounterViewState.NoTasbeh -> {
                    showEmptyState()
                }
                is CounterViewState.Error -> {
                    // Show error
                }
            }
        }

        viewModel.event.observe(this) { event ->
            when (event) {
                is CounterEvent.CountIncremented -> {
                    onCountIncremented()
                }
                is CounterEvent.TargetReached -> {
                    onTargetReached()
                }
                is CounterEvent.CountReset -> {
                    onCountReset()
                }
            }
        }
    }

    private fun showCounterState(state: CounterViewState.Success) {
        binding.counterContainer.visibility = View.VISIBLE
        binding.bottomSection.visibility = View.VISIBLE
        binding.emptyStateLayout.visibility = View.GONE

        binding.tvTasbehTitle.text = state.tasbeh.name
        binding.tvArabicText.text = state.tasbeh.arabicText ?: ""
        binding.tvArabicText.visibility = 
            if (state.tasbeh.arabicText.isNullOrEmpty()) View.GONE else View.VISIBLE
        
        binding.tvCurrentCount.text = state.tasbeh.currentCount.toString()
        binding.tvTargetCount.text = state.tasbeh.targetCount.toString()
        
        // Update progress circle
        binding.progressCircle.setProgress(state.progress)
    }

    private fun showEmptyState() {
        binding.counterContainer.visibility = View.GONE
        binding.bottomSection.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.VISIBLE
    }

    private fun onCountIncremented() {
        // Play sound
        soundManager.playClickSound()
        
        // Haptic feedback
        hapticManager.performClickFeedback()
        
        // Animate counter
        animateCounterIncrement()
        
        // Ripple effect
        animateRipple()
    }

    private fun onTargetReached() {
        // Play special sound
        soundManager.playTargetReachedSound()
        
        // Special haptic
        hapticManager.performTargetReachedFeedback()
        
        // Show celebration
        if (preferenceManager.showCelebration) {
            showCelebration()
        }
        
        // Auto reset if enabled
        if (preferenceManager.autoReset) {
            viewModel.resetCount()
        }
    }

    private fun onCountReset() {
        soundManager.playResetSound()
        animateCounterReset()
    }

    private fun animateCounterIncrement() {
        // Scale animation
        val scaleX = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleX", 1f, 1.15f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.tvCurrentCount, "scaleY", 1f, 1.15f, 1f)
        
        scaleX.duration = 150
        scaleY.duration = 150
        scaleX.interpolator = OvershootInterpolator()
        scaleY.interpolator = OvershootInterpolator()
        
        scaleX.start()
        scaleY.start()
    }

    private fun animateRipple() {
        // TODO: Implement custom ripple animation
    }

    private fun animateCounterReset() {
        // Rotation animation
        val rotation = ObjectAnimator.ofFloat(
            binding.tvCurrentCount,
            "rotation",
            0f, -360f
        )
        rotation.duration = 300
        rotation.interpolator = AccelerateDecelerateInterpolator()
        rotation.start()
    }

    private fun showCelebration() {
        binding.celebrationOverlay.visibility = View.VISIBLE
        binding.celebrationOverlay.alpha = 0f
        
        // Fade in
        binding.celebrationOverlay.animate()
            .alpha(1f)
            .setDuration(300)
            .start()
        
        // Scale animation for text
        binding.tvCelebration.scaleX = 0f
        binding.tvCelebration.scaleY = 0f
        binding.tvCelebration.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(400)
            .setInterpolator(OvershootInterpolator())
            .start()
        
        // Auto hide after 2 seconds
        binding.celebrationOverlay.postDelayed({
            hideCelebration()
        }, 2000)
        
        // Tap to dismiss
        binding.celebrationOverlay.setOnClickListener {
            hideCelebration()
        }
    }

    private fun hideCelebration() {
        binding.celebrationOverlay.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                binding.celebrationOverlay.visibility = View.GONE
            }
            .start()
    }

    private fun showResetDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.reset_count)
            .setMessage(R.string.reset_confirmation)
            .setPositiveButton(R.string.reset) { _, _ ->
                viewModel.resetCount()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}
```

---

## 📜 Continue to Part 3?

This covers:
- ✅ All major XML layouts
- ✅ Counter Activity implementation with animations
- ✅ Sound and haptic feedback integration
- ✅ MVVM pattern

Would you like me to continue with Part 3 covering:
- TasbehListActivity implementation
- RecyclerView Adapter with swipe actions
- AddEditTasbehActivity
- Custom CircularProgressView
- Settings implementation
- Additional animations and polish?

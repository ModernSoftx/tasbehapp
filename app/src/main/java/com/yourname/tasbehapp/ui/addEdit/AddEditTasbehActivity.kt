package com.yourname.tasbehapp.ui.addEdit

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.databinding.ActivityAddEditTasbehBinding
import com.yourname.tasbehapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddEditTasbehActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTasbehBinding
    private val viewModel: AddEditTasbehViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTasbehBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        setupWindowInsets()
        
        val tasbehId = intent.getLongExtra(EXTRA_TASBEH_ID, -1L)
        viewModel.loadTasbeh(tasbehId)

        setupToolbar(tasbehId != -1L)
        setupTargetChips()
        setupSaveButton()
        observeViewModel()
    }

    private fun setupEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.appBar.setPadding(0, bars.top, 0, 0)
            insets
        }
    }

    private fun setupToolbar(isEditMode: Boolean) {
        binding.tvTitle.text = if (isEditMode) getString(R.string.edit_tasbeh) else getString(R.string.new_tasbeh)
        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun setupTargetChips() {
        Constants.TARGET_COUNT_PRESETS.forEach { count ->
            val chip = Chip(this).apply {
                text = if (count == 99999) "∞" else count.toString()
                isCheckable = true
                setOnClickListener {
                    binding.etTarget.setText(if (count == 99999) "99999" else count.toString())
                }
            }
            binding.chipGroupTargets.addView(chip)
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            animatePress(it)
            if (validateInputs()) {
                val name = binding.etName.text.toString().trim()
                val arabic = binding.etArabic.text.toString().trim().ifEmpty { null }
                val targetStr = binding.etTarget.text.toString().trim()
                val target = if (targetStr == "∞" || targetStr == "99999") 99999 else (targetStr.toIntOrNull() ?: 33)
                val category = binding.spinnerCategory.selectedItem.toString()
                val description = binding.etDescription.text.toString().trim().ifEmpty { null }
                val isFavourite = binding.switchFavourite.isChecked

                viewModel.saveTasbeh(name, arabic, target, category, description, isFavourite)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (binding.etName.text.isNullOrBlank()) {
            binding.tilName.error = getString(R.string.error_name_required)
            isValid = false
        } else {
            binding.tilName.error = null
        }

        val targetStr = binding.etTarget.text.toString().trim()
        if (targetStr.isEmpty()) {
            binding.tilTarget.error = getString(R.string.error_target_required)
            isValid = false
        } else {
            val target = targetStr.toIntOrNull()
            if (target == null || target < 1) {
                binding.tilTarget.error = getString(R.string.error_invalid_target)
                isValid = false
            } else {
                binding.tilTarget.error = null
            }
        }

        return isValid
    }

    private fun animatePress(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f)
        scaleX.duration = 150
        scaleY.duration = 150
        scaleX.interpolator = OvershootInterpolator()
        scaleY.interpolator = OvershootInterpolator()
        scaleX.start()
        scaleY.start()
    }

    private fun observeViewModel() {
        viewModel.tasbeh.observe(this) { tasbeh ->
            tasbeh?.let {
                binding.etName.setText(it.name)
                binding.etArabic.setText(it.arabicText)
                binding.etTarget.setText(it.targetCount.toString())
                binding.etDescription.setText(it.description)
                binding.switchFavourite.isChecked = it.isFavourite

                val categories = resources.getStringArray(R.array.tasbeh_categories)
                val index = categories.indexOf(it.category)
                if (index >= 0) {
                    binding.spinnerCategory.setSelection(index)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is AddEditTasbehViewModel.AddEditEvent.Success -> {
                        Toast.makeText(this@AddEditTasbehActivity, getString(R.string.tasbeh_saved), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    is AddEditTasbehViewModel.AddEditEvent.Error -> {
                        Toast.makeText(this@AddEditTasbehActivity, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_TASBEH_ID = "extra_tasbeh_id"
    }
}

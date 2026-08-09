package com.yourname.tasbehapp.ui.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yourname.tasbehapp.R
import com.yourname.tasbehapp.databinding.ActivityTasbehListBinding
import com.yourname.tasbehapp.databinding.DialogEditTasbehBinding
import com.yourname.tasbehapp.ui.addEdit.AddEditTasbehActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.Toast
import com.yourname.tasbehapp.domain.model.Tasbeh
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasbehListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasbehListBinding
    private val viewModel: TasbehListViewModel by viewModels()
    private lateinit var adapter: TasbehAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasbehListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            binding.root.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        setupSearch()
        setupRecyclerView()
        setupAddButton()
        observeViewModel()
    }

    private fun setupEdgeToEdge() {
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        androidx.core.view.WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    private fun setupAddButton() {
        binding.fabAdd.setOnClickListener {
            val intent = android.content.Intent(this, AddEditTasbehActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupRecyclerView() {
        adapter = TasbehAdapter(
            onItemClick = { tasbeh ->
                viewModel.selectTasbeh(tasbeh.id)
                finish() // Return to CounterActivity
            },
            onItemLongClick = { tasbeh ->
                val intent = android.content.Intent(this, AddEditTasbehActivity::class.java)
                intent.putExtra(AddEditTasbehActivity.EXTRA_TASBEH_ID, tasbeh.id)
                startActivity(intent)
            },
            onFavouriteClick = { tasbeh ->
                viewModel.toggleFavourite(tasbeh)
            },
            onEditClick = { tasbeh ->
                showEditDialog(tasbeh)
            },
            onDeleteClick = { tasbeh ->
                showDeleteConfirmation(tasbeh)
            }
        )
        
        binding.rvTasbehList.apply {
            layoutManager = LinearLayoutManager(this@TasbehListActivity)
            adapter = this@TasbehListActivity.adapter
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val tasbeh = adapter.currentList[position]
                viewModel.deleteTasbeh(tasbeh)
                
                val message = getString(R.string.delete_confirmation, tasbeh.name)
                Snackbar.make(binding.rvTasbehList, message, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo)) {
                        viewModel.insertTasbeh(tasbeh)
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvTasbehList)
    }

    private fun showEditDialog(tasbeh: Tasbeh) {
        val dialogBinding = DialogEditTasbehBinding.inflate(layoutInflater)
        
        // Pre-fill
        dialogBinding.etTasbehName.setText(tasbeh.name)
        dialogBinding.etTargetCount.setText(tasbeh.targetCount.toString())

        MaterialAlertDialogBuilder(this)
            .setTitle("Edit Tasbeh")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                val name = dialogBinding.etTasbehName.text.toString().trim()
                val target = dialogBinding.etTargetCount.text.toString().toIntOrNull()
                
                if (name.isEmpty()) {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (target == null || target <= 0) {
                    Toast.makeText(this, "Enter a valid target count", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                
                viewModel.updateTasbeh(tasbeh.copy(name = name, targetCount = target))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmation(tasbeh: Tasbeh) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Tasbeh")
            .setMessage("Do you want to delete \"${tasbeh.name}\"?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTasbeh(tasbeh)
                
                val message = getString(R.string.delete_confirmation, tasbeh.name)
                Snackbar.make(binding.rvTasbehList, message, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo)) {
                        viewModel.insertTasbeh(tasbeh)
                    }.show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.allTasbehs.observe(this) { tasbehs ->
            adapter.submitList(tasbehs)
            binding.emptyState.visibility = if (tasbehs.isEmpty()) View.VISIBLE else View.GONE
            binding.rvTasbehList.visibility = if (tasbehs.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}

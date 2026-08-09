package com.yourname.tasbehapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yourname.tasbehapp.databinding.ItemTasbehBinding
import com.yourname.tasbehapp.domain.model.Tasbeh
import com.yourname.tasbehapp.R

class TasbehAdapter(
    private val onItemClick: (Tasbeh) -> Unit,
    private val onItemLongClick: (Tasbeh) -> Unit,
    private val onFavouriteClick: (Tasbeh) -> Unit,
    private val onEditClick: (Tasbeh) -> Unit,
    private val onDeleteClick: (Tasbeh) -> Unit
) : ListAdapter<Tasbeh, TasbehAdapter.TasbehViewHolder>(TasbehDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasbehViewHolder {
        val binding = ItemTasbehBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TasbehViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasbehViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TasbehViewHolder(
        private val binding: ItemTasbehBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tasbeh: Tasbeh) {
            binding.apply {
                tvName.text = tasbeh.name
                tvTranslation.text = tasbeh.description ?: ""
                tvTranslation.visibility = if (tasbeh.description.isNullOrBlank()) View.GONE else View.VISIBLE
                
                tvArabic.text = tasbeh.arabicText
                tvArabic.visibility = if (tasbeh.arabicText.isNullOrBlank()) View.GONE else View.VISIBLE
                
                tvCurrentCount.text = tasbeh.currentCount.toString()
                tvTargetCount.text = root.context.getString(R.string.target_slash_format, tasbeh.targetCount)
                
                val progressPercent = if (tasbeh.targetCount > 0) {
                    (tasbeh.currentCount.toFloat() / tasbeh.targetCount * 100).toInt()
                } else 0
                progressIndicator.progress = progressPercent
                
                // Card Variation Logic
                val isCompleted = tasbeh.currentCount >= tasbeh.targetCount && tasbeh.targetCount > 0
                val isNearlyComplete = progressPercent >= 90 && !isCompleted
                val isNotStarted = tasbeh.currentCount == 0

                // Progress Bar Color
                val indicatorColor = when {
                    isCompleted -> root.context.getColor(R.color.primary)
                    isNearlyComplete -> root.context.getColor(R.color.accent_amber)
                    isNotStarted -> root.context.getColor(R.color.track_color)
                    else -> root.context.getColor(R.color.primary)
                }
                progressIndicator.setIndicatorColor(indicatorColor)
                
                // Checkmark Visibility
                ivCheck.visibility = if (isCompleted) View.VISIBLE else View.GONE
                
                // Action Button Text
                btnAction.text = when {
                    isCompleted -> root.context.getString(R.string.action_reset)
                    isNotStarted -> root.context.getString(R.string.action_start)
                    else -> root.context.getString(R.string.action_resume)
                }
                btnAction.setTextColor(if (isCompleted || isNotStarted) root.context.getColor(R.color.text_secondary) else root.context.getColor(R.color.primary))

                // Opacity for Completed
                cardTasbeh.alpha = if (isCompleted) 0.75f else 1.0f
                
                // Active Line + Favourite Badge
                activeIndicatorLine.visibility = if (tasbeh.isActive) View.VISIBLE else View.GONE
                badgeFavourite.visibility = if (tasbeh.isFavourite) View.VISIBLE else View.GONE

                // Laps Display
                if (tasbeh.totalLaps > 0) {
                    tvLapsCount.visibility = View.VISIBLE
                    tvLapsCount.text = root.context.getString(R.string.laps_label_format, tasbeh.totalLaps)
                } else {
                    tvLapsCount.visibility = View.GONE
                }

                // More Menu
                ibMoreMenu.setOnClickListener { anchorView ->
                    showPopupMenu(anchorView, tasbeh)
                }
                
                // Relative Timestamp
                tvTimestamp.text = android.text.format.DateUtils.getRelativeTimeSpanString(
                    tasbeh.lastUsed, 
                    System.currentTimeMillis(), 
                    android.text.format.DateUtils.MINUTE_IN_MILLIS
                )
                
                root.setOnClickListener {
                    onItemClick(tasbeh)
                }
                
                root.setOnLongClickListener {
                    onItemLongClick(tasbeh)
                    true
                }
            }
        }

        private fun showPopupMenu(anchor: View, tasbeh: Tasbeh) {
            val popup = android.widget.PopupMenu(anchor.context, anchor)
            popup.menuInflater.inflate(R.menu.menu_tasbeh_item, popup.menu)

            // Dynamic titles & Icons
            popup.menu.findItem(R.id.action_favourite).apply {
                title = if (tasbeh.isFavourite) anchor.context.getString(R.string.menu_remove_favourite) else anchor.context.getString(R.string.menu_mark_favourite)
                setIcon(if (tasbeh.isFavourite) R.drawable.ic_star_filled else R.drawable.ic_star_outline)
            }

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onEditClick(tasbeh)
                        true
                    }
                    R.id.action_favourite -> {
                        onFavouriteClick(tasbeh)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(tasbeh)
                        true
                    }
                    else -> false
                }
            }

            // Force icons
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                popup.setForceShowIcon(true)
            } else {
                try {
                    val fieldPopup = android.widget.PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldPopup.isAccessible = true
                    val menuPopupHelper = fieldPopup.get(popup)
                    val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                    val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
                    setForceIcons.invoke(menuPopupHelper, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            popup.show()
        }
    }

    class TasbehDiffCallback : DiffUtil.ItemCallback<Tasbeh>() {
        override fun areItemsTheSame(oldItem: Tasbeh, newItem: Tasbeh): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tasbeh, newItem: Tasbeh): Boolean {
            return oldItem == newItem
        }
    }
}

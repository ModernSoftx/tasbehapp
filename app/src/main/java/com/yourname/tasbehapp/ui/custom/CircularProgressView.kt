package com.yourname.tasbehapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.yourname.tasbehapp.R

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float = 0f
    private val strokeWidthPx = 16f * context.resources.displayMetrics.density // Using 3dp for better visibility, roughly 3px on mdpi
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthPx
        strokeCap = Paint.Cap.ROUND
    }

    private val rect = RectF()

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val size = Math.min(width, height).toFloat()
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (size - strokeWidthPx) / 2f

        rect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        // Background track (E0E0E0)
        paint.color = ContextCompat.getColor(context, R.color.track_color)
        paint.alpha = 255
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Active progress (Islamic Green #2E7D32)
        paint.color = ContextCompat.getColor(context, R.color.primary)
        canvas.drawArc(rect, -90f, progress * 360f, false, paint)
    }
}

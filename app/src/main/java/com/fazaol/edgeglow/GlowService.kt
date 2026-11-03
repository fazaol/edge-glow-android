
package com.fazaol.edgeglow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.LinearGradient
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.graphics.PixelFormat

object GlowService {

    private val handler = Handler(Looper.getMainLooper())
    private var glowView: View? = null
    private var windowManager: WindowManager? = null

    private val removeGlow = Runnable {
        glowView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (_: Exception) {
            }
        }
        glowView = null
    }

    fun show(context: Context) {
        if (!android.provider.Settings.canDrawOverlays(context)) {
            return
        }

        handler.post {
            removeGlow.run()

            val manager = context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

            val view = GlowBorderView(context)

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )

            params.gravity = Gravity.TOP or Gravity.START

            try {
                manager.addView(view, params)
                windowManager = manager
                glowView = view

                handler.removeCallbacks(removeGlow)
                handler.postDelayed(removeGlow, 4500)
            } catch (_: Exception) {
                glowView = null
            }
        }
    }
}

private class GlowBorderView(context: Context) : View(context) {

    private val density = resources.displayMetrics.density

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 9f * density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val inset = 12f * density

        paint.shader = LinearGradient(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            intArrayOf(
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA,
                Color.CYAN
            ),
            null,
            Shader.TileMode.CLAMP
        )

        val rectangle = RectF(
            inset,
            inset,
            width - inset,
            height - inset
        )

        canvas.drawRoundRect(
            rectangle,
            38f * density,
            38f * density,
            paint
        )

        paint.shader = null
    }
}

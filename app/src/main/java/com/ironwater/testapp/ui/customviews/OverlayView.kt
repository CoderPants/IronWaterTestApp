package com.ironwater.testapp.ui.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout

class OverlayView(context: Context?, attrs: AttributeSet?) : FrameLayout(context!!, attrs) {
    init {
        setBackgroundColor(Color.BLACK)
        alpha = 0.8f

        setOnTouchListener { _, _ -> true }
    }
}
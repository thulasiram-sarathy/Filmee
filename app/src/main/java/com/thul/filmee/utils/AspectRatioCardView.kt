package com.thul.filmee.utils

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

class AspectRatioCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val ratio = 1.4f
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ratio > 0) {
            val ratioHeight = (measuredWidth * ratio).toInt()
            setMeasuredDimension(measuredWidth, ratioHeight)
            val lp = layoutParams
            lp.height = ratioHeight
            layoutParams = lp
        }
    }
}
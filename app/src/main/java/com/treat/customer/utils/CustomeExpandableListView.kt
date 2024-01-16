package com.treat.customer.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ExpandableListView


class CustomExpandableListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ExpandableListView(context, attrs) {
    var expanded = true

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (expanded) {
            // Calculate entire height by providing a very large height hint.
            // But do not use the highest 2 bits of this integer; those are
            // reserved for the MeasureSpec mode.
            val expandSpec = MeasureSpec.makeMeasureSpec(
                Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
            )
            super.onMeasure(widthMeasureSpec, expandSpec)
            val params = layoutParams
            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}
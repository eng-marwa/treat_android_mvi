package com.treat.customer.utils// NormalDistributionCurveView.kt
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.pow

class NormalDistributionCurveView : View {

    private val curvePath = Path()
    private val paint = Paint()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint.color = resources.getColor(android.R.color.darker_gray)
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawNormalDistributionCurve(canvas)
    }

    private fun drawNormalDistributionCurve(canvas: Canvas?) {
        val width = width.toFloat()
        val height = height.toFloat()

        val centerX = width / 2
        val centerY = height

        curvePath.reset()

        val amplitude = height / 3
        val wavelength = width / 4

        for (x in 0 until width.toInt() step 10) {
            val angle = (x / wavelength) * (2 * Math.PI)
            val y = centerY - amplitude * Math.exp(-0.5 * ((x - centerX) / (wavelength / 2)).pow(2))
                .toFloat()
            curvePath.lineTo(x.toFloat(), y)
        }

        curvePath.lineTo(width, height)
        curvePath.lineTo(0f, height)
        curvePath.close()

        canvas?.drawPath(curvePath, paint)
    }
}

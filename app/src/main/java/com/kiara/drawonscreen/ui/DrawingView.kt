package com.kiara.drawonscreen.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var drawPaint: Paint
    private lateinit var canvasPaint: Paint
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private var color: Int = Color.BLACK
    private var brushSize = 0.toFloat()
    private lateinit var drawPath: FingerPath
    private val pathList = ArrayList<FingerPath>()

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        setupDrawing()
    }

    private fun setupDrawing() {
        drawPaint = Paint()
        drawPaint.color = color
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        drawPaint.isAntiAlias = true

        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20.toFloat()

        drawPath = FingerPath(color, brushSize)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath.moveTo(touchX!!, touchY!!)
            }

            MotionEvent.ACTION_MOVE -> {
                drawPath.lineTo(touchX!!, touchY!!)
            }

            MotionEvent.ACTION_UP -> {
                canvas.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, drawPaint)
        drawPaint.strokeWidth = drawPath.brushThickness
        drawPaint.color = drawPath.color
        canvas.drawPath(drawPath, drawPaint)


//        for (path in pathList) {
//            drawPaint.strokeWidth = path.brushThickness
//            drawPaint.color = path.color
//            canvas.drawPath(path, drawPaint)
//        }
//
//        if (!drawPath.isEmpty) {
//            drawPaint.strokeWidth = drawPath.brushThickness
//            drawPaint.color = drawPath.color
//        }
    }

    fun changeBrushSize(newSize: Float) {
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        drawPaint.strokeWidth = brushSize
    }

    internal inner class FingerPath(var color: Int, var brushThickness: Float) : Path() {

    }

}
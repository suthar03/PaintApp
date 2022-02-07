package com.suthar.paintappassignment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.suthar.paintappassignment.Utils.Companion.DRAW_ARROW_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CIRCLE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CURVE_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_RECTANGLE
import com.suthar.paintappassignment.Utils.Companion.paint
import com.suthar.paintappassignment.Utils.Companion.typeOfLine
import kotlin.math.sqrt


class PaintView : View {


    private var path = Path()
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap


    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (typeOfLine) {
            DRAW_CURVE_LINE -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartCurveLine()
                    MotionEvent.ACTION_MOVE -> touchMoveCurveLine()
                    MotionEvent.ACTION_UP -> touchUpCurveLine()
                }
            }
            DRAW_ARROW_LINE -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartArrowLine()
                    MotionEvent.ACTION_MOVE -> touchMoveArrowLine()
                    MotionEvent.ACTION_UP -> touchUpArrowLine()
                }
            }
            DRAW_RECTANGLE -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartRectangle()
                    MotionEvent.ACTION_MOVE -> touchMoveRectangle()
                    MotionEvent.ACTION_UP -> touchUpRectangle()
                }
            }
            DRAW_CIRCLE -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchStartCircle()
                    MotionEvent.ACTION_MOVE -> touchMoveCircle()
                    MotionEvent.ACTION_UP -> touchUpCircle()
                }
            }
        }
        return true
    }

    private fun touchStartCurveLine() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMoveCurveLine() {
        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {

            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUpCurveLine() {
        path.reset()
    }

    private fun touchStartArrowLine() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMoveArrowLine() {
        // I have drawn the shape after touchUp only
    }

    private fun touchUpArrowLine() {

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            val mPath = Path()
            val deltaX: Float = motionTouchEventX - currentX
            val deltaY: Float = motionTouchEventY - currentY
            val arrowHeadLength = 15
            val sideZ = sqrt((deltaX * deltaX + deltaY * deltaY).toDouble()).toFloat()
            val fraction = if (arrowHeadLength < sideZ) arrowHeadLength / sideZ else 1.0f
            val x1: Float = currentX + ((1 - fraction) * deltaX + fraction * deltaY)
            val y1: Float = currentY + ((1 - fraction) * deltaY - fraction * deltaX)
            val x2: Float = motionTouchEventX
            val y2: Float = motionTouchEventY
            val x3: Float = currentX + ((1 - fraction) * deltaX - fraction * deltaY)
            val y3: Float = currentY + ((1 - fraction) * deltaY + fraction * deltaX)
            mPath.moveTo(x1, y1)
            mPath.lineTo(x2, y2)
            mPath.lineTo(x3, y3)
            extraCanvas.drawPath(mPath, paint)
            path.moveTo(currentX, currentY)
            path.lineTo(motionTouchEventX, motionTouchEventY)
            extraCanvas.drawPath(path, paint)

        }
        invalidate()
        path.reset()

    }

    private fun touchStartRectangle() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMoveRectangle() {
        // I have drawn the shape after touchUp only
    }

    private fun touchUpRectangle() {

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            extraCanvas.drawRect(currentX, currentY, motionTouchEventX, motionTouchEventY, paint)
        }
        invalidate()
        path.reset()
    }

    private fun touchStartCircle() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMoveCircle() {
        // I have drawn the shape after touchUp only'
    }

    private fun touchUpCircle() {

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            val radius = sqrt(dx * dx + dy * dy)
            //extraCanvas.drawCircle(currentX, currentY, radius, paint)
            extraCanvas.drawOval(currentX, currentY, motionTouchEventX, motionTouchEventY, paint)
        }
        invalidate()
        path.reset()

    }

    constructor(context: Context) : this(context, null) {
        //ToDO
        //init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        //ToDO
        //init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //ToDO we are going to implement this
        //init()
    }

}
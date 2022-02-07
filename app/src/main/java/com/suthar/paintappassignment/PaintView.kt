package com.suthar.paintappassignment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.suthar.paintappassignment.Utils.Companion.DRAW_ARROW_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CIRCLE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CURVE_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_RECTANGLE
import com.suthar.paintappassignment.Utils.Companion.typeOfLine
import kotlin.math.sqrt

private const val STROKE_WIDTH = 12f
typealias Degree = Float
typealias Radian = Float

class PaintView : View{


    private val TAG: String = "PaintView"
    private var params : ViewGroup.LayoutParams?= null

    private var path = Path()
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

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
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        // Draw the bitmap that has the saved path.
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
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to save it.
            extraCanvas.drawPath(path, paint)
        }
        // Invalidate() is inside the touchMove() under ACTION_MOVE because there are many other
        // types of motion events passed into this listener, and we don't want to invalidate the
        // view for those.
        invalidate()
    }
    private fun touchUpCurveLine() {
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }

    private fun touchStartArrowLine(){
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
    private fun touchMoveArrowLine(){

        // Invalidate() is inside the touchMove() under ACTION_MOVE because there are many other
        // types of motion events passed into this listener, and we don't want to invalidate the
        // view for those.
        //invalidate()
    }
    private fun touchUpArrowLine(){

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)



        if (dx >= touchTolerance || dy >= touchTolerance) {

            val mPath = Path()
            val deltaX: Float = motionTouchEventX - currentX
            val deltaY: Float = motionTouchEventY - currentY
            val ARROWHEAD_LENGTH = 15
            val sideZ =
                Math.sqrt((deltaX * deltaX + deltaY * deltaY).toDouble()).toFloat()
            val frac = if (ARROWHEAD_LENGTH < sideZ) ARROWHEAD_LENGTH / sideZ else 1.0f
            val point_x_1: Float = currentX + ((1 - frac) * deltaX + frac * deltaY)
            val point_y_1: Float = currentY + ((1 - frac) * deltaY - frac * deltaX)
            val point_x_2: Float = motionTouchEventX
            val point_y_2: Float = motionTouchEventY
            val point_x_3: Float = currentX + ((1 - frac) * deltaX - frac * deltaY)
            val point_y_3: Float = currentY + ((1 - frac) * deltaY + frac * deltaX)
            mPath.moveTo(point_x_1, point_y_1)
            mPath.lineTo(point_x_2, point_y_2)
            mPath.lineTo(point_x_3, point_y_3);
            extraCanvas.drawPath(mPath, paint);
            path.moveTo(currentX, currentY)
            path.lineTo(motionTouchEventX, motionTouchEventY)
            extraCanvas.drawPath(path,paint)
            
        }
        invalidate()
        path.reset()

    }

    private fun touchStartRectangle(){
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
    private fun touchMoveRectangle(){

        // Invalidate() is inside the touchMove() under ACTION_MOVE because there are many other
        // types of motion events passed into this listener, and we don't want to invalidate the
        // view for those.
        //invalidate()
    }
    private fun touchUpRectangle(){

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            extraCanvas.drawRect(currentX, currentY, motionTouchEventX, motionTouchEventY, paint)
        }
        invalidate()
        path.reset()

    }

    private fun touchStartCircle(){
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
    private fun touchMoveCircle(){
    }
    private fun touchUpCircle(){

        val dx = kotlin.math.abs(motionTouchEventX - currentX)
        val dy = kotlin.math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            //extraCanvas.drawRect(currentX, currentY, motionTouchEventX, motionTouchEventY, paint)
            val radius = sqrt(dx*dx + dy*dy);
            extraCanvas.drawCircle(currentX, currentY, radius, paint)
        }
        invalidate()
        path.reset()

    }

    constructor(context: Context) : this(context, null){
        //ToDO
        //init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        //ToDO
        //init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //ToDO we are going to implement this
        //init()
    }

    private fun Degree.toRadian(): Float = (this / 180 * kotlin.math.PI).toFloat()
    private fun Radian.toDegree(): Float = (this * 180/ kotlin.math.PI).toFloat()

    /*private fun init(){
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }*/

    /*override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                pathListQ.add(path);
                colorListQ.add(currentBrush);
            }
            else -> return false

        }

        postInvalidate()

        return false
    }

    override fun onDraw(canvas: Canvas) {
        var pathIterator = pathListQ.iterator()
        var colorIterator = colorListQ.iterator()
        while (pathIterator.hasNext()) {
            paintBrush.color = colorIterator.next();
            canvas.drawPath(pathIterator.next(), paintBrush)
            invalidate()
            colorIterator.remove();
            pathIterator.remove();
        }
//        for(i in pathListQ.indices){
//            paintBrush.color = colorListQ[i]
//            canvas.drawPath(pathListQ[i], paintBrush)
//            invalidate()
//        }
    }*/
}
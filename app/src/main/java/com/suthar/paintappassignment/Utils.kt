package com.suthar.paintappassignment

import android.graphics.Color
import android.graphics.Paint
import android.util.Log

class Utils {
    companion object {
        private const val TAG = "Utils"
        private const val STROKE_WIDTH = 12f
        const val DRAW_CURVE_LINE: Int = 0
        const val DRAW_ARROW_LINE: Int = 1
        const val DRAW_RECTANGLE: Int = 2
        const val DRAW_CIRCLE: Int = 3
        var typeOfLine: Int = 0
        var drawingColor = Color.BLACK
        var paint: Paint = Paint().apply {
            color = drawingColor
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = STROKE_WIDTH
        }

        fun colorChanged() {
            //Function will update the color in paint whenever color is changed via color selection menu
            paint.color = drawingColor
            Log.d(TAG, "colorChanged: " + paint.color)
        }
    }


}
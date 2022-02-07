package com.suthar.paintappassignment

import android.graphics.Color

class Utils {
    companion object{
        const val DRAW_CURVE_LINE : Int = 0
        const val DRAW_ARROW_LINE : Int = 1
        const val DRAW_RECTANGLE : Int = 2
        const val DRAW_CIRCLE : Int = 3
        var typeOfLine:Int = 0
        var currentColor = Color.BLACK
    }
}
package com.suthar.paintappassignment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.suthar.paintappassignment.Utils.Companion.DRAW_ARROW_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CIRCLE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CURVE_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_RECTANGLE
import com.suthar.paintappassignment.Utils.Companion.colorChanged
import com.suthar.paintappassignment.Utils.Companion.drawingColor
import com.suthar.paintappassignment.Utils.Companion.typeOfLine

private const val TAG = "Utils.Main"

class MainActivity : AppCompatActivity() {


    private lateinit var ivLine: ImageView
    private lateinit var ivArrowLine: ImageView
    private lateinit var ivRectangle: ImageView
    private lateinit var ivCircle: ImageView
    private lateinit var ivColorSelection: ImageView
    private lateinit var layoutColorSelectionBar: LinearLayout
    private lateinit var ivColorBlack: ImageView
    private lateinit var ivColorBlue: ImageView
    private lateinit var ivColorRed: ImageView
    private lateinit var ivColorGreen: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivLine = findViewById(R.id.iv_line)
        ivArrowLine = findViewById(R.id.iv_arrow)
        ivRectangle = findViewById(R.id.iv_rectangle)
        ivCircle = findViewById(R.id.iv_circle)
        ivColorSelection = findViewById(R.id.iv_colors)
        layoutColorSelectionBar = findViewById(R.id.colorSelctionBar)
        ivColorBlack = findViewById(R.id.colorBlack)
        ivColorBlue = findViewById(R.id.colorBlue)
        ivColorRed = findViewById(R.id.colorRed)
        ivColorGreen = findViewById(R.id.colorGreen)

        init()
        //Setting up the all ImageView listener for the different types of shapes
        ivLine.setOnClickListener {
            btnReset()
            ivLine.setBackgroundResource(R.drawable.activebtn_background)
            typeOfLine = DRAW_CURVE_LINE
        }

        ivArrowLine.setOnClickListener {
            btnReset()
            ivArrowLine.setBackgroundResource(R.drawable.activebtn_background)
            typeOfLine = DRAW_ARROW_LINE
        }

        ivRectangle.setOnClickListener {
            btnReset()
            ivRectangle.setBackgroundResource(R.drawable.activebtn_background)
            typeOfLine = DRAW_RECTANGLE
        }

        ivCircle.setOnClickListener {
            btnReset()
            ivCircle.setBackgroundResource(R.drawable.activebtn_background)
            typeOfLine = DRAW_CIRCLE
        }

        ivColorSelection.setOnClickListener {
            /*btnReset()
            ivColorSelection.setBackgroundResource(R.drawable.activebtn_background)*/
            if (layoutColorSelectionBar.visibility == View.VISIBLE) {
                layoutColorSelectionBar.visibility = View.GONE
            } else
                layoutColorSelectionBar.visibility = View.VISIBLE

            // TODO We have to create a pop-up for color selection
        }

        ivColorBlack.setOnClickListener {
            drawingColor = Color.BLACK
            colorChanged()
            layoutColorSelectionBar.visibility = View.GONE
        }

        ivColorBlue.setOnClickListener {
            drawingColor = Color.BLUE
            colorChanged()
            layoutColorSelectionBar.visibility = View.GONE
        }
        ivColorRed.setOnClickListener {
            drawingColor = Color.RED
            colorChanged()
            layoutColorSelectionBar.visibility = View.GONE
        }
        ivColorGreen.setOnClickListener {
            drawingColor = Color.GREEN
            colorChanged()
            layoutColorSelectionBar.visibility = View.GONE
        }

    }

    private fun init() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ivLine.setBackgroundResource(R.drawable.activebtn_background)
        typeOfLine = DRAW_CURVE_LINE
        drawingColor = Color.BLACK
        colorChanged()
    }

    private fun btnReset() {
        ivLine.setBackgroundResource(R.drawable.inactivebtn_background)
        ivArrowLine.setBackgroundResource(R.drawable.inactivebtn_background)
        ivRectangle.setBackgroundResource(R.drawable.inactivebtn_background)
        ivCircle.setBackgroundResource(R.drawable.inactivebtn_background)
        ivColorSelection.setBackgroundResource(R.drawable.inactivebtn_background)
    }
}
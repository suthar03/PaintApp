package com.suthar.paintappassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.suthar.paintappassignment.Utils.Companion.DRAW_ARROW_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CIRCLE
import com.suthar.paintappassignment.Utils.Companion.DRAW_CURVE_LINE
import com.suthar.paintappassignment.Utils.Companion.DRAW_RECTANGLE
import com.suthar.paintappassignment.Utils.Companion.typeOfLine

class MainActivity : AppCompatActivity() {

    private lateinit var ivLine : ImageView
    private lateinit var ivArrowLine : ImageView
    private lateinit var ivRectangle : ImageView
    private lateinit var ivCircle : ImageView
    private lateinit var ivColorSelection : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        ivLine = findViewById(R.id.iv_line)
        ivArrowLine = findViewById(R.id.iv_arrow)
        ivRectangle = findViewById(R.id.iv_rectangle)
        ivCircle = findViewById(R.id.iv_circle)
        ivColorSelection = findViewById(R.id.iv_colors)

        //Default we are taking curvy line
        ivLine.setBackgroundResource(R.drawable.activebtn_background)
        typeOfLine = DRAW_CURVE_LINE

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
            // TODO We have to create a pop-up for color selection
        }

    }

    private fun btnReset(){
        ivLine.setBackgroundResource(R.drawable.inactivebtn_background)
        ivArrowLine.setBackgroundResource(R.drawable.inactivebtn_background)
        ivRectangle.setBackgroundResource(R.drawable.inactivebtn_background)
        ivCircle.setBackgroundResource(R.drawable.inactivebtn_background)
        ivColorSelection.setBackgroundResource(R.drawable.inactivebtn_background)
    }
}
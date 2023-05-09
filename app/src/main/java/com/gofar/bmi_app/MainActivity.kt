package com.gofar.bmi_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    
    private lateinit var heightField: EditText
    private lateinit var weightField: EditText
    private lateinit var calcBtn: Button
    private lateinit var resultField: TextView
    private lateinit var infoField: TextView
    private lateinit var bmiField: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        heightField = findViewById(R.id.etHeight)
        weightField = findViewById(R.id.etWeight)
        calcBtn = findViewById(R.id.btnProcess)
        
        calcBtn.setOnClickListener {
            val height = heightField.text.toString()
            val weight = weightField.text.toString()
            Log.i("BMI", "Height value is $height")
            Log.i("BMI", "Weight value is $weight")
            if (validateInput(weight, height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                displayBmi(bmi)
            }
        }
    }

    private fun validateInput(weight: String?, height: String?): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                toast("weight must not be null or empty")
                false
            }
            height.isNullOrEmpty() -> {
                toast("height must not be null or empty")
                false
            }
            else -> {
                true
            }
        }
    }

    private fun displayBmi(bmiValue: Float) {
        val formatted = String.format("%.2f", bmiValue)
        bmiField = findViewById(R.id.tvIndex)
        resultField = findViewById(R.id.tvResult)
        infoField = findViewById(R.id.tvInfo)

        bmiField.text = formatted
        infoField.setText(R.string.normal_range)

        var color: Int = 0
        var message: String = ""

        when {
            bmiValue <= 18.49 -> {
                color = R.color.underweight
                message =  "Underweight"
            }
            bmiValue in 18.50..24.99 -> {
                color = R.color.normal
                message = "Normal"
            }
            bmiValue in 25.00..29.99 -> {
                color = R.color.overweight
                message = "Overweight"
            }
            else -> {
                color = R.color.obese
                message = "Obese"
            }
        }

        resultField.setTextColor(ContextCompat.getColor(this, color))
        resultField.text = message
    }

    private fun toast(message: CharSequence, isLengthLong: Boolean = true) {

       Toast.makeText(
            this,
            message,
            if (isLengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()

        Log.i("BMI", "Toast made successfully")
    }

}
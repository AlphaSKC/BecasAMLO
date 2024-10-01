package com.example.becasamlo

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var calcularBtn: Button
    private lateinit var edadEditText: EditText
    private lateinit var calificacionEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edadEditText = findViewById(R.id.et_age)
        calificacionEditText = findViewById(R.id.et_grade)
        calcularBtn = findViewById(R.id.btn_calculate)

        edadEditText.filters = arrayOf(InputFilterMinMax(0.0, 100.0))
        calificacionEditText.filters = arrayOf(InputFilterMinMax(0.0, 10.0))

        edadEditText.addTextChangedListener(calcularTextWatcher)
        calificacionEditText.addTextChangedListener(calcularTextWatcher)
    }

    class InputFilterMinMax(private val min: Double, private val max: Double) : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = (dest.toString() + source.toString()).toDouble()
                if (isInRange(min, max, input)) return null
            } catch (nfe: NumberFormatException) {
            }
            return ""
        }

        private fun isInRange(a: Double, b: Double, c: Double): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }

    private val calcularTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val edadInput: String = edadEditText.text.toString().trim()
            val calificacionInput: String = calificacionEditText.text.toString().trim()

            if (edadInput.isNotEmpty() && calificacionInput.isNotEmpty()) {
                calcularBtn.isEnabled = true
                calcularBtn.setBackgroundColor(resources.getColor(R.color.purple))
                calcularBtn.setTextColor(resources.getColor(R.color.white))
                calcularBtn.setOnClickListener {
                    calculateScholarship()
                }
            } else {
                calcularBtn.isEnabled = false
            }

        }

        override fun afterTextChanged(s: Editable?) {}

    }

    private fun calculateScholarship(){
        val ageEditText = findViewById<EditText>(R.id.et_age)
        val gradeEditText = findViewById<EditText>(R.id.et_grade)
        val resultTextView = findViewById<TextView>(R.id.tv_result)

        resultTextView.visibility = TextView.VISIBLE

        val age = ageEditText.text.toString().toInt()
        val grade = gradeEditText.text.toString().toDouble()

        if (age > 18){
            if (grade >= 9 ){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 2,000"
            } else if (grade >= 7.5){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 1,000"
            } else if (grade >= 6){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 500"
            } else {
                resultTextView.text = "Lo sentimos, no eres acreedor a la beca. Estudia con dedicación el siguiente ciclo y acércate a tu meta: ¡conseguir una beca que impulse tu futuro!"
            }
        } else {
            if (grade >= 9 ){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 3,000"
            } else if (grade >= 8){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 2,000"
            } else if (grade >= 6){
                resultTextView.text = "Felicidades, eres acreedor a la beca de: $ 100"
            } else {
                resultTextView.text = "Lo sentimos, no eres acreedor a la beca. Estudia con dedicación el siguiente ciclo y acércate a tu meta: ¡conseguir una beca que impulse tu futuro!"
            }
        }
    }
}
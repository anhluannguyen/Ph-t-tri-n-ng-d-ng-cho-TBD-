
package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = "0"
    private var operator: String? = null
    private var previousInput: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonEquals, R.id.buttonC, R.id.buttonCE, R.id.buttonBS, R.id.buttonDot,
            R.id.buttonPlusMinus
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.buttonCE -> clearEntry()
            R.id.buttonC -> clearAll()
            R.id.buttonBS -> backspace()
            R.id.buttonPlus -> appendOperator("+")
            R.id.buttonMinus -> appendOperator("-")
            R.id.buttonMultiply -> appendOperator("*")
            R.id.buttonDivide -> appendOperator("/")
            R.id.buttonEquals -> calculate()
            R.id.buttonDot -> appendDot()
            R.id.buttonPlusMinus -> toggleSign()
            else -> appendNumber(button.text.toString().toInt())
        }
    }

    private fun updateDisplay() {
        display.text = currentInput
    }

    private fun clearEntry() {
        currentInput = "0"
        updateDisplay()
    }

    private fun clearAll() {
        currentInput = "0"
        operator = null
        previousInput = null
        updateDisplay()
    }

    private fun backspace() {
        currentInput = if (currentInput.length > 1) {
            currentInput.dropLast(1)
        } else {
            "0"
        }
        updateDisplay()
    }

    private fun appendNumber(number: Int) {
        currentInput = if (currentInput == "0") {
            number.toString()
        } else {
            currentInput + number.toString()
        }
        updateDisplay()
    }

    private fun appendOperator(op: String) {
        if (operator != null) {
            calculate()
        }
        previousInput = currentInput
        operator = op
        currentInput = "0"
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += "."
        }
        updateDisplay()
    }

    private fun toggleSign() {
        if (currentInput != "0") {
            currentInput = (currentInput.toDouble() * -1).toString()
        }
        updateDisplay()
    }

    private fun calculate() {
        if (operator == null || previousInput == null) {
            return
        }
        val result = when (operator) {
            "+" -> previousInput!!.toInt() + currentInput.toInt()
            "-" -> previousInput!!.toInt() - currentInput.toInt()
            "*" -> previousInput!!.toInt() * currentInput.toInt()
            "/" -> previousInput!!.toInt() / currentInput.toInt()
            else -> return
        }
        currentInput = result.toString()
        operator = null
        previousInput = null
        updateDisplay()
    }
}

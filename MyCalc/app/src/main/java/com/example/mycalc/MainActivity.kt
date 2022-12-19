package com.example.mycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mycalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private var canAddOperation = false
    private var canAddDecimal = true
    private val calc = CalcOperations()
    private var inputList = ArrayList<String>()

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun numberAction(view: View)
    {
        if(view is Button)
        {
            if(view.text == ".")
            {
                if(canAddDecimal) {
                    binding.showScreen.append(view.text)
                }
                canAddDecimal = false
            }
            else {
                binding.showScreen.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View)
    {
        if(view is Button && canAddOperation)
        {
            binding.showScreen.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
        if(view is Button && !canAddOperation)
        {
            val input = binding.showScreen.text.toString().toCharArray()
            binding.showScreen.setText(input, 0, input.size -1)
            binding.showScreen.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }


    fun clearAction(view: View)
    {
        binding.showScreen.text = ""
    }

    fun allClearAction(view: View)
    {
        binding.showScreen.text = ""
    }

    fun backSpaceAction(view: View)
    {
        val length = binding.showScreen.length()
        if(length > 0)
            binding.showScreen.text = binding.showScreen.text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View)
    {
        binding.showScreen.text = calculateResults()
    }

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.showScreen.text)
        {
            if(character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

}


class CalcOperations(){

    private  lateinit var binding: ActivityMainBinding
    private var canAddOperator = true
    private var canAddDecimal = true
    private var input = mutableListOf<Any>()


    fun addOperations(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.showScreen.text)
        {
            if(character == '.'){
                if(canAddDecimal)
                    currentDigit += character
                canAddDecimal = false
            }
            else if(character.isDigit())
                currentDigit += character
            else
            {
                if(!canAddDecimal)
                    list.add(currentDigit.toFloat())
                else
                    list.add(currentDigit)
                currentDigit = ""
                list.add(character)
            }
        }

        return list
    }

}
class Calculate(){
    private fun calculate(): String
    {
        val digitsOperators = CalcOperations().addOperations()
        if(digitsOperators.isEmpty()) return ""

        val timesDivision = calcTimesDivide(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = calcAddMinus(timesDivision)
        return result.toString()
    }

    private fun calcTimesDivide(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = listMultDiv(list)
        }
        return list
    }

    private fun listMultDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun calcAddMinus(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }






}

package com.andreisingeleytsev.testentryapp.presentation.utils

object IndexStringConverter {
    fun fromIndexToString(index: Int): String{
        return when (index+1){
            1->"Первый"
            2->"Второй"
            3->"Третий"
            4->"Четвертый"
            5->"Пятый"
            6->"Шестой"
            7->"Седьмой"
            8->"Восьмой"
            9->"Девятый"
            10->"Десятый"
            else -> ""
        }
    }
}
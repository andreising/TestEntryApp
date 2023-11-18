package com.andreisingeleytsev.testentryapp.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.formatNumberWithSpaces(): String{
    val unsignedNumber = this.toLong() and 0xFFFFFFFFL
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return numberFormat.format(unsignedNumber)
}
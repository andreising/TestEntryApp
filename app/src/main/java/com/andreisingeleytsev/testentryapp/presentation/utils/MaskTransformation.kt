package com.andreisingeleytsev.testentryapp.presentation.utils

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object PhoneNumberFilter {
    val phoneNumberFilter = VisualTransformation { text ->

        var out = ""
        for (i in text.indices) {

            fun changeChar(newChar: Char, string: String, index: Int): String{
                return if (index==0) "+7 ($newChar**) ***-**-**"
                else string.substring(0, index) + newChar + string.substring(index + 1)
            }


            out = changeChar(newChar = text[i], string = out, index = when(i){
                0 -> 0
                1 -> 5
                2 -> 6
                3 -> 9
                4 -> 10
                5 -> 11
                6 -> 13
                7 -> 14
                8 -> 16
                9 -> 17
                else -> 0
            })

        }

        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset == 0) return 0
                if (offset <= 2) return offset + 4
                if (offset <= 5) return offset + 6
                if (offset <= 7) return offset + 7

                return offset + 8

            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.length
            }

        }
        TransformedText(AnnotatedString(out), mapping)
    }


}

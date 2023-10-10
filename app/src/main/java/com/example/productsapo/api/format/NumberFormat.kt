package com.example.productsapo.api.format

import java.text.NumberFormat
import java.util.Locale

object NumberFormat {

    fun Double.formatNumber(): String {
        val formatter = NumberFormat.getInstance(Locale.US)
        formatter.maximumFractionDigits = 2
        return formatter.format(this)
    }

}
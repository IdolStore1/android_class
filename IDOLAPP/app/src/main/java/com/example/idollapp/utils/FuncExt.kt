package com.example.idollapp.utils

import java.util.Locale

fun Float.toFormatString(): String {
    return String.format(Locale.getDefault(), "%.2f", this)
}
fun Double.toFormatString(): String {
    return String.format(Locale.getDefault(), "%.2f", this)
}
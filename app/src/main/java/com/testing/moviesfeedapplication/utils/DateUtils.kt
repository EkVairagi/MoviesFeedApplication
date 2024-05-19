package com.testing.moviesfeedapplication.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun formatDate(dateString: String?): String? {
        return dateString?.let {
            val date = inputFormat.parse(it)
            date?.let { outputFormat.format(it) }
        }
    }
}
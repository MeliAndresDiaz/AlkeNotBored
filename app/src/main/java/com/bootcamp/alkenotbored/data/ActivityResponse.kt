package com.example.notbored

import java.io.Serializable

/**
* Activity Response
* Class to catch the JSON response from the API and convert it to my data class structure
*/
data class ActivityResponse(
    val accessibility: Double = 0.0,
    val activity: String = "",
    val key: String = "",
    val link: String = "",
    val participants: Int = 0,
    val price: Double = 0.0,
    val type: String = "",
    val error: String = ""
): Serializable
{
    val priceLevel: String
        get() = when {
            price == 0.0 -> "Free"
            price > 0.0 && price < 0.3 -> "Low"
            price >= 0.3 && price < 0.6 -> "Medium"
            price >= 0.6 -> "High"
            else -> "Unknown"
        }
}
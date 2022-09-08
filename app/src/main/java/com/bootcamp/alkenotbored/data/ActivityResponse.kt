package com.example.notbored

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
)
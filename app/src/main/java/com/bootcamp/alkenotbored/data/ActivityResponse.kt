package com.example.notbored

/**
* Activity Response
* Class to catch the JSON response from the API and convert it to my data class structure
*/
data class ActivityResponse(
    var accessibility: Double = 0.0,
    var activity: String = "",
    var key: String = "",
    var link: String = "",
    var participants: Int = 0,
    var price: Double = 0.0,
    var type: String = "",
    var error: String = ""
)
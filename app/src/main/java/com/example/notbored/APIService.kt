package com.example.notbored

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * This interface defines the API call and what type of data it will return (response). In our case an ActivityResponse.
 * In this interface we are only going to define the method that Retrofit will use and its configuration.
 */
interface APIService {
    /**
     * GET method with its queries.
     * Retrofit skips null parameters and ignores them while assembling the request.
     */
    @GET()
    suspend fun getActivity(
        @Url url: String,
        @Query("participants") participants: Int?,
        @Query("type") type: String?,
        @Query("price") price: Double?
    ): Response<ActivityResponse>
}
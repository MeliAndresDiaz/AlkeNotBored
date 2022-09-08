package com.bootcamp.alkenotbored.utils

import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallService : AppCompatActivity() {

    /**
     * This instance of Retrofit will have the base url of the endpoint, it will be in charge of
     * converting the JSON to ActivityResponse and will have all the configuration to make the API call.
     */
    fun initRetrofitRequest(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
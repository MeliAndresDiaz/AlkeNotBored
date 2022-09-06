package com.bootcamp.alkenotbored.util

import com.bootcamp.alkenotbored.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    /**
     * This instance of Retrofit will have the base url of the endpoint, it will be in charge of
     * converting the JSON to ActivityResponse and will have all the configuration to make the API call.
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * ESTA FUNCION ES LA QUE VA EN LA ACTIVITY LANZADORA DE LA MISMA, MOVER A ELLA CUANDO ESTE DISPONIBLE
     *
     * This function is where we will do the query to the API. This query must be made in an asynchronous thread,
     * for that we will use CoroutineScope(Dispatchers.IO).launch{} and everything that is between braces
     * will be executed in an asynchronous thread.
     *
     */

}
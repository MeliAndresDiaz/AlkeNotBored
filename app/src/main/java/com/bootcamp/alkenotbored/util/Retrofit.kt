package com.bootcamp.alkenotbored.util

import android.graphics.Color
import android.util.Log
import com.example.notbored.APIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    private val BASE_URL = "http://www.boredapi.com/api/"

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
    private fun getActivity(participants: Int? = null, type: String? = null, price: Double? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            //API call and body response
            val call = getRetrofit().create(APIService::class.java).getActivity("activity/", participants, type, price)
            val activityResponse = call.body()

            //As we're going to show the result visually, we must execute it in the UI thread. For that, we use runOnUiThread
            runOnUiThread {
                if (call.isSuccessful) {
                    activityResponse?.let { activity ->
                        //If no activity is available, show an error message
                        if (activity.error.isNotEmpty()) {
                            Snackbar
                                .make(binding.root, activity.error, Snackbar.LENGTH_LONG)
                                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                                .setBackgroundTint(Color.rgb(255,82,82))
                                .show()
                            return@runOnUiThread
                        }

                        /** TODO: Aplicar la logica para ir a la proxima pantalla con los datos requeridos
                         *  activity.type, activity.activity, activity.price, activity.participants
                         */
                        Log.d("Retrofit", activity.toString())
                    }
                } else {
                    //Call failed or not successful. We show an error message
                    Snackbar
                        .make(binding.root, "An error has occurred with API. Please try again.", Snackbar.LENGTH_LONG)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(Color.rgb(255,82,82))
                        .show()
                }
            }
        }
    }
}
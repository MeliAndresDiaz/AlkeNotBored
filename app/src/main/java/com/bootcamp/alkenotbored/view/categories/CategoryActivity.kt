package com.bootcamp.alkenotbored.view.categories

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.R
import com.bootcamp.alkenotbored.databinding.CategoriesActivityBinding
import com.bootcamp.alkenotbored.utils.Constants
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_PRICE
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS
import com.bootcamp.alkenotbored.utils.navigateTo
import com.bootcamp.alkenotbored.utils.showAlert
import com.example.notbored.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: CategoriesActivityBinding

    private lateinit var numberOfParticipants: String
    private lateinit var activityPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListView()
        setListViewListener()
        getInputUser()
    }

    private fun getInputUser() {
        val intent = intent
        numberOfParticipants = intent.getStringExtra(KEY_NUMBER_PARTICIPANTS).toString()
        activityPrice = intent.getStringExtra(KEY_ACTIVITY_PRICE).toString()
    }

    private fun setListView() {
        val categories = arrayOf(
            "Education",
            "Recreational",
            "Social",
            "Diy",
            "Charity",
            "Cooking",
            "Relaxation",
            "Music",
            "Busywork"
        )
        binding.listActivities.adapter = CategoryListAdapter(this, categories)
    }

    private fun setListViewListener() {
        binding.listActivities.setOnItemClickListener { parent, _, position, _ ->
            Log.d("ListView", "Clicked ${parent.getItemAtPosition(position)}")
            getRetrofitResponse(
                numberOfParticipants.toInt(),
                parent.getItemAtPosition(position).toString(),
                activityPrice.toDouble(),
                this@CategoryActivity
            )
        }
    }

    /**
     * This instance of Retrofit will have the base url of the endpoint, it will be in charge of
     * converting the JSON to ActivityResponse and will have all the configuration to make the API call.
     */
    private fun initRetrofitRequest(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * This function is where we will do the query to the API. This query must be made in an asynchronous thread,
     * for that we will use CoroutineScope(Dispatchers.IO).launch{} and everything that is between braces
     * will be executed in an asynchronous thread.
     */

    private fun getRetrofitResponse(
        participants: Int? = null,
        type: String? = null,
        price: Double? = null,
        activity: Activity
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            //API call and body response
            val call = initRetrofitRequest().create(APIService::class.java)
                .getActivity("activity/", participants, type, price)
            val activityResponse = call.body()

            //As we're going to show the result visually, we must execute it in the UI thread. For that, we use runOnUiThread
            activity.runOnUiThread {
                if (call.isSuccessful) {
                    activityResponse?.let { response ->
                        //If no activity is available, show an error message
                        if (response.error.isNotEmpty()) {
                            activity.showAlert(
                                R.string.global_alert_text_title,
                                messageId = response.error
                            )
                        }
                        navigateTo<SuggestionActivity> {
                            putExtra(
                                Constants.KEY_ACTIVITY,
                                response.activity
                            )
                        }
                        Log.d("Retrofit", response.toString())
                    }
                } else {
                    //Call failed or not successful. We show an error message
                    activity.showAlert(
                        R.string.global_alert_text_title,
                        messageId = "An error has occurred with API. Please try again."
                    )
                }
            }
        }
    }
}
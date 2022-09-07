package com.bootcamp.alkenotbored.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.ListAdapter
import com.bootcamp.alkenotbored.databinding.CategoriesActivityBinding
import com.bootcamp.alkenotbored.util.Retrofit
import com.example.notbored.APIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ACTIVITY_TYPE = "TYPE"
const val ACTIVITY_PRICE = "PRICE"
const val ACTIVITY_PARTICIPANTS = "PARTICIPANTS"
const val ACTIVITY = "Activity"

class CategoryActivity: AppCompatActivity() {

    private lateinit var binding: CategoriesActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListView()
        setListViewListener()
    }

    private fun setListView() {
        val categories = arrayOf("Education", "Recreational", "Social", "Diy", "Charity", "Cooking", "Relaxation", "Music", "Busywork")
        binding.listActivities.adapter = ListAdapter(this, categories)
    }

    private fun setListViewListener() {
        binding.listActivities.setOnItemClickListener { parent, _, position, _ ->
            Log.d("ListView", "Clicked ${parent.getItemAtPosition(position)}")
            getActivity(1, parent.getItemAtPosition(position).toString().lowercase(), null)
        }
    }

    private fun getActivity(participants: Int? = null, type: String? = null, price: Double? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            //API call and body response
            val call = Retrofit().getRetrofit().create(APIService::class.java).getActivity("activity/", participants, type, price)
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
                        val suggestionActivity = Intent(applicationContext, SuggestionActivity::class.java).apply {
                            /*putExtra(ACTIVITY_TYPE, activity.type)
                            putExtra(ACTIVITY_PRICE, activity.priceLevel)
                            putExtra(ACTIVITY_PARTICIPANTS, activity.participants)*/
                            putExtra(ACTIVITY, activity)
                        }
                        startActivity(suggestionActivity)

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
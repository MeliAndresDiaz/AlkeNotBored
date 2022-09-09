package com.bootcamp.alkenotbored.view.categories

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.R
import com.bootcamp.alkenotbored.databinding.CategoriesActivityBinding
import com.bootcamp.alkenotbored.utils.*
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_BUSYWORK
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_CHARITY
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_COOKING
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_DIY
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_EDUCATION
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_MUSIC
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_RECREATIONAL
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_RELAXATION
import com.bootcamp.alkenotbored.utils.Constants.CATEGORY_SOCIAL
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_NAME
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_PRICE
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_TYPE
import com.bootcamp.alkenotbored.utils.Constants.KEY_FROM_RANDOM
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS
import com.bootcamp.alkenotbored.data.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: CategoriesActivityBinding
    private lateinit var numberOfParticipants: String
    private lateinit var activityPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        setListView()
        setListViewListener()
        setRandomListener()
        getInputUser()
    }

    // Function for get the data pass with Intent in HomeActivity
    private fun getInputUser() {
        val intent = intent
        numberOfParticipants = intent.getStringExtra(KEY_NUMBER_PARTICIPANTS).toString()
        activityPrice = intent.getStringExtra(KEY_ACTIVITY_PRICE).toString()
    }

    // Function used for set a random activity
    private fun setRandomListener() {
        binding.toolbar.random.setOnClickListener {
            binding.progressBar.show()
            getRetrofitResponse(
                participants = numberOfParticipants.toInt(),
                type = null,
                price = activityPrice.toDouble().toString(),
                activity = this@CategoryActivity,
                random = true
            )
        }
    }

    // Listener for navigate to suggestion activity
    private fun setListViewListener() {
        binding.listActivities.setOnItemClickListener { parent, _, position, _ ->
            binding.progressBar.show()
            getRetrofitResponse(
                participants = numberOfParticipants.toInt(),
                type = parent.getItemAtPosition(position).toString().lowercase(),
                price = activityPrice.toDouble().toString(),
                activity = this@CategoryActivity
            )
        }
    }

    // Function used for set categories on listView
    private fun setListView() {
        val categories = arrayOf(
            CATEGORY_EDUCATION,
            CATEGORY_RECREATIONAL,
            CATEGORY_SOCIAL,
            CATEGORY_DIY,
            CATEGORY_CHARITY,
            CATEGORY_COOKING,
            CATEGORY_RELAXATION,
            CATEGORY_MUSIC,
            CATEGORY_BUSYWORK
        )
        binding.listActivities.adapter = CategoryListAdapter(this, categories)
    }

    /**
     * This function is where we will do the query to the API. This query must be made in an asynchronous thread,
     * for that we will use CoroutineScope(Dispatchers.IO).launch{} and everything that is between braces
     * will be executed in an asynchronous thread.
     *
     * As we're going to show the result visually, we must execute it in the UI thread. For that, we use runOnUiThread
     **/
    private fun getRetrofitResponse(
        participants: Int? = null,
        type: String? = null,
        price: String? = null,
        activity: Activity,
        random: Boolean = false
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = initRetrofitRequest().create(APIService::class.java)
                .getActivity("activity/", participants, type, price)
            val activityResponse = call.body()

            activity.runOnUiThread {
                binding.progressBar.hide()

                if (call.isSuccessful) {
                    activityResponse?.let { response ->
                        //If no activity is available, show an error message
                        if (response.error.isNotEmpty()) {
                            activity.showAlert(
                                R.string.global_alert_text_title,
                                messageId = response.error
                            )
                            return@runOnUiThread
                        }

                        navigateTo<SuggestionActivity> {
                            putExtra(KEY_ACTIVITY_NAME, response.activity)
                            putExtra(KEY_ACTIVITY_TYPE, response.type)
                            putExtra(KEY_NUMBER_PARTICIPANTS, numberOfParticipants)
                            putExtra(KEY_ACTIVITY_PRICE, activityPrice)
                            putExtra(KEY_FROM_RANDOM, random)
                        }
                    }
                } else {
                    //Call failed or not successful. We show an error message
                    activity.showAlert(
                        R.string.global_alert_text_title,
                        messageId = getString(R.string.category_text_error_api)
                    )
                }
            }
        }
    }
}
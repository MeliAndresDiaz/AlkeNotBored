package com.bootcamp.alkenotbored.view.categories

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.R
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding
import com.bootcamp.alkenotbored.utils.*
import com.bootcamp.alkenotbored.utils.Constants.KEY_FROM_RANDOM
import com.example.notbored.APIService
import com.example.notbored.ActivityResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuggestionActivity: AppCompatActivity() {

    private lateinit var binding: SuggestionActivityBinding
    private lateinit var numberOfParticipants: String
    private lateinit var activityPrice: String
    private lateinit var activityName:String
    private lateinit var activityType:String
    private var fromRandom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SuggestionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserInputAndActivityData()
        setListeners()
        setToolbarTitle()
        setActivityDetails()
    }

    private fun setActivityDetails() {
        with(binding){
            activityTextView.text = activityName
            numberParticipantsTextView.text = numberOfParticipants
            moneyLevelTextView.text = getPriceLevel(activityPrice)
            typeDescriptionTextView.text = activityType.replaceFirstChar { it.uppercase() }

            if (fromRandom) typeImageView.show() else typeImageView.hide()
            if (fromRandom) typeDescriptionTextView.show() else typeDescriptionTextView.hide()
            if (fromRandom) typeTextView.show() else typeTextView.hide()
        }
    }

    private fun setToolbarTitle() {
        binding.toolbarSuggestion.textView.text = if (fromRandom) "Random" else activityType.replaceFirstChar { it.uppercase() }
    }

    private fun getUserInputAndActivityData() {
        numberOfParticipants = intent.getStringExtra(Constants.KEY_NUMBER_PARTICIPANTS).toString()
        activityPrice = intent.getStringExtra(Constants.KEY_ACTIVITY_PRICE).toString()
        activityName = intent.getStringExtra(Constants.KEY_ACTIVITY_NAME).toString()
        activityType = intent.getStringExtra(Constants.KEY_ACTIVITY_TYPE).toString()
        fromRandom = intent.extras?.getBoolean(KEY_FROM_RANDOM) == true
    }

    private fun setListeners(){
        binding.toolbarSuggestion.back.setOnClickListener {
            onBackPressed()
        }
        binding.tryAnotherButton.setOnClickListener {
            binding.progressBar.show()
            getRetrofitResponse(participants = numberOfParticipants.toInt(),
            type = if (fromRandom) null else activityType,
            price = activityPrice,
            this@SuggestionActivity,
            fromRandom)
        }
    }

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

                        with(binding){
                            activityTextView.text = response.activity
                            numberParticipantsTextView.text = response.participants.toString()
                            moneyLevelTextView.text = getPriceLevel(response.price.toString())
                            typeDescriptionTextView.text = response.type.replaceFirstChar { it.uppercase() }

                            if (fromRandom) typeImageView.show() else typeImageView.hide()
                            if (fromRandom) typeDescriptionTextView.show() else typeDescriptionTextView.hide()
                            if (fromRandom) typeTextView.show() else typeTextView.hide()
                        }
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
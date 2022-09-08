package com.bootcamp.alkenotbored.view.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding
import com.bootcamp.alkenotbored.utils.Constants
import com.bootcamp.alkenotbored.utils.Constants.KEY_FROM_RANDOM
import com.bootcamp.alkenotbored.utils.getPriceLevel
import com.bootcamp.alkenotbored.utils.hide
import com.bootcamp.alkenotbored.utils.show
import com.example.notbored.ActivityResponse

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
    }

}
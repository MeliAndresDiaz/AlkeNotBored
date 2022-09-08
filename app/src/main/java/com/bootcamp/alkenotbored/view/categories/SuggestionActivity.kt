package com.bootcamp.alkenotbored.view.categories

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding
import com.bootcamp.alkenotbored.utils.Constants
import com.example.notbored.ActivityResponse

class SuggestionActivity: AppCompatActivity() {

    private lateinit var binding: SuggestionActivityBinding
    private lateinit var numberOfParticipants: String
    private lateinit var activityPrice: String
    private lateinit var activityName:String
    private lateinit var activityType:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SuggestionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserInputAndActivityData()
        setListeners()
        setToolbarTitle()
    }



    private fun setToolbarTitle() {
        binding.toolbarSuggestion.textView.text = activityType.replaceFirstChar { it.uppercase() }
    }

    private fun getUserInputAndActivityData() {
        numberOfParticipants = intent.getStringExtra(Constants.KEY_NUMBER_PARTICIPANTS).toString()
        activityPrice = intent.getStringExtra(Constants.KEY_ACTIVITY_PRICE).toString()
        activityName = intent.getStringExtra(Constants.KEY_ACTIVITY_NAME).toString()
        activityType = intent.getStringExtra(Constants.KEY_ACTIVITY_TYPE).toString()
    }

    private fun setListeners(){
        binding.toolbarSuggestion.back.setOnClickListener {
            onBackPressed()
        }
    }

}
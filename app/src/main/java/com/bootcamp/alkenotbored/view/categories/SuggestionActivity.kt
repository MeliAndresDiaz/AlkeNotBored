package com.bootcamp.alkenotbored.view.categories

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding
import com.bootcamp.alkenotbored.utils.Constants
import com.example.notbored.ActivityResponse

class SuggestionActivity: AppCompatActivity() {

    private lateinit var binding: SuggestionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SuggestionActivityBinding.inflate(layoutInflater)

        binding.toolbarSuggestion.back.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)

        /*val priceLevel = intent.getStringExtra(ACTIVITY_PRICE)
        val type = intent.getStringExtra(ACTIVITY_TYPE)
        val participants = intent.getIntExtra(ACTIVITY_PARTICIPANTS, 0)*/

        val activityResponse = intent.getSerializableExtra(Constants.KEY_ACTIVITY) as ActivityResponse

        binding.toolbarSuggestion.textView.text = activityResponse.type.replaceFirstChar { it.uppercase() }
        binding.test.text = activityResponse.activity

        Log.d("Suggestion", activityResponse.toString())
    }

}
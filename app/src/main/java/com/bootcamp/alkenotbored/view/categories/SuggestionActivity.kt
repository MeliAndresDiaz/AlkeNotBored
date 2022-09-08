package com.bootcamp.alkenotbored.view.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS
import com.example.notbored.ActivityResponse

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: SuggestionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SuggestionActivityBinding.inflate(layoutInflater)

        binding.toolbarSuggestion.back.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)

        binding.test.text = intent.getStringExtra(KEY_ACTIVITY)
        val participants = intent.getIntExtra(KEY_NUMBER_PARTICIPANTS, 0)

        val activityResponse = intent.getSerializableExtra(KEY_ACTIVITY) as ActivityResponse
        binding.toolbarSuggestion.textView.text =
            activityResponse.type.replaceFirstChar { it.uppercase() }

        //Log.d("Suggestion", activityResponse.toString())
    }

}
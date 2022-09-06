package com.bootcamp.alkenotbored.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.SuggestionActivityBinding

class SuggestionActivity: AppCompatActivity() {

    private lateinit var binding: SuggestionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SuggestionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
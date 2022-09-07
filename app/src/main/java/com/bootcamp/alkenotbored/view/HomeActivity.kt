package com.bootcamp.alkenotbored.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.HomeActivityBinding
import com.bootcamp.alkenotbored.utils.navigateTo

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.homeTextTitleTermsAndConditions.setOnClickListener {
            navigateTo<TermsAndConditionsActivity> { }
        }
    }
}
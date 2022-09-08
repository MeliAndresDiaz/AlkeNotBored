package com.bootcamp.alkenotbored.view.termsAndConditions

import android.app.Activity
import android.os.Bundle
import com.bootcamp.alkenotbored.databinding.TermsAndConditionsActivityBinding
import com.bootcamp.alkenotbored.utils.navigateTo
import com.bootcamp.alkenotbored.view.home.HomeActivity

class TermsAndConditionsActivity : Activity() {

    private lateinit var binding: TermsAndConditionsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TermsAndConditionsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.termsAndConditionsToolBarLayout.apply {
            binding.termsAndConditionsExitIcon.setOnClickListener {
                navigateTo<HomeActivity> { }
            }
        }
    }
}
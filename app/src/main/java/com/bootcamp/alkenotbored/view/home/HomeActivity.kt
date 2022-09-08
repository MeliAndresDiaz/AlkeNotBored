package com.bootcamp.alkenotbored.view.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bootcamp.alkenotbored.R
import com.bootcamp.alkenotbored.databinding.HomeActivityBinding
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_PRICE
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS
import com.bootcamp.alkenotbored.utils.navigateTo
import com.bootcamp.alkenotbored.view.categories.CategoryActivity
import com.bootcamp.alkenotbored.view.termsAndConditions.TermsAndConditionsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        observeEvent()
    }

    private fun setListeners() {
        binding.homeTextTitleTermsAndConditions.setOnClickListener {
            navigateTo<TermsAndConditionsActivity>()
        }

        binding.homeButtonStartApp.setOnClickListener {
            navigateTo<CategoryActivity> {
                putExtra(
                    KEY_NUMBER_PARTICIPANTS,
                    binding.homeFieldTextNumberOfParticipants.text.toString()
                )
                putExtra(KEY_ACTIVITY_PRICE, binding.homeFieldTextActivityPrice.text.toString())
            }
        }
    }

    private fun observeEvent() {
        binding.homeFieldTextNumberOfParticipants.let {
            it.doAfterTextChanged { input ->
                viewModel.getNumberParticipants(isValidNumberParticipants(input.toString()))
                viewModel.validateInformation()
            }
        }

        binding.homeFieldTextActivityPrice.let {
            it.doAfterTextChanged { input ->
                when {
                    input.toString().isEmpty() -> it.error =
                        getString(R.string.home_text_error_activity_price)
                    input.toString().toDouble() > 1.0 -> it.error =
                        getString(R.string.home_text_error_activity_price)
                    else -> it.error = null
                }
                viewModel.getActivityPrice(isValidActivityPrice(input.toString()))
                viewModel.validateInformation()
            }
        }

        viewModel.isEnableButtonStart.observe(this) {
            binding.homeButtonStartApp.isEnabled = it
        }
    }

    private fun isValidNumberParticipants(numberOfParticipants: String): Boolean {
        return when {
            numberOfParticipants.isEmpty() -> false
            numberOfParticipants.toInt() <= 0 -> false
            else -> true
        }
    }

    private fun isValidActivityPrice(activityPrice: String): Boolean {
        return when {
            activityPrice.isEmpty() -> false
            activityPrice.toDouble() > 1.0 -> false
            else -> true
        }
    }
}
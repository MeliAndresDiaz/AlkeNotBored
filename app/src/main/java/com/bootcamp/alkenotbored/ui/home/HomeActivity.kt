package com.bootcamp.alkenotbored.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.bootcamp.alkenotbored.R
import com.bootcamp.alkenotbored.databinding.HomeActivityBinding
import com.bootcamp.alkenotbored.utils.Constants.CHARACTER_DECIMAL
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_PRICE
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS
import com.bootcamp.alkenotbored.utils.Constants.MAX_NUMBER_PARTICIPANTS
import com.bootcamp.alkenotbored.utils.Constants.MAX_PRICE
import com.bootcamp.alkenotbored.utils.Constants.ZERO_VALUE
import com.bootcamp.alkenotbored.utils.navigateTo
import com.bootcamp.alkenotbored.ui.categories.CategoryActivity
import com.bootcamp.alkenotbored.ui.termsAndConditions.TermsAndConditionsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

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

        binding.homeCheckboxTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isTermsAccepted.value = isChecked
            viewModel.validateInformation()
        }
    }

    private fun observeEvent() {
        binding.homeFieldTextNumberOfParticipants.let {
            it.doAfterTextChanged { input ->
                when {
                    input.toString().isEmpty() -> it.error =
                        getString(R.string.home_text_error_number_participants)
                    input.toString().toInt() <= ZERO_VALUE -> it.error =
                        getString(R.string.home_text_error_number_participants)
                    input.toString().toInt() > MAX_NUMBER_PARTICIPANTS -> it.error =
                        getString(R.string.home_text_error_number_participants)
                    else -> it.error = null
                }

                viewModel.getNumberParticipants(isValidNumberParticipants(input.toString()))
                viewModel.validateInformation()
            }
        }

        binding.homeFieldTextActivityPrice.let {
            it.doAfterTextChanged { input ->
                when {
                    input.toString().isEmpty() -> it.error =
                        getString(R.string.home_text_error_activity_price)
                    input.toString() == CHARACTER_DECIMAL -> it.error =
                        getString(R.string.home_text_error_activity_price)
                    input.toString().toDouble() > MAX_PRICE -> it.error =
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
            numberOfParticipants.toInt() <= ZERO_VALUE -> false
            else -> true
        }
    }

    private fun isValidActivityPrice(activityPrice: String): Boolean {
        return when {
            activityPrice.isEmpty() -> false
            activityPrice == CHARACTER_DECIMAL -> false
            activityPrice.toDouble() > MAX_PRICE -> false
            else -> true
        }
    }
}
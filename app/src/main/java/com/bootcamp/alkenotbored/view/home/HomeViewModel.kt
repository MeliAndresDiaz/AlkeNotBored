package com.bootcamp.alkenotbored.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var isValidNumberParticipants = MutableLiveData(false)
    private var isValidActivityPrice = MutableLiveData(false)

    private var _isEnableButtonStart = MutableLiveData(false)
    val isEnableButtonStart: LiveData<Boolean>
        get() = _isEnableButtonStart


    fun getNumberParticipants(numberParticipants: Boolean) {
        isValidNumberParticipants.value = numberParticipants
    }

    fun getActivityPrice(activityPrice: Boolean) {
        isValidActivityPrice.value = activityPrice
    }

    fun validateInformation() {
        when {
            isValidNumberParticipants.value == true && isValidActivityPrice.value == true ->
                _isEnableButtonStart.value = true
            else -> _isEnableButtonStart.value = false
        }
    }
}
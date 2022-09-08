package com.bootcamp.alkenotbored.view.categories

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.alkenotbored.databinding.CategoriesActivityBinding
import com.bootcamp.alkenotbored.utils.CallService
import com.bootcamp.alkenotbored.utils.Constants.KEY_ACTIVITY_PRICE
import com.bootcamp.alkenotbored.utils.Constants.KEY_NUMBER_PARTICIPANTS

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: CategoriesActivityBinding

    private lateinit var numberOfParticipants: String
    private lateinit var activityPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListView()
        setListViewListener()
        getInputUser()
    }

    private fun getInputUser() {
        val intent = intent
        numberOfParticipants = intent.getStringExtra(KEY_NUMBER_PARTICIPANTS).toString()
        activityPrice = intent.getStringExtra(KEY_ACTIVITY_PRICE).toString()
    }

    private fun setListView() {
        val categories = arrayOf(
            "Education",
            "Recreational",
            "Social",
            "Diy",
            "Charity",
            "Cooking",
            "Relaxation",
            "Music",
            "Busywork"
        )
        binding.listActivities.adapter = CategoryListAdapter(this, categories)
    }

    private fun setListViewListener() {
        binding.listActivities.setOnItemClickListener { parent, _, position, _ ->
            Log.d("ListView", "Clicked ${parent.getItemAtPosition(position)}")
            CallService().getRetrofitResponse(
                numberOfParticipants.toInt(),
                parent.getItemAtPosition(position).toString(),
                activityPrice.toDouble(),
                this@CategoryActivity
            )
        }
    }
}
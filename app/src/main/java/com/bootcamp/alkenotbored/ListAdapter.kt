package com.bootcamp.alkenotbored

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bootcamp.alkenotbored.databinding.ItemCategoryOfActivityBinding

class ListAdapter(private val context: Activity, private val categories: Array<String>) : ArrayAdapter<String>(context, R.layout.item_category_of_activity, categories) {
    private lateinit var mBinding: ItemCategoryOfActivityBinding

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        mBinding = ItemCategoryOfActivityBinding.inflate(context.layoutInflater)
        mBinding.category.text = categories[position]
        return mBinding.root
    }
}
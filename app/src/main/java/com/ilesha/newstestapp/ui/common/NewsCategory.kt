package com.ilesha.newstestapp.ui.common

import androidx.annotation.StringRes
import com.ilesha.newstestapp.R

enum class NewsCategory(val categoryName: String, val stringResId: Int) {

    BUSINESS("business", R.string.category_business),
    ENTERTAINMENT("entertainment", R.string.category_entertainment),
    GENERAL("general", R.string.category_general),
    HEALTH("health", R.string.category_health),
    SCIENCE("science", R.string.category_science),
    SPORTS("sports", R.string.category_sports),
    TECHNOLOGY("technology", R.string.category_technology),

}
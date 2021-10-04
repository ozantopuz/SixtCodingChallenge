package com.ozantopuz.sixtcodingchallenge.util.extension

import android.widget.ImageView
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.ozantopuz.sixtcodingchallenge.R

fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .into(this)
    } ?: kotlin.run {
        setImageResource(R.drawable.ic_placeholder)
    }
}
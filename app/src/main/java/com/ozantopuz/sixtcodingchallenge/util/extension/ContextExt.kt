package com.ozantopuz.sixtcodingchallenge.util.extension

import android.content.Context
import android.widget.Toast

fun Context?.toast(text: String?, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

package com.example.thirtydayskotlin.utils

import android.content.Context
import android.widget.ProgressBar
import com.example.thirtydayskotlin.R

object Loader {
    @JvmStatic
    fun getProgressDialog(context: Context?): ProgressBar {
        return ProgressBar(context,null, R.style.AppCompatAlertDialogStyle)
    }
}
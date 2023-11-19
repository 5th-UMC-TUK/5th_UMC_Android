package com.example.chapter1.view

import android.content.res.Resources
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.chapter1.R
import com.google.android.material.snackbar.Snackbar

object CustomSnackBar {
    fun createSnackBar(view: View, message: String): Snackbar {
        return Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_SHORT
        ).apply {
            val params = this.view.layoutParams as FrameLayout.LayoutParams
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT
            params.gravity = Gravity.BOTTOM
            params.setMargins(
                50,
                0,
                50,
                context.resources
                    .getDimensionPixelSize(R.dimen.snackbar_margin_bottom)
            )
            this.view.layoutParams = params
            val snackBarView = this.view
            val snackBarText =
                snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            snackBarText.textAlignment = View.TEXT_ALIGNMENT_CENTER // 안내 텍스트 위치 조정
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
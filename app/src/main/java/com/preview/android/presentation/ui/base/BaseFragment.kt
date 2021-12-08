package com.preview.android.presentation.ui.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.preview.android.R

open class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected open val isBackPressedEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isBackPressedEnabled) {
                    isEnabled = false
                    activity?.onBackPressed()
                } else {
                    onBackPressed()
                }
            }
        })
    }

    protected open fun onBackPressed() = Unit

    protected fun showErrorDialog(repeatAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.errorDialogTitle)
            .setMessage(R.string.errorDialogMessage)
            .setPositiveButton(R.string.errorDialogButton) { _, _ ->
                repeatAction()
            }
            .setCancelable(false)
            .create()
            .show()
    }
}
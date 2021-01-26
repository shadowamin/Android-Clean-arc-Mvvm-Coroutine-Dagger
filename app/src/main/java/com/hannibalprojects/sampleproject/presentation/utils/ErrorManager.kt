package com.hannibalprojects.sampleproject.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.hannibalprojects.sampleproject.R
import com.hannibalprojects.sampleproject.presentation.models.ErrorType
import com.hannibalprojects.sampleproject.presentation.models.RequestError


object ErrorManager {

    fun displayError(context: Context?, requestError: RequestError) {
        context?.let {
            when (requestError.errorType) {
                ErrorType.SERVER_ERROR,
                ErrorType.NO_CONNECTIVITY_ERROR,
                ErrorType.TIMEOUT_ERROR -> {
                    displayErrorDialog(
                        context,
                        title = context.getString(R.string.error_occurred),
                        text = requestError.message
                    )
                }
                else -> {
                    displayErrorToast(context, requestError.message)
                }
            }
        }
    }

    // For simple errors
    private fun displayErrorToast(context: Context?, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun displayErrorDialog(context: Context, title: String? = null, text: String? = null) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton(android.R.string.ok) { dialog, id ->
                dialog.cancel()
            }.show()
    }

}
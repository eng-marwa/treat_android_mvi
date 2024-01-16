package com.treat.customer.utils.extensions

import android.R.id.custom
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.treat.customer.R


fun Fragment.hideKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.showToast(message: String?) {
    activity?.let {
        Toast.makeText(it, message ?: "", Toast.LENGTH_LONG).show()
    }
}

fun Fragment.showSnack(
    v: View?,
    message: String?,
    title: String? = "",
    isError: Boolean = false,
    showButton: Boolean? = false,
    buttonTitle: Int? = 0,
    onClick: (() -> Unit)?
) {
    activity?.let {
        val snackbar = Snackbar.make(v!!, "", Snackbar.LENGTH_LONG)
        val customSnackView = layoutInflater.inflate(R.layout.custom_snackbar_view, null)
        val card: View = customSnackView.findViewById(R.id.snackContainer)
        val messageView: TextView = customSnackView.findViewById(R.id.textView2)
        val titleView: TextView = customSnackView.findViewById(R.id.textView1)
        messageView.text = message
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        if (isError) {
            titleView.text = getString(R.string.error)
            card.setBackgroundColor(requireContext().getColor(R.color.redD4))

        } else {
            titleView.text = getString(R.string.success)
            card.setBackgroundColor(requireContext().getColor(R.color.green5E))

        }
        snackbar.view.setPadding(0, 0, 0, 0)
        val separator: View = customSnackView.findViewById(R.id.viewSeparator)
        if (showButton == true) {
            val btnSnack: Button = customSnackView.findViewById(R.id.btnSnack)
            btnSnack.setText(buttonTitle ?: 0)
            btnSnack.setOnClickListener {
                onClick?.let { it1 -> it1() }
                snackbar.dismiss()
            }
            separator.visibility = View.VISIBLE

        } else {
            separator.visibility = View.GONE
        }
        (snackbar.view as ViewGroup).addView(customSnackView)
        snackbar.show()
    }
}


fun Fragment.showDialog(
    activity: Activity,
    message: String,
    title: String,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val context: Context = ContextThemeWrapper(activity, R.style.CustomDialogTheme)
    val mDialog = MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(
            getString(R.string.ok)
        ) { dialogInterface, _ ->
            onOkClick()
            dialogInterface.dismiss()
        }.setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
            dialogInterface.dismiss()

        }


    // Show Dialog
    mDialog.show()
}


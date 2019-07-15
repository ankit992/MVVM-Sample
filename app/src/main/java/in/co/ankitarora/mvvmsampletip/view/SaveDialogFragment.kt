package `in`.co.ankitarora.mvvmsampletip.view

import `in`.co.ankitarora.mvvmsampletip.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText

class SaveDialogFragment : DialogFragment() {

    interface Callback {
        fun onSaveTip(name: String)
    }

    private var saveTipCallback: Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
        val saveDialog = context?.let { context ->
            val editText = EditText(context)
            editText.id = viewId
            editText.hint = getString(R.string.save_hint)

            AlertDialog.Builder(context).setView(editText)
                .setPositiveButton(R.string.action_save){ _, _ ->
                    onSave(editText)
                }
                .setNegativeButton(R.string.action_cancel,null)
                .create()
        }

        return saveDialog!!
    }

    private fun onSave(editText: EditText) {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            saveTipCallback?.onSaveTip(text)
        }
    }

    companion object {
        val viewId = View.generateViewId()
    }

}
package `in`.co.ankitarora.mvvmsampletip.view

import `in`.co.ankitarora.mvvmsampletip.R
import `in`.co.ankitarora.mvvmsampletip.viewmodel.CalculatorViewModel
import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.saved_tip_calculations_list.view.*

class LoadFragment: DialogFragment() {

    interface Callback {
        fun onTipSelected(name: String)
    }

    private var loadTipCallback: Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        loadTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
       val dialog = context?.let {
           AlertDialog.Builder(it)
               .setView(createView(it))
               .setNegativeButton(R.string.action_cancel, null)
               .create()
       }


        return dialog!!
    }

    private fun createView(context: Context): View {
        val rv = LayoutInflater.from(context)
            .inflate(R.layout.saved_tip_calculations_list,null)
            .recycler_saved_calculations

        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val adapter = TipSummaryAdapter{
            loadTipCallback?.onTipSelected(it.locationName)
        }
        rv.adapter = adapter

        val vm  = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        vm.loadSavedTipCalculationSummaries().observe(this, Observer {
            if(it!=null){
                adapter.updateList(it)
            }
        })
        return rv
    }

    companion object {
        val viewId = View.generateViewId()
    }

}
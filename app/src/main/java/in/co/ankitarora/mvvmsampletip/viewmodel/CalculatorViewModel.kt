package `in`.co.ankitarora.mvvmsampletip.viewmodel

import `in`.co.ankitarora.mvvmsampletip.R
import `in`.co.ankitarora.mvvmsampletip.model.RestaurantCalculator
import `in`.co.ankitarora.mvvmsampletip.model.TipCalculation
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

class CalculatorViewModel @JvmOverloads constructor(
    app: Application,
    val calculator: RestaurantCalculator = RestaurantCalculator()
) :
    ObservableViewModel(app) {

    private var lastTipCalculatod = TipCalculation()

    val outputGrandTotal
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculatod.grandTotal
        )
    val outputTipAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculatod.tipAmount
        )
    val outputCheckAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_amount,
            lastTipCalculatod.checkAmount
        )
    val locationName get() = lastTipCalculatod.locationName

    var inputCheckAmount = ""
    var inputTipPercentage = ""


    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        lastTipCalculatod = tc
        notifyChange()
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()
        if (checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
//            clearInputs()
        }

    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculatod.copy(locationName = name)

        calculator.saveRestaurantCalculation(tipToSave)

        updateOutputs(tipToSave)
    }

    fun loadSavedTipCalculationSummaries(): LiveData<List<TipCalculationSummaryItem>> {
        return Transformations.map(calculator.loadSavedRestaurantCalculations()) { tipCalculationObjects ->
            tipCalculationObjects.map {
                TipCalculationSummaryItem(
                    it.locationName,
                    getApplication<Application>().getString(R.string.dollar_amount, it.grandTotal)
                )
            }
        }
    }

    fun loadTipCalculation(name: String){
        val tc = calculator.loadRestaurantCalculationByLocationName(name)

        if(tc!=null){
            inputCheckAmount = tc.checkAmount.toString()
            inputTipPercentage = tc.tipPct.toString()

            updateOutputs(tc)
            notifyChange()
        }
    }

    private fun clearInputs() {
        inputTipPercentage = ""
        inputCheckAmount = ""
    }
}
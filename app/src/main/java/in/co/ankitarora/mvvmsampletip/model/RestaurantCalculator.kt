package `in`.co.ankitarora.mvvmsampletip.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

class RestaurantCalculator(val repository: RestaurantCalculationRepository = RestaurantCalculationRepository()){

    fun calculateTip(checkAmount: Double, tipPct: Int): TipCalculation {
        val tipAmount = (checkAmount * (tipPct.toDouble()/100)).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        val grandTotal = checkAmount + tipAmount
        return TipCalculation(checkAmount = checkAmount,
            tipPct = tipPct,
            tipAmount = tipAmount,
            grandTotal = grandTotal
            )
    }

    fun saveRestaurantCalculation(tc: TipCalculation){
        repository.saveRestaurantCalculation(tc)
    }

    fun loadRestaurantCalculationByLocationName(locationName: String): TipCalculation?{
        return repository.loadRestaurantCalculationByName(locationName)
    }

    fun loadSavedRestaurantCalculations(): LiveData<List<TipCalculation>>{
        return repository.loadSavedRestaurantCalculations()
    }
}
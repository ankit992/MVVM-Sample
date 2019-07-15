package `in`.co.ankitarora.mvvmsampletip.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class RestaurantCalculationRepository {

    private val savedCalculations  = mutableMapOf<String,TipCalculation>()

    fun saveRestaurantCalculation(restaurantCalculation: TipCalculation) {
        savedCalculations[restaurantCalculation.locationName] = restaurantCalculation
    }

    fun loadRestaurantCalculationByName(locationName: String): TipCalculation? {
        return savedCalculations[locationName]
    }

    fun loadSavedRestaurantCalculations(): LiveData<List<TipCalculation>> {
        val liveData = MutableLiveData<List<TipCalculation>>()
        liveData.value = savedCalculations.values.toList()
        return liveData
    }
}

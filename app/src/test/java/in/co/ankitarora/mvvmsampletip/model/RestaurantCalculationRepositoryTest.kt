package `in`.co.ankitarora.mvvmsampletip.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RestaurantCalculationRepositoryTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var repository : RestaurantCalculationRepository

    @Before
    fun setup(){
        repository = RestaurantCalculationRepository()
    }

    @Test
    fun testSaveRestaurantCalculation(){
        val restaurantCalculation = TipCalculation(locationName = "Romi ka Dhaba",
            checkAmount = 100.0, tipPct = 24,
            tipAmount = 24.0, grandTotal = 124.0)

        repository.saveRestaurantCalculation(restaurantCalculation)

        assertEquals(restaurantCalculation,repository.loadRestaurantCalculationByName(restaurantCalculation.locationName))
    }

    @Test
    fun testLoadSavedRestaurantCalculations(){
        val restaurantCalculation1 = TipCalculation(locationName = "Romi ka Dhaba",
            checkAmount = 100.0, tipPct = 24,
            tipAmount = 24.0, grandTotal = 124.0)
        val restaurantCalculation2 = TipCalculation(locationName = "Gul Chicken",
            checkAmount = 100.0, tipPct = 24,
            tipAmount = 24.0, grandTotal = 124.0)

        repository.saveRestaurantCalculation(restaurantCalculation1)
        repository.saveRestaurantCalculation(restaurantCalculation2)

        repository.loadSavedRestaurantCalculations().observeForever{ tipCalculations ->
            assertEquals(2, tipCalculations?.size)

        }
    }
}
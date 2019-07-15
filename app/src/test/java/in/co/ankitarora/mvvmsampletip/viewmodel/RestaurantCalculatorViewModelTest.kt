package `in`.co.ankitarora.mvvmsampletip.viewmodel

import `in`.co.ankitarora.mvvmsampletip.R
import `in`.co.ankitarora.mvvmsampletip.model.RestaurantCalculator
import `in`.co.ankitarora.mvvmsampletip.model.TipCalculation
import android.app.Application
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RestaurantCalculatorViewModelTest {

    lateinit var calculatorViewModel: CalculatorViewModel

    @Mock
    lateinit var mockCalculator: RestaurantCalculator

    @Mock
    lateinit var application: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stubResource(10.00, "$10.00")
        stubResource(0.00, "$0.00")
        stubResource(1.50, "$1.50")
        stubResource(11.50, "$11.50")
        calculatorViewModel = CalculatorViewModel(application, mockCalculator)
    }


    @Test
    fun testCalculateTip() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = "15"
        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.50, grandTotal = 11.50, tipPct = 15)
        whenever(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)

        calculatorViewModel.calculateTip()

//        assertEquals(stub, calculatorViewModel.tipCalculation)
        assertEquals("$10.00", calculatorViewModel.outputCheckAmount)
        assertEquals("$1.50", calculatorViewModel.outputTipAmount)
        assertEquals("$11.50", calculatorViewModel.outputGrandTotal)
    }

    private fun stubResource(given: Double, returnStub: String) {
        whenever(application.getString(R.string.dollar_amount, given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTipBadTipPct() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPercentage = ""

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(any(), any())

    }

    @Test
    fun testCalculateTipBadCheckAmount() {
        calculatorViewModel.inputCheckAmount = ""
        calculatorViewModel.inputTipPercentage = "10"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(any(), any())

    }

    @Test
    fun testSaveCurrentTip() {
        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 1.50, grandTotal = 11.50, tipPct = 15)
        val stubLocationName = "Green Eggs and Bacon"
        fun setupTipCalculation() {
            calculatorViewModel.inputCheckAmount = "10.00"
            calculatorViewModel.inputTipPercentage = "15"
            whenever(mockCalculator.calculateTip(10.00, 15)).thenReturn(stub)
        }
        setupTipCalculation()
        calculatorViewModel.calculateTip()
        calculatorViewModel.saveCurrentTip(stubLocationName)
        verify(mockCalculator).saveRestaurantCalculation(stub.copy(locationName = stubLocationName))

        assertEquals(stubLocationName, calculatorViewModel.locationName)


    }
}
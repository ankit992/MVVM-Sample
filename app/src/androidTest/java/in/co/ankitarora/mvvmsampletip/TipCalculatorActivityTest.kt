package `in`.co.ankitarora.mvvmsampletip

import `in`.co.ankitarora.mvvmsampletip.view.MainActivity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class TipCalculatorActivityTest{

    @get:Rule
    var activityTestRule =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun testTipCalculator(){
    //calculate tip
        enter(checkAmount = 15.99, tipPercent = 15)
        calculateTip()
        assertOutput(name="", checkAmount ="$15.99", tipAmount = "$2.40", total="$18.39")

    // save tip
        saveTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount ="$15.99", tipAmount = "$2.40", total="$18.39")

    // clear outputs
        clearOutputs()
        assertOutput(name="", checkAmount ="$0.00", tipAmount = "$0.00", total="$0.00")

    // Load tip
        loadTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount ="$15.99", tipAmount = "$2.40", total="$18.39")


    }

    private fun loadTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText(R.string.action_load)).perform(click())
        onView(withText(name)).perform(click())
    }

    private fun saveTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText(R.string.action_save)).perform(click())
        onView(withHint(R.string.save_hint)).perform(replaceText(name))
        onView(withText(R.string.action_save)).perform(click())
    }

    private fun clearOutputs() {
        enter(checkAmount = 0.0, tipPercent = 0)
        calculateTip()
    }

    private fun assertOutput(name: String, checkAmount: String, tipAmount: String, total: String) {
        onView(withId(R.id.bill_amount)).check(matches(withText(checkAmount)))
        onView(withId(R.id.tip_amount)).check(matches(withText(tipAmount)))
        onView(withId(R.id.grand_total)).check(matches(withText(total)))
        onView(withId(R.id.calculation_name)).check(matches(withText(name)))
    }

    private fun enter(checkAmount: Double, tipPercent: Int) {
        onView(withId(R.id.input_check_amount)).perform(replaceText(checkAmount.toString()))
        onView(withId(R.id.input_tip_percentage)).perform(replaceText(tipPercent.toString()))
    }

    private fun calculateTip() {
        onView(withId(R.id.fab)).perform(click())
    }

}
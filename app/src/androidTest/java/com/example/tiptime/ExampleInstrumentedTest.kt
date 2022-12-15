package com.example.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/*instrumentation test runner to run tests */
@RunWith(AndroidJUnit4::class)
//class  ExampleInstrumentedTest {
class CalculatorTests {
    /* specify subsequent rule */
    @get:Rule()
    /* launch activity before every test*/
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        /*enter text into edit text view*/
        onView(withId(R.id.cost_of_service_edit_text)) // onView() returns a ViewInteraction
            .perform(typeText("50.00")) // typeText() returns a ViewAction
            .perform(ViewActions.closeSoftKeyboard()) //close soft keyboard so doesn't hide button
        /*click calculate button*/
        onView(withId(R.id.calculate_button)) // withId() returns a ViewMatcher
            .perform(click())
        /*verify tip_result view asserts correct amount*/
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("10.00")))) //check() gets passed a ViewAssertion
    }
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tiptime", appContext.packageName)
    }
}
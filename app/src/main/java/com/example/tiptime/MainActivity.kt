package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

/**
 * Activity that displays a tip calculator.
 */
class MainActivity : AppCompatActivity() {
    /*declares a top level variable in the class for the binding object
    *defined at this level because it will be used across multiple methods
    *  in Main Activity class
    * late init is a promise to initialize variable before using it
    */
    // Binding object instance with access to the views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*initializes the binding object to access views in activity main layout*/
        // Inflate the layout xml file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)
        /*
         * sets the content view of the activity
         * instead of passing the resource id of the layout (R.layout.activity_main)
         * this specifies the root of the hierarchy of views in the app (binding.root)
        */
        // Set the content view of the Activity to be the root view of the layout
        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate tip
        binding.calculateButton.setOnClickListener { calculateTip() }

        // key listener on the text field is attached here; right after the layout is created,
        // but before the user can start interacting with the activity
        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }


    private fun calculateTip() {

        /*
         * Get the decimal value from the [cost of service] EditText field
         * and converts it to a string; then stores it as the variable
        */
        val stringInTextField = binding.costOfServiceEditText.text.toString()

        /*
         * converts the string to a double if an integer input was made by the user
         * toDouble will crash the app, if the calculate is executed without an input
         * as the string would not be a valid representation of a number
        */
        val cost = stringInTextField.toDoubleOrNull()

        // added the if statement to return out of the method if the value is null
        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            /*
             * this line resets the tip result text view to a blank string
             * if the edit text view is null.
             * Refactored to the displayTip function
             */
            //binding.tipResult.text = ""
            displayTip(0.0)
            return
        }

        // Get the tip percentage based on which radio button is selected
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calculate the tip
        var tip = tipPercentage * cost

        // If the switch for rounding up the tip is toggled on (isChecked is true),
        // then round up the tip. Otherwise do not change the tip value.
        /* reformatted
        *  val roundUp = binding.roundupSwitch.isChecked
        *  if (roundUp) { tip = kotlin.math.ceil(tip) }
         */
        if (binding.roundUpSwitch.isChecked) {
            // Take the ceiling of the current tip, which rounds up to the next integer,
            // and store the new value in the tip variable.
            tip = kotlin.math.ceil(tip)
        }

        // Display formatted tip value onscreen
        displayTip(tip)


    }

    /**
     * Format the tip amount according to the local currency and display it onscreen.
     * Example would be "Tip Amount: $10.00".
     */
    fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    /**
     * Key listener for hiding the keyboard when the "Enter" button is tapped.
     */
    // A helper function that hides the soft keyboard when the enter button is pushed.
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            //Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}

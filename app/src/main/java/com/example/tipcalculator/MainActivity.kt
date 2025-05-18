package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var amountInput by remember { mutableStateOf("") }
    var tipResult by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tip Calculator",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Please enter the Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TipButton(percentage = 0.15, text = "15%") {
                // Use the new handler function
                handleTipCalculation(amountInput, 0.15, context) { result ->
                    tipResult = result
                }
            }
            TipButton(percentage = 0.18, text = "18%") {
                handleTipCalculation(amountInput, 0.18, context) { result ->
                    tipResult = result
                }
            }
            TipButton(percentage = 0.20, text = "20%") {
                handleTipCalculation(amountInput, 0.20, context) { result ->
                    tipResult = result
                }
            }
        }

        if (tipResult.isNotEmpty()) {
            val parts = tipResult.split("\n")
            if (parts.size == 2) { // Check if it's a valid result, not an error indicator
                Text(
                    text = tipResult,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

@Composable
fun TipButton(percentage: Double, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .defaultMinSize(minWidth = 90.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

/**
 * Pure Kotlin function that performs the tip calculation logic.
 * This function is easily unit-testable.
 *
 * @param amountInput The bill amount as a string.
 * @param percentage The tip percentage (e.g., 0.15 for 15%).
 * @return A formatted string with "Tip: $X.XX\nTotal Bill: $Y.YY" or an empty string if input is invalid.
 */
fun calculateTipLogic(
    amountInput: String,
    percentage: Double
): String {
    val amount = amountInput.toDoubleOrNull()

    if (amount == null || amount <= 0) {
        return "" // Return empty string to indicate invalid input or error
    }

    val tip = amount * percentage
    val total = amount + tip
    return "Tip: $%.2f\nTotal Bill: $%.2f".format(tip, total)
}

/**
 * Handles the tip calculation, including showing a Toast for invalid input
 * and calling the onResult lambda with the calculation result.
 * This function calls the pure `calculateTipLogic` function.
 */
fun handleTipCalculation(
    amountInput: String,
    percentage: Double,
    context: android.content.Context,
    onResult: (String) -> Unit
) {
    val resultString = calculateTipLogic(amountInput, percentage)

    // Show Toast only if calculateTipLogic returned an empty string (indicating an error)
    // AND the original input was indeed problematic (not just an empty input that should clear the result).
    // This logic helps avoid showing a toast if the user just clears the input field.
    val isActualError = resultString.isEmpty() && (amountInput.isNotEmpty() && (amountInput.toDoubleOrNull() == null || amountInput.toDoubleOrNull()!! <= 0))


    if (isActualError) {
        android.widget.Toast.makeText(
            context,
            "Please enter a valid positive number",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
    onResult(resultString) // Pass the result (empty or formatted) to the UI
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}
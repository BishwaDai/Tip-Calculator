package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// import androidx.compose.animation.core.copy // Not used, can be removed
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button // Ensure this is the M3 Button
import androidx.compose.material3.ButtonDefaults // Ensure this is the M3 ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField // Ensure this is the M3 OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text // Ensure this is the M3 Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// import androidx.compose.runtime.getValue // Redundant with by delegate
// import androidx.compose.runtime.setValue // Redundant with by delegate
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Standard Color import
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme { // Apply your app's M3 theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // Use background from M3 colorScheme
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
            style = MaterialTheme.typography.headlineSmall, // Using a more appropriate M3 style
            color = MaterialTheme.colorScheme.primary // Using primary color for the title
        )

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge // Use M3 typography
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TipButton(percentage = 0.15, text = "15%") {
                calculateAndDisplayTip(amountInput, 0.15, context) { result ->
                    tipResult = result
                }
            }
            TipButton(percentage = 0.18, text = "18%") {
                calculateAndDisplayTip(amountInput, 0.18, context) { result ->
                    tipResult = result
                }
            }
            TipButton(percentage = 0.20, text = "20%") {
                calculateAndDisplayTip(amountInput, 0.20, context) { result ->
                    tipResult = result
                }
            }
        }

        if (tipResult.isNotEmpty()) {
            Text(
                text = tipResult,
                style = MaterialTheme.typography.titleLarge, // Using a more appropriate M3 style for result
                color = MaterialTheme.colorScheme.onBackground, // Ensure good contrast
                modifier = Modifier.padding(top = 24.dp)
            )
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
        colors = ButtonDefaults.buttonColors( // M3 ButtonDefaults
            containerColor = MaterialTheme.colorScheme.secondary, // Correct M3 parameter
            contentColor = MaterialTheme.colorScheme.onSecondary    // Correct M3 parameter, get from theme
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge) // Use M3 typography for button text
    }
}

fun calculateAndDisplayTip(
    amountInput: String,
    percentage: Double,
    context: android.content.Context,
    onResult: (String) -> Unit
) {
    val amount = amountInput.toDoubleOrNull()

    if (amount == null || amount <= 0) {
        android.widget.Toast.makeText(context, "Please enter a valid positive number", android.widget.Toast.LENGTH_SHORT).show()
        onResult("")
        return
    }

    val tip = amount * percentage
    val total = amount + tip
    // Using String.format for locale-aware currency formatting might be better in a real app
    onResult("Tip: $%.2f\nTotal Bill: $%.2f".format(tip, total))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}
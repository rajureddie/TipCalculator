package com.example.tipcalculator

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import androidx.compose.runtime.getValue
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ){
                    TipGeneratorLayout()
                }

            }
        }
    }
}

@Composable
fun TipGeneratorLayout( modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember {mutableStateOf(false)}

    // Calculate amount and tip whenever amountInput changes
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?:0.0
    val tip = calculateTip(amount, tipPercent,roundUp)
    Column (
        modifier = modifier.statusBarsPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.app_name),
            modifier= Modifier
                .padding(bottom = 16.dp,top =40.dp )
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,



        )
        Spacer(modifier = Modifier.height((30.dp)))
//        EditNumberField(
//            value = amountInput,
//            leadingIcon = R.drawable.baseline_money_24,
//            onValueChanged = { amountInput = it },
//            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next)
//        )
//
//
//        EditNumberField(
//            leadingIcon = R.drawable.outline_add_2_24,
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType=KeyboardType.Number,imeAction = ImeAction.Done),
//            label = R.string.how_was_the_service,
//            value = tipInput,
//            onValueChanged = { tipInput = it },
//            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
//        )
//
//        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = {roundUp = it})
//        Spacer(modifier = Modifier.height((30.dp)))
//        Text(text = stringResource(R.string.tipamount, tip),
//            fontSize = 25.sp,
//            modifier = Modifier.align(Alignment.Start).fillMaxWidth()
//            )
//        Spacer(modifier = Modifier.height((150.dp)))

    }
}

@Composable
fun EditNumberField(
    @DrawableRes leadingIcon: Int,
    value: String,

    onValueChanged: (String) -> Unit,
    modifier: Modifier=Modifier,
    keyboardOptions : KeyboardOptions
){
    TextField(
        value = value,
        leadingIcon={Icon(painter = painterResource(id = leadingIcon),null)},
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = {Text(stringResource(R.string.billamount))},
        keyboardOptions = keyboardOptions

    )
}

@Composable
fun EditNumberField(@StringRes label: Int, @DrawableRes leadingIcon:Int, value: String, onValueChanged: (String) -> Unit, modifier: Modifier = Modifier, keyboardOptions: KeyboardOptions)
{
    TextField(
        value=value,
        leadingIcon={ Icon(painter = painterResource(id=leadingIcon),null) },
        singleLine = true,
        modifier= modifier,
        onValueChange = onValueChanged,
        label={Text(stringResource(label))},
        keyboardOptions = keyboardOptions,

    )
}

@Composable
fun RoundTheTipRow(roundUp: Boolean,onRoundUpChanged : (Boolean)-> Unit,modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically


    ){
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),

            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }

}
@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercent: Double,roundUp: Boolean ): String{
    var tip = tipPercent / 100 * amount
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipGeneratorLayout()
    }
}
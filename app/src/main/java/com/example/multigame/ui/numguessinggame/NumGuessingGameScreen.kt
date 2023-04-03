package com.example.multigame.ui.numguessinggame

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.multigame.MultiGameAppBar
import com.example.multigame.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NumGuessingGameScreen(navController: NavHostController) {
    var targetNumber by remember { mutableStateOf((1..1000).random()) }
    var guessCount by remember { mutableStateOf(0) }
    var guessTextFieldValue by remember { mutableStateOf("") }
    var feedbackText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var isGuessButtonEnabled by remember { mutableStateOf(true) }
    var isNewGameButtonEnabled by remember { mutableStateOf(true) }

     fun newGame() {
        isGuessButtonEnabled = true
        guessTextFieldValue = ""
        targetNumber = (1..1000).random()
        guessCount = 0
        resultText = ""
        feedbackText = ""
    }

    fun guessButtonClick() {
        val guess = guessTextFieldValue.toIntOrNull()
        if (guess == null || guess < 1 || guess > 1000) {
            guessTextFieldValue = ""
            feedbackText = "Please enter a valid number."
        } else {
            guessCount++
            if (guess == targetNumber) {
                guessCount--
                isGuessButtonEnabled = false
                resultText = if (guessCount == 1) {
                    "Congratulations! You won in $guessCount guess!"
                } else {
                    "Congratulations! You won in $guessCount guesses!"
                }
            } else {
                feedbackText = if (guess < targetNumber) {
                    "Hint: $guess is too low."
                } else {
                    "Hint: $guess is too high."
                }
            }
            guessTextFieldValue = ""
        }
    }

    MultiGameAppBar(navController = navController) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(20.dp),
                text = stringResource(id = R.string.app_name_num), fontSize = 30.sp,
                color = colorResource(id = R.color.my_blue_2)
            )
            Divider(
                color = colorResource(id = R.color.greyLight),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.instructions),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(
                        colorResource(id = R.color.my_blue_2),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp, 40.dp, 20.dp, 40.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.white)
            )
            if (resultText.isNotBlank()) {
                Text(
                    text = resultText,
                    color = colorResource(id = R.color.yellowNum),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 19.sp
                )
            } else if (feedbackText == stringResource(id = R.string.enter_valid_number)) {
                Text(
                    text = feedbackText,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 19.sp
                )
            } else {
                Text(
                    text = feedbackText,
                    color = colorResource(id = R.color.my_blue_2),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 19.sp
                )
            }
            EditNumberField(
                value = guessTextFieldValue,
                onValueChange = { guessTextFieldValue = it },
                isGuessButtonEnabled = isGuessButtonEnabled,
            )
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    enabled = isGuessButtonEnabled,
                    onClick = { guessButtonClick() },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(250.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.my_yellow_orange),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.guess_button),
                        fontSize = 18.sp
                    )
                }
                Button(
                    enabled = isNewGameButtonEnabled,
                    onClick = { newGame() },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(250.dp)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.my_blue_2),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.new_game_button),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}

@Composable
private fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    isGuessButtonEnabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = R.string.guess_label), color = colorResource(id = R.color.my_blue_2)) },
        enabled = isGuessButtonEnabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.my_yellow_orange),
            cursorColor = colorResource(id = R.color.my_blue_2)
        ),
        modifier = Modifier
            .width(300.dp)
            .padding(16.dp),
    )
}

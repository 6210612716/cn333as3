package com.example.multigame.ui.memorymatchinggame

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.multigame.R
import androidx.navigation.NavHostController
import com.example.multigame.MultiGameAppBar

private val cardValues = listOf(
    R.drawable.apple, R.drawable.banana, R.drawable.grape, R.drawable.mango,
    R.drawable.orange, R.drawable.pear, R.drawable.strawberry, R.drawable.watermelon
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MemoryMatchingGameScreen(navController: NavHostController) {
    var cardPairs = remember { generateCardPairs() }
    var selectedCardIndex by remember { mutableStateOf(-1) }
    var matchedCards by remember { mutableStateOf(emptySet<Int>()) }
    var movesCount by remember { mutableStateOf(0) }
    var pairsCount by remember { mutableStateOf(0) }
    var isDelayActive by remember { mutableStateOf(false) }

    fun newGame() {
        cardPairs = generateCardPairs()
        selectedCardIndex = -1
        matchedCards = emptySet()
        movesCount = 0
        pairsCount = 0
        isDelayActive = false
    }

    MultiGameAppBar(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    colorResource(id = R.color.my_yellow_orange),
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier

                    .align(Alignment.CenterHorizontally)
                    .background(
                        colorResource(id = R.color.steel_blue),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp),
                text = stringResource(id = R.string.app_name_mem), fontSize = 28.sp,
                color = colorResource(id = R.color.ivory)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier
                .width(400.dp)
                .height(40.dp)
                .padding(4.dp)
                .background(
                    colorResource(id = R.color.ivory),
                    shape = RoundedCornerShape(8.dp)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier
                    .padding(4.dp),
                    text = stringResource(id = R.string.movesCount, movesCount), fontSize = 20.sp)
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.End),
                    text = stringResource(id = R.string.pairsCount, pairsCount, cardPairs.size/2),
                    fontSize = 20.sp)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        colorResource(id = R.color.ivory),
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                items(cardPairs.size) { index ->
                    val cardValue = cardPairs[index]
                    val isSelected = index == selectedCardIndex
                    val isMatched = index in matchedCards
                    var isUnmatched by remember { mutableStateOf(false) }
                    val isClickable = !isDelayActive && !isMatched
                    val scope = rememberCoroutineScope()

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clickable(enabled = isClickable) {
                                if (!isMatched && !isUnmatched) {
                                    if (selectedCardIndex == index) {
                                        return@clickable
                                    }

                                    if (selectedCardIndex == -1) {
                                        selectedCardIndex = index
                                    } else {
                                        val isMatch = cardPairs[selectedCardIndex] == cardValue
                                        if (isMatch) {
                                            matchedCards += setOf(selectedCardIndex, index)
                                            pairsCount++
                                            selectedCardIndex = -1
                                        } else {
                                            isUnmatched = true
                                            isDelayActive = true
                                            scope.launch {
                                                delay(500)
                                                selectedCardIndex = -1
                                                isUnmatched = false
                                                isDelayActive = false
                                            }
                                        }
                                        movesCount++
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected || isMatched || isUnmatched) {
                            Image(
                                painter = painterResource(id = cardValue),
                                contentDescription = "Fruit image",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "Default image",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            if (pairsCount == cardPairs.size/2) {
                FinalScoreDialog(
                    movesCount = movesCount,
                    onPlayAgain = {
                        newGame()
                    },
                    navController = navController
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
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

@Composable
fun FinalScoreDialog(
    movesCount: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.end_game),
            fontSize = 20.sp,
            color = colorResource(id = R.color.my_yellow_orange)
        ) },
        text = { Text(stringResource(R.string.you_moves, movesCount),
            fontSize = 18.sp,
            color = colorResource(id = R.color.my_blue)
        ) },
        modifier = modifier,
        dismissButton = {
            IconButton(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.back_button),
                    tint = colorResource(id = R.color.my_blue_2)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onPlayAgain,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.my_yellow_orange),
                    contentColor = colorResource(id = R.color.my_blue)
                )
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

fun generateCardPairs(): List<Int> {
    val shuffledImages = cardValues.shuffled()
    return (shuffledImages + shuffledImages).shuffled()
}

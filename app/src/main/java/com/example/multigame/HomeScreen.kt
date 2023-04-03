package com.example.multigame

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(
            colorResource(id = R.color.my_blue_2),
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = stringResource(id = R.string.app_name), fontSize = 30.sp,
            color = colorResource(id = R.color.my_yellow_orange)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = stringResource(id = R.string.main_instructions), fontSize = 20.sp,
            color = colorResource(id = R.color.ivory)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("num_guessing_game") },
            modifier = Modifier
                .width(280.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.steel_blue),
                contentColor = colorResource(id = R.color.white),
            )
        ) {
            Text(
                text = stringResource(id = R.string.app_name_num),
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = { navController.navigate("quiz_game") },
            modifier = Modifier
                .width(280.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.steel_blue),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(
                text = stringResource(id = R.string.app_name_quiz),
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = { navController.navigate("mem_match_game") },
            modifier = Modifier
                .width(280.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.steel_blue),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(
                text = "Memory Matching Game",
                fontSize = 18.sp,
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MultiGameAppBar(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.dark_grey)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("home")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(id = R.string.back_button),
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        },
        content = {
            content()
        }
    )
}
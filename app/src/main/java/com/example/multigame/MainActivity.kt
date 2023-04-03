package com.example.multigame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multigame.ui.memorymatchinggame.MemoryMatchingGameScreen
import com.example.multigame.ui.theme.MultiGameTheme

import com.example.multigame.ui.numguessinggame.NumGuessingGameScreen
import com.example.multigame.ui.quizgame.QuizGameScreen

sealed class Screen(val route: String){
    object Home: Screen("home")
    object NumGuessingGame: Screen("num_guessing_game")
    object QuizGame: Screen("quiz_game")
    object MemoryMatchingGame: Screen("mem_match_game")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_grey)

        setContent {
            val navController = rememberNavController()
            MultiGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController, startDestination = "home") {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(Screen.NumGuessingGame.route) {
                            NumGuessingGameScreen(navController = navController)
                        }
                        composable(Screen.QuizGame.route) {
                            QuizGameScreen(navController = navController)
                        }
                        composable(Screen.MemoryMatchingGame.route) {
                            MemoryMatchingGameScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MultiGameTheme {
        val navController = rememberNavController()
        HomeScreen(navController, modifier = Modifier)
    }
}
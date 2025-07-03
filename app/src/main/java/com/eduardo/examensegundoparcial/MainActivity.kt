package com.eduardo.examensegundoparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eduardo.examensegundoparcial.ui.theme.ExamenSegundoParcialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenSegundoParcialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("formulario") }
    var userData by remember { mutableStateOf(UserData()) }
    var examAnswers by remember { mutableStateOf(listOf<Int>()) }
    var examResults by remember { mutableStateOf(listOf<Boolean>()) }
    var score by remember { mutableStateOf(0) }

    when (currentScreen) {
        "formulario" -> {
            FormularioScreen(
                userData = userData,
                onUserDataChange = { userData = it },
                onNavigateToExam = { currentScreen = "examen" }
            )
        }
        "examen" -> {
            ExamenScreen(
                onExamFinished = { answers, results, finalScore ->
                    examAnswers = answers
                    examResults = results
                    score = finalScore
                    currentScreen = "revision"
                }
            )
        }
        "revision" -> {
            RevisionScreen(
                answers = examAnswers,
                results = examResults,
                onNavigateToResult = { currentScreen = "resultado" }
            )
        }
        "resultado" -> {
            ResultadoScreen(
                userData = userData,
                score = score,
                onRestart = {
                    currentScreen = "formulario"
                    userData = UserData()
                    examAnswers = listOf()
                    examResults = listOf()
                    score = 0
                }
            )
        }
    }
}

data class UserData(
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val dia: String = "",
    val mes: String = "",
    val año: String = "",
    val sexo: String = ""
)
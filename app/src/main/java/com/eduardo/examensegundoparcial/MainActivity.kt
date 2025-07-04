package com.eduardo.examensegundoparcial

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.eduardo.examensegundoparcial.ui.theme.ExamenSegundoParcialTheme

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        if (allGranted) {
            println("Permisos de almacenamiento concedidos")
        } else {
            println("Permisos de almacenamiento denegados")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

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
    val context = LocalContext.current
    val dataManager = remember { DataManager(context) }

    var currentScreen by remember { mutableStateOf("formulario") }
    var userData by remember { mutableStateOf(UserData()) }
    var examAnswers by remember { mutableStateOf(listOf<Int>()) }
    var examResults by remember { mutableStateOf(listOf<Boolean>()) }
    var score by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        dataManager.loadUserData()?.let { loadedUserData ->
            userData = loadedUserData
        }
        dataManager.saveActivityLog("Aplicación iniciada")
        println("Archivos guardados en: ${dataManager.getStoragePath()}")
    }

    when (currentScreen) {
        "formulario" -> {
            FormularioScreen(
                userData = userData,
                onUserDataChange = { newUserData ->
                    userData = newUserData
                    dataManager.saveUserData(newUserData)
                    dataManager.saveActivityLog("Datos de usuario guardados")
                },
                onNavigateToExam = {
                    currentScreen = "examen"
                    dataManager.saveActivityLog("Navegó al examen")
                }
            )
        }
        "examen" -> {
            ExamenScreen(
                onExamFinished = { answers, results, finalScore ->
                    examAnswers = answers
                    examResults = results
                    score = finalScore
                    dataManager.saveExamResults(userData, answers, results, finalScore)
                    dataManager.saveActivityLog("Examen completado - Calificación: $finalScore")
                    currentScreen = "revision"
                }
            )
        }
        "revision" -> {
            RevisionScreen(
                answers = examAnswers,
                results = examResults,
                onNavigateToResult = {
                    currentScreen = "resultado"
                    dataManager.saveActivityLog("Navegó al resultado final")
                }
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
                    dataManager.clearAllData()
                    dataManager.saveActivityLog("Aplicación reiniciada")
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
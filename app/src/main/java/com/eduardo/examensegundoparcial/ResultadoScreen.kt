package com.eduardo.examensegundoparcial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun ResultadoScreen(
    userData: UserData,
    score: Int,
    onRestart: () -> Unit
) {
    val edad = calcularEdad(userData.año.toIntOrNull() ?: 2000)

    val signoChino = calcularSignoZodiacalChino(userData.año.toIntOrNull() ?: 2000)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resultado Final",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Hola ${userData.nombre} ${userData.apellidoPaterno} ${userData.apellidoMaterno}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Tienes $edad años y tu signo zodiacal",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getSignoEmoji(signoChino),
                        fontSize = 60.sp
                    )
                }

                Text(
                    text = "Es $signoChino",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Calificación $score",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        score >= 5 -> Color.Green
                        score >= 3 -> Color(0xFFFF9800) // Orange
                        else -> Color.Red
                    },
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Información Personal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Nombre completo: ${userData.nombre} ${userData.apellidoPaterno} ${userData.apellidoMaterno}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Fecha de nacimiento: ${userData.dia}/${userData.mes}/${userData.año}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Sexo: ${userData.sexo}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Edad: $edad años",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Signo zodiacal chino: $signoChino",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = onRestart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reiniciar Aplicación")
                }
            }
        }
    }
}

fun calcularEdad(añoNacimiento: Int): Int {
    val añoActual = Calendar.getInstance().get(Calendar.YEAR)
    return añoActual - añoNacimiento
}

fun calcularSignoZodiacalChino(año: Int): String {
    val signos = arrayOf(
        "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
        "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
    )
    return signos[año % 12]
}

fun getSignoEmoji(signo: String): String {
    return when (signo) {
        "Rata" -> "🐭"
        "Buey" -> "🐂"
        "Tigre" -> "🐅"
        "Conejo" -> "🐰"
        "Dragón" -> "🐲"
        "Serpiente" -> "🐍"
        "Caballo" -> "🐴"
        "Cabra" -> "🐐"
        "Mono" -> "🐵"
        "Gallo" -> "🐓"
        "Perro" -> "🐕"
        "Cerdo" -> "🐷"
        else -> "🐴"
    }
}
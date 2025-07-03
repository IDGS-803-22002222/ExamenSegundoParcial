package com.eduardo.examensegundoparcial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RevisionScreen(
    answers: List<Int>,
    results: List<Boolean>,
    onNavigateToResult: () -> Unit
) {
    val preguntas = listOf(
        Pregunta("¿Qué país ganó el Mundial de Fútbol 2022?", listOf("Francia", "Brasil", "Argentina", "Alemania"), 2),
        Pregunta("¿En qué país se celebró el Mundial de Fútbol 2018?", listOf("Brasil", "Rusia", "Qatar", "Alemania"), 1),
        Pregunta("¿Cuál es el máximo goleador histórico de los Mundiales?", listOf("Pelé", "Miroslav Klose", "Ronaldo", "Maradona"), 1),
        Pregunta("¿Qué país ha ganado más Mundiales de Fútbol?", listOf("Alemania", "Argentina", "Brasil", "Italia"), 2),
        Pregunta("¿En qué año se celebró el primer Mundial de Fútbol?", listOf("1928", "1930", "1932", "1934"), 1),
        Pregunta("¿Quién fue el Balón de Oro del Mundial 2022?", listOf("Kylian Mbappé", "Lionel Messi", "Luka Modrić", "Cristiano Ronaldo"), 1)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Revisión del Examen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        preguntas.forEachIndexed { index, pregunta ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${index + 1}. ${pregunta.pregunta}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    pregunta.opciones.forEachIndexed { opcionIndex, opcion ->
                        val isSelected = answers[index] == opcionIndex
                        val isCorrect = opcionIndex == pregunta.respuestaCorrecta
                        val isWrongSelection = isSelected && !isCorrect

                        val backgroundColor = when {
                            isCorrect -> Color.Green.copy(alpha = 0.3f)
                            isWrongSelection -> Color.Red.copy(alpha = 0.3f)
                            else -> Color.Transparent
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(backgroundColor)
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { },
                                enabled = false
                            )
                            Text(
                                text = "${('a' + opcionIndex)} $opcion",
                                modifier = Modifier.padding(start = 8.dp),
                                color = when {
                                    isCorrect -> Color.Green
                                    isWrongSelection -> Color.Red
                                    else -> Color.Black
                                }
                            )

                            if (isCorrect) {
                                Text(
                                    text = " ✓",
                                    color = Color.Green,
                                    fontWeight = FontWeight.Bold
                                )
                            } else if (isWrongSelection) {
                                Text(
                                    text = " ✗",
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    val resultText = if (results[index]) "Correcta" else "Incorrecta"
                    val resultColor = if (results[index]) Color.Green else Color.Red

                    Text(
                        text = "Resultado: $resultText",
                        color = resultColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        // Resumen
        val correctas = results.count { it }
        val incorrectas = results.size - correctas

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resumen",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Respuestas correctas: $correctas",
                    color = Color.Green,
                    fontSize = 16.sp
                )
                Text(
                    text = "Respuestas incorrectas: $incorrectas",
                    color = Color.Red,
                    fontSize = 16.sp
                )
                Text(
                    text = "Calificación: $correctas/6",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Button(
            onClick = onNavigateToResult,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Ver Resultado Final")
        }
    }
}
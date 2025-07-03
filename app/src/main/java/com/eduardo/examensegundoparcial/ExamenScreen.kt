package com.eduardo.examensegundoparcial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Pregunta(
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int
)

@Composable
fun ExamenScreen(
    onExamFinished: (List<Int>, List<Boolean>, Int) -> Unit
) {
    val preguntas = listOf(
        Pregunta("¿Qué país ganó el Mundial de Fútbol 2022?", listOf("Francia", "Brasil", "Argentina", "Alemania"), 2),
        Pregunta("¿En qué país se celebró el Mundial de Fútbol 2018?", listOf("Brasil", "Rusia", "Qatar", "Alemania"), 1),
        Pregunta("¿Cuál es el máximo goleador histórico de los Mundiales?", listOf("Pelé", "Miroslav Klose", "Ronaldo", "Maradona"), 1),
        Pregunta("¿Qué país ha ganado más Mundiales de Fútbol?", listOf("Alemania", "Argentina", "Brasil", "Italia"), 2),
        Pregunta("¿En qué año se celebró el primer Mundial de Fútbol?", listOf("1928", "1930", "1932", "1934"), 1),
        Pregunta("¿Quién fue el Balón de Oro del Mundial 2022?", listOf("Kylian Mbappé", "Lionel Messi", "Luka Modrić", "Cristiano Ronaldo"), 1)
    )

    var respuestasSeleccionadas by remember { mutableStateOf(List(6) { -1 }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Examen",
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (respuestasSeleccionadas[index] == opcionIndex),
                                    onClick = {
                                        respuestasSeleccionadas = respuestasSeleccionadas.toMutableList().apply {
                                            this[index] = opcionIndex
                                        }
                                    }
                                )
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (respuestasSeleccionadas[index] == opcionIndex),
                                onClick = {
                                    respuestasSeleccionadas = respuestasSeleccionadas.toMutableList().apply {
                                        this[index] = opcionIndex
                                    }
                                }
                            )
                            Text(
                                text = "${('a' + opcionIndex)} $opcion",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                val resultados = preguntas.mapIndexed { index, pregunta ->
                    respuestasSeleccionadas[index] == pregunta.respuestaCorrecta
                }
                val calificacion = resultados.count { it }

                onExamFinished(respuestasSeleccionadas, resultados, calificacion)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = respuestasSeleccionadas.all { it != -1 }
        ) {
            Text("Terminar Examen")
        }
    }
}
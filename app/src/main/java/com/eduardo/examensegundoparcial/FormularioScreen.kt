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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    userData: UserData,
    onUserDataChange: (UserData) -> Unit,
    onNavigateToExam: () -> Unit
) {
    var nombre by remember { mutableStateOf(TextFieldValue(userData.nombre)) }
    var apellidoPaterno by remember { mutableStateOf(TextFieldValue(userData.apellidoPaterno)) }
    var apellidoMaterno by remember { mutableStateOf(TextFieldValue(userData.apellidoMaterno)) }
    var dia by remember { mutableStateOf(TextFieldValue(userData.dia)) }
    var mes by remember { mutableStateOf(TextFieldValue(userData.mes)) }
    var año by remember { mutableStateOf(TextFieldValue(userData.año)) }
    var sexo by remember { mutableStateOf(userData.sexo) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Datos Personales",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = apellidoPaterno,
            onValueChange = { apellidoPaterno = it },
            label = { Text("Apellido Paterno") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = apellidoMaterno,
            onValueChange = { apellidoMaterno = it },
            label = { Text("Apellido Materno") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Fecha de Nacimiento",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = dia,
                onValueChange = { dia = it },
                label = { Text("Día") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = mes,
                onValueChange = { mes = it },
                label = { Text("Mes") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = año,
                onValueChange = { año = it },
                label = { Text("Año") },
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Sexo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (sexo == "Masculino"),
                        onClick = { sexo = "Masculino" }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (sexo == "Masculino"),
                    onClick = { sexo = "Masculino" }
                )
                Text(
                    text = "Masculino",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (sexo == "Femenino"),
                        onClick = { sexo = "Femenino" }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (sexo == "Femenino"),
                    onClick = { sexo = "Femenino" }
                )
                Text(
                    text = "Femenino",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    nombre = TextFieldValue("")
                    apellidoPaterno = TextFieldValue("")
                    apellidoMaterno = TextFieldValue("")
                    dia = TextFieldValue("")
                    mes = TextFieldValue("")
                    año = TextFieldValue("")
                    sexo = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }

            Button(
                onClick = {
                    onUserDataChange(
                        UserData(
                            nombre = nombre.text,
                            apellidoPaterno = apellidoPaterno.text,
                            apellidoMaterno = apellidoMaterno.text,
                            dia = dia.text,
                            mes = mes.text,
                            año = año.text,
                            sexo = sexo
                        )
                    )
                    onNavigateToExam()
                },
                modifier = Modifier.weight(1f),
                enabled = nombre.text.isNotEmpty() && apellidoPaterno.text.isNotEmpty() &&
                        dia.text.isNotEmpty() && mes.text.isNotEmpty() && año.text.isNotEmpty() &&
                        sexo.isNotEmpty()
            ) {
                Text("Siguiente")
            }
        }
    }
}
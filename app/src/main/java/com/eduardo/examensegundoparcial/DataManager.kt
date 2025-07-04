package com.eduardo.examensegundoparcial

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class DataManager(private val context: Context) {

    private val storageDir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        "ExamenSegundoParcial"
    )

    private val USER_DATA_FILE = "user_data.txt"
    private val EXAM_RESULTS_FILE = "exam_results.txt"
    private val ACTIVITY_LOG_FILE = "activity_log.txt"

    init {
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuu ${storageDir.absolutePath}")
    }

    fun saveUserData(userData: UserData) {
        try {
            val file = File(storageDir, USER_DATA_FILE)
            val outputStream = FileOutputStream(file)

            val dataString = "${userData.nombre}|${userData.apellidoPaterno}|${userData.apellidoMaterno}|${userData.dia}|${userData.mes}|${userData.año}|${userData.sexo}"

            outputStream.write(dataString.toByteArray())
            outputStream.close()

            println("Datos de usuario guardados en: ${file.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadUserData(): UserData? {
        try {
            val file = File(storageDir, USER_DATA_FILE)
            if (!file.exists()) return null

            val inputStream = FileInputStream(file)
            val data = inputStream.readBytes().toString(Charsets.UTF_8)
            inputStream.close()

            val parts = data.split("|")
            if (parts.size >= 7) {
                return UserData(
                    nombre = parts[0],
                    apellidoPaterno = parts[1],
                    apellidoMaterno = parts[2],
                    dia = parts[3],
                    mes = parts[4],
                    año = parts[5],
                    sexo = parts[6]
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun saveExamResults(userData: UserData, answers: List<Int>, results: List<Boolean>, score: Int) {
        try {
            val file = File(storageDir, EXAM_RESULTS_FILE)
            val outputStream = FileOutputStream(file)

            val timestamp = System.currentTimeMillis()
            val answersString = answers.joinToString(",")
            val resultsString = results.joinToString(",")
            val edad = calcularEdad(userData.año.toIntOrNull() ?: 2000)
            val signoChino = calcularSignoZodiacalChino(userData.año.toIntOrNull() ?: 2000)

            val dataString = """
                Hombre: ${userData.nombre} ${userData.apellidoPaterno} ${userData.apellidoMaterno}
                Edad: $edad años
                Sexo: ${userData.sexo}
                Fecha_Nacimiento: ${userData.dia}/${userData.mes}/${userData.año}
                Signo_Zodiacal_Chino: $signoChino
                Respuestas_Seleccionadas: $answersString
                Resultados_Correctos: $resultsString
                Calificacion: $score/6
                Porcentaje: ${(score * 100) / 6}%
                Estado: ${if (score >= 4) "APROBADO" else "REPROBADO"}
            """.trimIndent()

            outputStream.write(dataString.toByteArray())
            outputStream.close()

            println("Ruaaaaaaaaaaaaaa ${file.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadExamResults(): String? {
        try {
            val file = File(storageDir, EXAM_RESULTS_FILE)
            if (!file.exists()) return null

            val inputStream = FileInputStream(file)
            val data = inputStream.readBytes().toString(Charsets.UTF_8)
            inputStream.close()

            return data
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun saveActivityLog(activity: String) {
        try {
            val file = File(storageDir, ACTIVITY_LOG_FILE)
            val outputStream = FileOutputStream(file, true) // append mode

            val timestamp = System.currentTimeMillis()
            val logEntry = "${timestamp}: $activity\n"

            outputStream.write(logEntry.toByteArray())
            outputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getAllFiles(): List<String> {
        val files = mutableListOf<String>()
        try {
            storageDir.listFiles()?.forEach { file ->
                if (file.isFile && file.name.endsWith(".txt")) {
                    files.add("${file.name}: ${file.length()} bytes")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return files
    }

    fun clearAllData() {
        try {
            val files = listOf(USER_DATA_FILE, EXAM_RESULTS_FILE, ACTIVITY_LOG_FILE)
            files.forEach { fileName ->
                val file = File(storageDir, fileName)
                if (file.exists()) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getStoragePath(): String {
        return storageDir.absolutePath
    }

    fun saveDataAsText(fileName: String, data: String) {
        try {
            val file = File(storageDir, fileName)
            file.writeText(data)
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa${file.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

     fun calcularEdad(añoNacimiento: Int): Int {
        val añoActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        return añoActual - añoNacimiento
    }

     fun calcularSignoZodiacalChino(año: Int): String {
        val signos = arrayOf(
            "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
            "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
        )
        return signos[año % 12]
    }
}
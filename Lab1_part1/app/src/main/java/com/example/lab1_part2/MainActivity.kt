package com.example.lab1_part1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1_part2.ui.theme.Lab1_part1Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1_part2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCDEC3673)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Центрирование содержимого по вертикали
    ) {
        var myName by remember { mutableStateOf("Привет, Студент!") } // Начальное значение
        var textValue by remember { mutableStateOf("") }

        Text(
            text = myName,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() // Текст заполняет всю ширину экрана
                .align(Alignment.CenterHorizontally), // Центрирование текста
            fontSize = 48.sp,
            textAlign = TextAlign.Center, // Центрирование текста по горизонтали
            softWrap = true // Текст переносится, если не помещается
        )

        Spacer(modifier = Modifier.height(16.dp)) // Пространство между текстом и изображением

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Hello Student Image",
            modifier = Modifier.size(200.dp) // Размер изображения
        )

        Spacer(modifier = Modifier.height(16.dp)) // Пространство между изображением и TextField

        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(text = "Введите имя") },
            placeholder = { Text(text = "Student") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() // Полная ширина TextField
        )

        Button(
            onClick = {
                myName = if (textValue.isNotBlank()) "Привет, $textValue!" else "Привет, Студент!" // Проверка на пустое значение
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally), // Центрирование кнопки по горизонтали
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC0CB) // Розовый цвет кнопки
            )
        ) {
            Text(text = "Приветствовать")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab1_part2Theme {
        Greeting()
    }
}

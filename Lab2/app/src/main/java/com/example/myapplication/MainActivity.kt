package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    Navigation(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("home", "lists")
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.capitalize()) },
                selected = false,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    if (screen == "home") Icon(Icons.Filled.Home, contentDescription = null)
                    else Icon(Icons.Filled.List, contentDescription = null)
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen() }
        composable("lists") { ListsScreen() }
    }
}

@Composable
fun HomeScreen() {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val textSize = if (isPortrait) 28.sp else 22.sp

    var name by rememberSaveable { mutableStateOf("") }
    var textValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF5E95)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (name.isNotEmpty()) {
            Text(
                text = name,
                fontSize = textSize,
                textAlign = TextAlign.Center, // Текст по горизонтали
                color = Color.White, // Цвет имени белый
                modifier = Modifier.padding(16.dp)
            )
        }

        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(text = "Введите имя") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() // TextField по ширине экрана
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    name = if (textValue.isNotEmpty()) textValue else ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C002C))
            ) {
                Text(text = "Вывести имя")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    name = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C002C))
            ) {
                Text(text = "X")
            }
        }
    }
}


@Composable
fun ListsScreen() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF7C002C), Color(0xFFFF5E95))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally // Выровнять по центру
    ) {
        Text("Kotlin", style = MaterialTheme.typography.bodyLarge, color = Color.White, textAlign = TextAlign.Center) // Выравнивание текста по горизонтали
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Kotlin — это язык программирования, который позволяет писать программы для разных платформ. " +
                    "Его разработала международная компания JetBrains, " +
                    "чтобы быстрее писать свой продукт — среду разработки IntelliJ IDEA. " +
                    "Но некоторые особенности сделали этот язык идеальным для " +
                    "мобильной разработки.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center // Центрирование текста
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Картинка по ширине экрана
        Image(
            painter = painterResource(id = R.drawable.kotlin),
            contentDescription = "Kotlin Logo",
            modifier = Modifier
                .fillMaxWidth() // Картинка по ширине экрана
                .height(200.dp) // Высота картинки
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Приложения на Android. Главная сфера применения языка Kotlin — разработка приложений для устройств на Android. Это могут быть смартфоны, телевизоры, умные устройства или приборы для бизнеса и промышленности: кассовые аппараты, терминалы сбора данных и даже некоторые станки. В любом смартфоне на Android большинство приложений наверняка написаны именно на Kotlin. В том числе все основные приложения Google.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center // Выравнивание текста по центру
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        HomeScreen()
    }
}

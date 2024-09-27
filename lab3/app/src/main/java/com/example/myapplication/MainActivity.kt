package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

// Модель данных для авиалиний
data class Airline(
    val destination: String,
    val departureDate: String,
    val departureTime: String
)

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
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(text = "Введите имя") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
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
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    // Пример данных авиалиний
    val airlines = listOf(
        Airline("Нью-Йорк", "2024-10-01", "10:00 AM"),
        Airline("Нью-Йорк", "2024-10-02", "11:00 AM"),
        Airline("Лондон", "2024-10-03", "12:00 PM"),
        Airline("Лондон", "2024-10-04", "01:00 PM"),
        Airline("Токио", "2024-10-05", "02:00 PM"),
        Airline("Токио", "2024-10-06", "03:00 PM"),
        Airline("Сидней", "2024-10-07", "04:00 PM"),
        Airline("Сидней", "2024-10-08", "05:00 PM")
    )

    // Группировка авиалиний по пункту назначения
    val groupedAirlines = airlines.groupBy { it.destination }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF7C002C), Color(0xFFFF5E95))
    )

    // Используем LazyColumn для обеспечения прокрутки
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        groupedAirlines.forEach { (destination, airlineList) ->
            // Заголовок пункта назначения
            item {
                Text(
                    text = destination,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5C0020))
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Список авиалиний для данного пункта назначения
            items(airlineList) { airline ->
                AirlineItem(airline)
            }
        }
    }
}

@Composable
fun AirlineItem(airline: Airline) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x33FFFFFF)) // Полупрозрачный белый фон
            .padding(8.dp)
    ) {
        Text(
            text = "Дата вылета: ${airline.departureDate}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Text(
            text = "Время вылета: ${airline.departureTime}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
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

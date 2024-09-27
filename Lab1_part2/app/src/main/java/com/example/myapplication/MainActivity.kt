package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF7C002C), Color(0xFFFF5E95))
    )

    // Состояния для имени и ввода текста
    var myName by remember { mutableStateOf("") }
    var textValue by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .background(gradientBrush)
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "Kotlin Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))


        if (myName.isNotEmpty()) {
            Text(
                text = myName,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
            )
        }
        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(text = "Введите имя") },
            placeholder = { Text(text = "Student") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    myName = if (textValue.isNotBlank()) "Привет, $textValue!" else ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC0CB)
                ),
                modifier = Modifier
                    .weight(2f)
                    .height(50.dp)
            ) {
                Text(text = "Приветствовать")
            }

            Button(
                onClick = {
                    myName = ""
                    textValue = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC0CB)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = "X")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting()
    }
}

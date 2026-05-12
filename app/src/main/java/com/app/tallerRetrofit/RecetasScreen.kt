package com.app.tallerRetrofit

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun RecetasScreen(viewModel: RecetasViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "🍽️ Receta del Día",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (uiState) {
            is RecetasUiState.Loading -> {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is RecetasUiState.Error -> {
                val error = (uiState as RecetasUiState.Error).message
                Text(text = error, color = Color.Red)
            }

            is RecetasUiState.Success -> {
                val receta = (uiState as RecetasUiState.Success).receta

                Text(
                    text = receta.strMeal,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                AsyncImage(
                    model = receta.strMealThumb,
                    contentDescription = receta.strMeal,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.height(16.dp))

                Text("Ingredientes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                receta.getIngredients().forEach { ingrediente ->
                    Text(text = ingrediente, fontSize = 14.sp)
                }

                Spacer(Modifier.height(16.dp))

                Text("Preparación", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(text = receta.strInstructions, fontSize = 14.sp, lineHeight = 22.sp)

                Spacer(Modifier.height(24.dp))

                if (!receta.strYoutube.isNullOrBlank()) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(receta.strYoutube))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000))
                    ) {
                        Text("▶ Ver en YouTube", color = Color.White)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { viewModel.fetchRandomReceta() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("🔀 Nueva Receta")
                }
            }
        }
    }
}
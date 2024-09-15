package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantsScreen() {
    val viewModel: RestaurantsViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Restaurant App",
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(22.dp)
        )
        Text(
            text = "Cicilia Angelica Rumahorbo\n225150207111113",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(viewModel.state.value) { restaurant ->
                RestaurantItem(restaurant) { id ->
                    viewModel.toggleFavorite(id)
                }
            }
        }
    }
}

@Composable
fun RestaurantItem(item: Restaurant, onClick: (id: Int) -> Unit) {
    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // Icon Location
            RestaurantIcon(Icons.Filled.LocationOn, Modifier.weight(0.15f))

            Spacer(modifier = Modifier.width(8.dp))

            // Details
            RestaurantDetails(
                title = item.name,
                description = "A nice place to eat",
                modifier = Modifier.weight(0.7f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Icon Favorite
            RestaurantIcon(icon, Modifier.weight(0.15f)) {
                onClick(item.id)  // Trigger the favorite toggle
            }
        }
    }
}

@Composable
private fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = { }) {
    Icon(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = MaterialTheme.shapes.medium)
            .padding(12.dp)
            .clickable { onClick() }
    )
}

@Composable
private fun RestaurantDetails(title: String, description: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme {
        RestaurantsScreen()
    }
}

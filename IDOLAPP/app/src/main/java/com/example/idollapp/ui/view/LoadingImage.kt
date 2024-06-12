package com.example.idollapp.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.idollapp.R


@Composable
fun LoadingImage(modifier: Modifier = Modifier, model: Any) {
    AsyncImage(
        model = model,
        contentDescription = "",
        placeholder = painterResource(id = R.drawable.baseline_image_24),
        error = painterResource(id = R.drawable.baseline_image_not_supported_24),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

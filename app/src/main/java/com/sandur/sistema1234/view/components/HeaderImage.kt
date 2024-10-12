package com.sandur.sistema1234.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sandur.sistema1234.R

@Composable
fun headerImage(drawableResource: Int, descriptionResources: String, titleResources: String) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .height(300.dp)
    ) {
        Image(
            modifier = Modifier.height(320.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id =drawableResource),
            contentDescription = descriptionResources
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Text(
            text = titleResources,
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.size_2)),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
    }
}
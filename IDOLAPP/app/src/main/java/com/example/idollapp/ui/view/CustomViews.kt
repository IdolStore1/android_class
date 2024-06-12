package com.example.idollapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ViewSpacer(size: Int) {
    Spacer(modifier = Modifier.size(size.dp))
}

@Composable
fun SpacerDivider(height: Dp = 0.5.dp, color: Color = Color.Gray) {
    Spacer(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(color)
    )
}

@Composable
fun RowScope.ViewSpacerWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.ViewSpacerWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}




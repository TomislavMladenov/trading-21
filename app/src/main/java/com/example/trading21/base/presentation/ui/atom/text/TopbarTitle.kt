package com.example.trading21.base.presentation.ui.atom.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.trading21.R

@Composable
fun TopbarTitle() {
    Text(
        text = stringResource(R.string.app_name),
        color = Color.Black
    )
}
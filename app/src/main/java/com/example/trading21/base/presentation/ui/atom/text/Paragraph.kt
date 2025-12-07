package com.example.trading21.base.presentation.ui.atom.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ParagraphTitle(text: String) {
    /**
     * Apply some styling to the text to be reused
     */
    Text(text = text, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun DefaultText(text: String) {
    /**
     * Apply some styling to the text to be reused
     */
    Text(text = text)
}
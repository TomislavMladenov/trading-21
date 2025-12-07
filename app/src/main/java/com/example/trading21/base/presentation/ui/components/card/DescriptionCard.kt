package com.example.trading21.base.presentation.ui.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.trading21.R
import com.example.trading21.base.presentation.ui.atom.spacer.SmallSpacing
import com.example.trading21.base.presentation.ui.atom.text.DefaultText
import com.example.trading21.base.presentation.ui.atom.text.ParagraphTitle
import com.example.trading21.base.presentation.ui.theme.T21Theme

/**
 * Example purpose only
 */

@Composable
fun Description(
    title: String = stringResource(R.string.tv_about),
    description: String,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(T21Theme.dimens.space_2)) {
            ParagraphTitle(title)
            SmallSpacing()
            DefaultText(description)
        }
    }
}
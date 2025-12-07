package com.example.trading21.base.presentation.ui.atom.spacer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.trading21.base.presentation.ui.theme.T21Theme

@Composable
fun ColumnScope.FillAvailableHeightSpacer() = Spacer(Modifier.weight(1f))

@Composable
fun RowScope.FillAvailableWidthSpacer() = Spacer(Modifier.weight(1f))

@Composable
fun HugeSpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_4))


@Composable
fun BigSpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_3))

@Composable
fun DefaultSpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_2))

@Composable
fun MediumSpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_1_5))

@Composable
fun SmallSpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_1))

@Composable
fun TinySpacing() = Spacer(modifier = Modifier.height(T21Theme.dimens.space_0_5))
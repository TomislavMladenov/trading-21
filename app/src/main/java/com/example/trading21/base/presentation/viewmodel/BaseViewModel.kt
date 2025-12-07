package com.example.trading21.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<State, Action>(
    initialState: State
): ViewModel() {

    @PublishedApi
    internal val mState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = mState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = initialState,
    )

    private val actions = MutableSharedFlow<Action>()

    init {
        collectActions()
    }

    open suspend fun handleActions(action: Action) {
        // To or not to override, some screens mights not have actions
    }

    private fun collectActions() = viewModelScope.launch {
        actions.collect {
            Timber.Forest.i("action collected: $it")
            handleActions(it)
        }
    }

    val submitAction: (action: Action) -> Unit = {
        viewModelScope.launch {
            Timber.Forest.i("action submitted: $it")
            actions.emit(it)
        }
    }

    inline fun updateState(crossinline function: State.() -> State) {
        mState.update(function)
    }
}
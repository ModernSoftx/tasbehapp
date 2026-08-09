package com.yourname.tasbehapp.ui.counter

import com.yourname.tasbehapp.domain.model.Tasbeh

sealed class CounterViewState {
    object Loading : CounterViewState()
    data class Success(
        val tasbeh: Tasbeh,
        val progress: Float
    ) : CounterViewState()
    object NoTasbeh : CounterViewState()
    data class Error(val message: String) : CounterViewState()
}

sealed class CounterEvent {
    object TargetReached : CounterEvent()
    object CountIncremented : CounterEvent()
    object CountDecremented : CounterEvent()
    object CountReset : CounterEvent()
    data class LapCompleted(val lapNumber: Int) : CounterEvent()
}

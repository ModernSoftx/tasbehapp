package com.yourname.tasbehapp.ui.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.tasbehapp.data.repository.TasbehRepository
import com.yourname.tasbehapp.domain.model.Tasbeh
import com.yourname.tasbehapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val repository: TasbehRepository
) : ViewModel() {
    
    private val _viewState = MutableLiveData<CounterViewState>()
    val viewState: LiveData<CounterViewState> = _viewState
    
    private val _event = MutableLiveData<Event<CounterEvent>>()
    val event: LiveData<Event<CounterEvent>> = _event
    
    private var currentTasbeh: Tasbeh? = null
    
    init {
        observeActiveTasbeh()
    }
    
    private fun observeActiveTasbeh() {
        _viewState.value = CounterViewState.Loading
        repository.activeTasbeh.observeForever { tasbeh ->
            if (tasbeh != null) {
                currentTasbeh = tasbeh
                updateState(tasbeh)
            } else {
                _viewState.value = CounterViewState.NoTasbeh
            }
        }
    }
    
    private fun updateState(tasbeh: Tasbeh) {
        val progress = if (tasbeh.targetCount > 0) {
            tasbeh.currentCount.toFloat() / tasbeh.targetCount
        } else 0f
        _viewState.value = CounterViewState.Success(tasbeh, progress)
    }
    
    fun incrementCount() {
        currentTasbeh?.let { tasbeh ->
            viewModelScope.launch {
                val newCount = tasbeh.currentCount + 1
                if (newCount >= tasbeh.targetCount) {
                    val newLaps = tasbeh.totalLaps + 1
                    repository.updateCountAndLaps(tasbeh.id, 0, newLaps)
                    _event.value = Event(CounterEvent.LapCompleted(newLaps))
                } else {
                    repository.updateCountAndLaps(tasbeh.id, newCount, tasbeh.totalLaps)
                    _event.value = Event(CounterEvent.CountIncremented)
                }
            }
        }
    }

    fun decrementCount() {
        currentTasbeh?.let { tasbeh ->
            if (tasbeh.currentCount > 0) {
                viewModelScope.launch {
                    repository.decrementCount(tasbeh)
                    _event.value = Event(CounterEvent.CountDecremented)
                }
            }
        }
    }
    
    fun resetCount() {
        currentTasbeh?.let { tasbeh ->
            viewModelScope.launch {
                repository.updateCountAndLaps(tasbeh.id, 0, 0)
                _event.value = Event(CounterEvent.CountReset)
            }
        }
    }

    fun resetCounterOnly() {
        currentTasbeh?.let { tasbeh ->
            viewModelScope.launch {
                repository.updateCountAndLaps(tasbeh.id, 0, tasbeh.totalLaps)
                _event.value = Event(CounterEvent.CountReset)
            }
        }
    }
}
